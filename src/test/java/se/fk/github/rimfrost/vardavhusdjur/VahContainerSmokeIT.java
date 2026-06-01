package se.fk.github.rimfrost.vardavhusdjur;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.fk.rimfrost.HandlaggningResponseMessagePayload;
import se.fk.rimfrost.framework.regel.RegelRequestMessagePayload;
import se.fk.rimfrost.framework.regel.Utfall;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class VahContainerSmokeIT extends VahContainerBase
{
   @Test
   void TestVahSmoke() throws Exception
   {
      var handlaggningId = UUID.randomUUID().toString();
      System.out.println("Starting TestVahSmoke");

      // Start background Kafka responders
      CompletableFuture<Void> responderRtfMaskinell = startKafkaResponder(rtfMaskinellRequestTopic, rtfMaskinellResponseTopic,
            Utfall.UTREDNING, handlaggningId);
      CompletableFuture<Void> responderRtfManuell = startKafkaResponder(rtfManuellRequestTopic, rtfManuellResponseTopic,
            Utfall.JA, handlaggningId);
      CompletableFuture<Void> responderBekraftaBeslut = startKafkaResponder(bekraftaBeslutRequestTopic,
            bekraftaBeslutResponseTopic, Utfall.JA, handlaggningId);

      // Send Handlaggning request to start workflow
      sendVahHandlaggningRequest(handlaggningId, "A1");

      // Verify rtf maskinell message produced by VAH
      String rtfMaskinellRequest = readKafkaRequestMessage(rtfMaskinellRequestTopic, handlaggningId);
      System.out.println("Received rtfMaskinellRequest: " + rtfMaskinellRequest);
      RegelRequestMessagePayload rtfMaskinellRequestMessagePayload = mapper.readValue(rtfMaskinellRequest,
            RegelRequestMessagePayload.class);
      assertEquals(handlaggningId, rtfMaskinellRequestMessagePayload.getData().getHandlaggningId());
      assertEquals("d4ab4820-68d9-41e0-abe1-cd8f9865d275", rtfMaskinellRequestMessagePayload.getData().getAktivitetId());

      // Wait for kafka responder to complete
      responderRtfMaskinell.get(topicTimeout, TimeUnit.SECONDS);

      // Verify rtf manuell message produced by VAH
      String rtfManuellRequest = readKafkaRequestMessage(rtfManuellRequestTopic, handlaggningId);
      System.out.println("Received rtfManuellRequest: " + rtfManuellRequest);
      RegelRequestMessagePayload rtfManuellRequestMessagePayload = mapper.readValue(rtfManuellRequest,
            RegelRequestMessagePayload.class);
      assertEquals(handlaggningId, rtfManuellRequestMessagePayload.getData().getHandlaggningId());
      assertEquals("c58dd666-b3c1-4a30-91b8-76c3495668c6", rtfManuellRequestMessagePayload.getData().getAktivitetId());

      // Wait for kafka responder to complete
      responderRtfManuell.get(topicTimeout, TimeUnit.SECONDS);

      // Verify bekraftaBeslut message produced by VAH
      String bekraftaBeslutRequest = readKafkaRequestMessage(bekraftaBeslutRequestTopic, handlaggningId);
      System.out.println("Received bekraftaBeslutRequest: " + bekraftaBeslutRequest);
      RegelRequestMessagePayload bekraftaBeslutRequestMessagePayload = mapper.readValue(bekraftaBeslutRequest,
            RegelRequestMessagePayload.class);
      assertEquals(handlaggningId, bekraftaBeslutRequestMessagePayload.getData().getHandlaggningId());
      assertEquals("8cde2355-aea5-4951-916f-08319b2f1e99", bekraftaBeslutRequestMessagePayload.getData().getAktivitetId());

      // Wait for kafka responder to complete
      responderBekraftaBeslut.get(topicTimeout, TimeUnit.SECONDS);

      // Wait for response from VAH
      String vahHandlaggningResponse = readKafkaRequestMessage(vahHandlaggningResponseTopic, handlaggningId);
      System.out.println("Received vahHandlaggningResponse: " + vahHandlaggningResponse);
      HandlaggningResponseMessagePayload handlaggningRequestMessagePayload = mapper
            .readValue(vahHandlaggningResponse, HandlaggningResponseMessagePayload.class);
      assertEquals(handlaggningId, handlaggningRequestMessagePayload.getData().getHandlaggningId());
      assertEquals("GODKÄND", handlaggningRequestMessagePayload.getData().getResultat());
   }
}
