package se.fk.github.rimfrost.vardavhusdjur.common;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.framework.regel.RegelRequestMessagePayloadData;
import se.fk.rimfrost.framework.regel.RegelResponseMessagePayloadData;
import se.fk.rimfrost.framework.regel.Utfall;

@ApplicationScoped
public class RegelService
{

   public Utfall onRegelResponse(RegelResponseMessagePayloadData response)
   {
      System.out.printf("onRegelResponse. received response: %s", response.toString());
      System.out.printf("Received RegelResponse for processId: %s with utfall: %s%n",
            response.getHandlaggningId(),
            response.getUtfall().toString());
      return response.getUtfall();
   }

   public RegelRequestMessagePayloadData createRegelRequest(String handlaggningId)
   {
      System.out.printf("Created RegelRequest with handlaggningId: %s%n", handlaggningId);
      RegelRequestMessagePayloadData requestMessageData = new RegelRequestMessagePayloadData();
      requestMessageData.setHandlaggningId(handlaggningId);
      return requestMessageData;
   }

}
