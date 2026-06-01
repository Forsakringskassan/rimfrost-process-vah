package se.fk.github.rimfrost.vardavhusdjur;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.fk.rimfrost.HandlaggningResponseMessagePayload;
import se.fk.rimfrost.framework.regel.RegelRequestMessagePayload;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class VahContainerErrorIT extends VahContainerBase
{
   /**
    * Verifies the "Avsluta process med error" path when maskinell kontroll returns a technical error. The process must
    * skip manuell kontroll and bekräfta beslut and instead send a handlaggning response with the error populated.
    */
   @Test
   void TestVahMaskinellError() throws Exception
   {
      var handlaggningId = UUID.randomUUID().toString();
      System.out.println("Starting TestVahMaskinellError");

      CompletableFuture<Void> responderRtfMaskinell = startKafkaResponderWithError(
            rtfMaskinellRequestTopic, rtfMaskinellResponseTopic, "RTF-001", "Tekniskt fel i rtfMaskinell",
            handlaggningId);

      sendVahHandlaggningRequest(handlaggningId, "A1");

      String rtfMaskinellRequest = readKafkaRequestMessage(rtfMaskinellRequestTopic, handlaggningId);
      System.out.println("Received rtfMaskinellRequest: " + rtfMaskinellRequest);
      RegelRequestMessagePayload rtfMaskinellRequestMessagePayload = mapper.readValue(rtfMaskinellRequest,
            RegelRequestMessagePayload.class);
      assertEquals(handlaggningId, rtfMaskinellRequestMessagePayload.getData().getHandlaggningId());
      assertEquals("d4ab4820-68d9-41e0-abe1-cd8f9865d275", rtfMaskinellRequestMessagePayload.getData().getAktivitetId());

      responderRtfMaskinell.get(topicTimeout, TimeUnit.SECONDS);

      String vahHandlaggningResponse = readKafkaRequestMessage(vahHandlaggningResponseTopic, handlaggningId);
      System.out.println("Received vahHandlaggningResponse: " + vahHandlaggningResponse);
      HandlaggningResponseMessagePayload response = mapper.readValue(vahHandlaggningResponse,
            HandlaggningResponseMessagePayload.class);
      assertEquals(handlaggningId, response.getData().getHandlaggningId());
      assertEquals("FEL", response.getData().getResultat());
      assertNotNull(response.getData().getError());

      boolean errorLogged = Arrays.stream(vah.getLogs().split("\n"))
            .filter(line -> line.contains(handlaggningId))
            .anyMatch(line -> line.contains("ERROR") && line.contains("RTF-001"));
      assertTrue(errorLogged, "Expected ERROR log containing handlaggningId and felkod RTF-001");
   }
}
