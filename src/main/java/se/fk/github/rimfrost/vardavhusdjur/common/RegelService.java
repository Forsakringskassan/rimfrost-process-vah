package se.fk.github.rimfrost.vardavhusdjur.common;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.regel.common.RegelRequestMessagePayloadData;
import se.fk.rimfrost.regel.common.RegelResponseMessagePayloadData;
import se.fk.rimfrost.regel.common.Utfall;

@ApplicationScoped
public class RegelService
{

   public Utfall onRegelResponse(RegelResponseMessagePayloadData response)
   {
      System.out.printf("onRegelResponse. received response: %s", response.toString());
      System.out.printf("Received RegelResponse for processId: %s with utfall: %s%n",
            response.getKundbehovsflodeId(),
            response.getUtfall().toString());
      return response.getUtfall();
   }

   public RegelRequestMessagePayloadData createRegelRequest(String kundbehovsflodeId)
   {
      System.out.printf("Created RegelRequest with kundbehovsflodeId: %s%n", kundbehovsflodeId);
      RegelRequestMessagePayloadData requestMessageData = new RegelRequestMessagePayloadData();
      requestMessageData.setKundbehovsflodeId(kundbehovsflodeId);
      return requestMessageData;
   }

}
