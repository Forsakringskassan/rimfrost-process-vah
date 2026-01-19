package se.fk.github.rimfrost.vardavhusdjur.bekraftabeslut;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.regel.bekraftabeslut.*;

@ApplicationScoped
public class BekraftaBeslutService
{

   public BekraftaBeslutRequestMessageData createBekraftaBeslutRequest(String kundbehovsflodeId)
   {
      System.out.printf("Created BekraftaBeslutRequestMessageData with kundbehovsflodeId: %s%n", kundbehovsflodeId);
      BekraftaBeslutRequestMessageData bekraftaBeslutRequestMessageData = new BekraftaBeslutRequestMessageData();
      bekraftaBeslutRequestMessageData.setKundbehovsflodeId(kundbehovsflodeId);
      return bekraftaBeslutRequestMessageData;
   }

   public RattTillForsakring onBekraftaBeslutResponse(BekraftaBeslutResponseMessageData bekraftaBeslutResponse)
   {
      System.out.printf("onBekraftaBeslutResponse. received response: %s%n", bekraftaBeslutResponse.toString());
      System.out.printf("Received VahBekraftaBeslutResponse for kundbehovsflodeId: %s with result: %s%n",
            bekraftaBeslutResponse.getKundbehovsflodeId(), bekraftaBeslutResponse.getRattTillForsakring());
      return bekraftaBeslutResponse.getRattTillForsakring();
   }

}
