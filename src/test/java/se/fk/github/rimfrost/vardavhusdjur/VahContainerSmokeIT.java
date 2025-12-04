package se.fk.github.rimfrost.vardavhusdjur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import se.fk.rimfrost.SpecVersion;
import se.fk.rimfrost.VahKundbehovsflodeRequestMessageData;
import se.fk.rimfrost.VahKundbehovsflodeRequestMessagePayload;
import se.fk.rimfrost.VahKundbehovsflodeResponseMessagePayload;
import se.fk.rimfrost.regel.rtf.manuell.RtfManuellRequestMessageData;
import se.fk.rimfrost.regel.rtf.manuell.RtfManuellRequestMessagePayload;
import se.fk.rimfrost.regel.rtf.manuell.RtfManuellResponseMessageData;
import se.fk.rimfrost.regel.rtf.manuell.RtfManuellResponseMessagePayload;
import se.fk.rimfrost.regel.rtf.maskinell.RtfMaskinellRequestMessageData;
import se.fk.rimfrost.regel.rtf.maskinell.RtfMaskinellRequestMessagePayload;
import se.fk.rimfrost.regel.rtf.maskinell.RtfMaskinellResponseMessageData;
import se.fk.rimfrost.regel.rtf.maskinell.RtfMaskinellResponseMessagePayload;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("deprecation")
@Testcontainers
public class VahContainerSmokeIT
{

   private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
         .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
   private static KafkaContainer kafka;
   private static GenericContainer<?> vah;
   private static final String kafkaImage = TestConfig.get("kafka.image");
   private static final String vahImage = TestConfig.get("vah.image");
   private static final int vahPort = TestConfig.getInt("vah.port");
   private static final String vahKundbehovsflodeRequestTopic = TestConfig.get("vah.kundbehovsflode.requests.topic");
   private static final String vahKundbehovsflodeResponseTopic = TestConfig.get("vah.kundbehovsflode.responses.topic");
   private static final String rtfMaskinellRequestTopic = TestConfig.get("rtf.maskinell.requests.topic");
   private static final String rtfMaskinellResponseTopic = TestConfig.get("rtf.maskinell.responses.topic");
   private static final String rtfManuellRequestTopic = TestConfig.get("rtf.manuell.requests.topic");
   private static final String rtfManuellResponseTopic = TestConfig.get("rtf.manuell.responses.topic");
   private static final int topicTimeout = TestConfig.getInt("topic.timeout");
   private static final String networkAlias = TestConfig.get("network.alias");
   private static final String smallryeKafkaBootstrapServers = networkAlias + ":9092";
   private static Network network = Network.newNetwork();

   @BeforeAll
   static void setupKafka()
   {

      kafka = new KafkaContainer(DockerImageName.parse(kafkaImage)
            .asCompatibleSubstituteFor("apache/kafka"))
            .withNetwork(network)
            .withNetworkAliases(networkAlias);
      kafka.start();
      try
      {
         createTopic(vahKundbehovsflodeRequestTopic, 1, (short) 1);
         createTopic(vahKundbehovsflodeResponseTopic, 1, (short) 1);
         createTopic(rtfMaskinellRequestTopic, 1, (short) 1);
         createTopic(rtfMaskinellResponseTopic, 1, (short) 1);
         createTopic(rtfManuellRequestTopic, 1, (short) 1);
         createTopic(rtfManuellResponseTopic, 1, (short) 1);
      }
      catch (Exception e)
      {
         throw new RuntimeException("Failed to create Kafka topics", e);
      }
      setupVah();

   }

   static void setupVah()
   {
      //noinspection resource
      vah = new GenericContainer<>(DockerImageName.parse(vahImage))
            .withNetwork(network)
            .withEnv("MP_MESSAGING_CONNECTOR_SMALLRYE_KAFKA_BOOTSTRAP_SERVERS", smallryeKafkaBootstrapServers);
      vah.start();
   }

   static void createTopic(String topicName, int numPartitions, short replicationFactor) throws Exception
   {
      String bootstrap = kafka.getBootstrapServers().replace("PLAINTEXT://", "");
      Properties props = new Properties();
      props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);

      try (AdminClient admin = AdminClient.create(props))
      {
         NewTopic topic = new NewTopic(topicName, numPartitions, replicationFactor);
         admin.createTopics(List.of(topic)).all().get();
         System.out.printf("Created topic: %S%n", topicName);
      }
   }

   @AfterAll
   static void tearDown()
   {
      if (vah != null)
         vah.stop();
      if (kafka != null)
         kafka.stop();
   }

   private String readKafkaRequestMessage(String topic)
   {
      String bootstrap = kafka.getBootstrapServers().replace("PLAINTEXT://", "");
      Properties props = new Properties();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-" + System.currentTimeMillis());
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

      try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props))
      {
         System.out.printf("New kafka consumer subscribing to topic: %s%n", topic);
         consumer.subscribe(Collections.singletonList(topic));
         ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(120));

         if (records.isEmpty())
         {
            throw new IllegalStateException("No Kafka message received on topic " + topic);
         }
         return records.iterator().next().value();
      }
   }

   private CompletableFuture<Void> startKafkaResponderRtfMaskinell(ExecutorService executor)
   {
      return CompletableFuture.runAsync(() -> {
         try (KafkaConsumer<String, String> consumer = createConsumer())
         {
            consumer.subscribe(Collections.singletonList(rtfMaskinellRequestTopic));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            if (records.isEmpty())
            {
               throw new IllegalStateException("No Kafka message received on " + rtfMaskinellRequestTopic);
            }

            // Deserialize request message into typed payload
            String message = records.iterator().next().value();
            RtfMaskinellRequestMessagePayload request = mapper.readValue(message, RtfMaskinellRequestMessagePayload.class);
            // Extract data safely
            RtfMaskinellRequestMessageData requestData = request.getData();
            if (requestData == null)
            {
               throw new IllegalStateException("Missing data field in Kafka message: " + message);
            }
            String kundbehovsflodeId = requestData.getKundbehovsflodeId();
            // Create typed response data object
            RtfMaskinellResponseMessageData responseData = new RtfMaskinellResponseMessageData();
            responseData.setKundbehovsflodeId(kundbehovsflodeId);
            responseData.setRattTillForsakring(se.fk.rimfrost.regel.rtf.maskinell.RattTillForsakring.UTREDNING);

            sendMaskinellRtfResponse(request, rtfMaskinellResponseTopic, responseData);
            System.out.printf("Sent mock Kafka response for kundbehovsflodeId=%s%n", kundbehovsflodeId);
         }
         catch (Exception e)
         {
            throw new RuntimeException("Kafka responder failed", e);
         }
      }, executor);
   }

   private CompletableFuture<Void> startKafkaResponderRtfManuell(ExecutorService executor)
   {
      return CompletableFuture.runAsync(() -> {
         try (KafkaConsumer<String, String> consumer = createConsumer())
         {
            consumer.subscribe(Collections.singletonList(rtfManuellRequestTopic));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            if (records.isEmpty())
            {
               throw new IllegalStateException("No Kafka message received on " + rtfManuellRequestTopic);
            }

            // Deserialize request message into typed payload
            String message = records.iterator().next().value();
            RtfManuellRequestMessagePayload request = mapper.readValue(message, RtfManuellRequestMessagePayload.class);
            // Extract data safely
            RtfManuellRequestMessageData requestData = request.getData();
            if (requestData == null)
            {
               throw new IllegalStateException("Missing data field in Kafka message: " + message);
            }
            String kundbehovsflodeId = requestData.getKundbehovsflodeId();
            // Create typed response data object
            RtfManuellResponseMessageData responseData = new RtfManuellResponseMessageData();
            responseData.setKundbehovsflodeId(kundbehovsflodeId);
            responseData.setRattTillForsakring(se.fk.rimfrost.regel.rtf.manuell.RattTillForsakring.JA);

            sendManuellRtfResponse(request, rtfManuellResponseTopic, responseData);
            System.out.printf("Sent mock Kafka response for kundbehovsflodeId=%s%n", kundbehovsflodeId);
         }
         catch (Exception e)
         {
            throw new RuntimeException("Kafka responder failed", e);
         }
      }, executor);
   }

   private void sendVahKundbehovsflodeRequest(String kundbehovsflodeId, String messageKey) throws Exception
   {
      VahKundbehovsflodeRequestMessagePayload payload = new VahKundbehovsflodeRequestMessagePayload();
      VahKundbehovsflodeRequestMessageData data = new VahKundbehovsflodeRequestMessageData();
      data.setKundbehovsflodeId(kundbehovsflodeId);
      payload.setSpecversion(SpecVersion.NUMBER_1_DOT_0);
      payload.setId("TestId-001");
      payload.setSource("TestSource-001");
      payload.setType(vahKundbehovsflodeRequestTopic);
      payload.setData(data);
      // Serialize entire payload to JSON
      String eventJson = mapper.writeValueAsString(payload);

      Properties props = new Properties();
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

      try (KafkaProducer<String, String> producer = new KafkaProducer<>(props))
      {
         ProducerRecord<String, String> record = new ProducerRecord<>(
               vahKundbehovsflodeRequestTopic,
               messageKey,
               eventJson);
         System.out.printf("Kafka sending to topic : %s, json: %s%n", vahKundbehovsflodeRequestTopic, eventJson);
         producer.send(record).get();
      }
   }

   private void sendMaskinellRtfResponse(RtfMaskinellRequestMessagePayload request,
         String topic,
         RtfMaskinellResponseMessageData messageData) throws Exception
   {
      RtfMaskinellResponseMessagePayload payload = new RtfMaskinellResponseMessagePayload();
      payload.setSpecversion(request.getSpecversion());
      payload.setId(request.getId());
      payload.setSource(request.getSource());
      payload.setType(topic); // Hardcoded but should be taken from reply-to in header
      payload.setTime(OffsetDateTime.now());
      payload.setKogitoparentprociid(request.getKogitoparentprociid());
      payload.setKogitorootprocid(request.getKogitorootprocid());
      payload.setKogitoproctype(request.getKogitoproctype());
      payload.setKogitoprocinstanceid(request.getKogitoprocinstanceid());
      payload.setKogitoprocist(request.getKogitoprocist());
      payload.setKogitoprocversion(request.getKogitoprocversion());
      payload.setKogitorootprociid(request.getKogitorootprociid());
      payload.setKogitoprocid(request.getKogitoprocid());
      payload.setKogitoprocrefid(request.getKogitoprocinstanceid());

      payload.setData(messageData);

      // Serialize entire payload to JSON
      String eventJson = mapper.writeValueAsString(payload);

      Properties props = new Properties();
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

      try (KafkaProducer<String, String> producer = new KafkaProducer<>(props))
      {
         ProducerRecord<String, String> record = new ProducerRecord<>(
               topic,
               request.getId(), // message key
               eventJson);
         System.out.printf("Kafka mock sending: %s\n", eventJson);
         producer.send(record).get();
      }
   }

   private void sendManuellRtfResponse(RtfManuellRequestMessagePayload request,
         String topic,
         RtfManuellResponseMessageData messageData) throws Exception
   {

      RtfManuellResponseMessagePayload payload = new RtfManuellResponseMessagePayload();
      payload.setSpecversion(request.getSpecversion());
      payload.setId(request.getId());
      payload.setSource(request.getSource());
      payload.setType(topic);
      payload.setTime(OffsetDateTime.now());
      payload.setKogitoparentprociid(request.getKogitoparentprociid());
      payload.setKogitorootprocid(request.getKogitorootprocid());
      payload.setKogitoproctype(request.getKogitoproctype());
      payload.setKogitoprocinstanceid(request.getKogitoprocinstanceid());
      payload.setKogitoprocist(request.getKogitoprocist());
      payload.setKogitoprocversion(request.getKogitoprocversion());
      payload.setKogitorootprociid(request.getKogitorootprociid());
      payload.setKogitoprocid(request.getKogitoprocid());
      payload.setKogitoprocrefid(request.getKogitoprocinstanceid());

      payload.setData(messageData);

      // Serialize entire payload to JSON
      String eventJson = mapper.writeValueAsString(payload);

      Properties props = new Properties();
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

      try (KafkaProducer<String, String> producer = new KafkaProducer<>(props))
      {
         ProducerRecord<String, String> record = new ProducerRecord<>(
               topic,
               request.getId(), // message key
               eventJson);
         System.out.printf("Kafka mock sending: %s\n", eventJson);
         producer.send(record).get();
      }
   }

   private KafkaConsumer<String, String> createConsumer()
   {
      Properties props = new Properties();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-" + System.currentTimeMillis());
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      return new KafkaConsumer<>(props);
   }

   @Test
   void TestVahSmoke() throws Exception
   {
      var kundbehovsflodeId = UUID.randomUUID().toString();
      System.out.println("Starting TestVahSmoke");
      // Start background Kafka responders
      ExecutorService executorRtfMaskinell = Executors.newSingleThreadExecutor();
      CompletableFuture<Void> responderRtfMaskinell = startKafkaResponderRtfMaskinell(executorRtfMaskinell);
      ExecutorService executorRtfManuell = Executors.newSingleThreadExecutor();
      CompletableFuture<Void> responderRtfManuell = startKafkaResponderRtfManuell(executorRtfManuell);
      // Send Kundbehovsflöde request to start workflow
      sendVahKundbehovsflodeRequest(kundbehovsflodeId, "A1");
      // Verify rtf maskinell message produced by VAH
      String rtfMaskinellRequest = readKafkaRequestMessage(rtfMaskinellRequestTopic);
      System.out.println("Received rtfMaskinellRequest: " + rtfMaskinellRequest);
      RtfMaskinellRequestMessagePayload rtfMaskinellRequestMessagePayload = mapper.readValue(rtfMaskinellRequest,
            RtfMaskinellRequestMessagePayload.class);
      assertEquals(kundbehovsflodeId, rtfMaskinellRequestMessagePayload.getData().getKundbehovsflodeId());
      // Wait for kafka responder to complete
      responderRtfMaskinell.get(topicTimeout, TimeUnit.SECONDS);
      // Verify rtf manuell message produced by VAH
      String rtfManuellRequest = readKafkaRequestMessage(rtfManuellRequestTopic);
      System.out.println("Received rtfManuellRequest: " + rtfManuellRequest);
      RtfManuellRequestMessagePayload rtfManuellRequestMessagePayload = mapper.readValue(rtfManuellRequest,
            RtfManuellRequestMessagePayload.class);
      assertEquals(kundbehovsflodeId, rtfManuellRequestMessagePayload.getData().getKundbehovsflodeId());
      // Wait for kafka responder to complete
      responderRtfManuell.get(topicTimeout, TimeUnit.SECONDS);
      // Wait for response from VAH
      String vahKundbehovsflodeResponse = readKafkaRequestMessage(vahKundbehovsflodeResponseTopic);
      System.out.println("Received vahKundbehovsflodeResponse: " + vahKundbehovsflodeResponse);
      VahKundbehovsflodeResponseMessagePayload vahKundbehovsflodeRequestMessagePayload = mapper
            .readValue(vahKundbehovsflodeResponse, VahKundbehovsflodeResponseMessagePayload.class);
      assertEquals(kundbehovsflodeId, vahKundbehovsflodeRequestMessagePayload.getData().getKundbehovsflodeId());
      assertEquals("GODKÄND", vahKundbehovsflodeRequestMessagePayload.getData().getResultat());
   }
}
