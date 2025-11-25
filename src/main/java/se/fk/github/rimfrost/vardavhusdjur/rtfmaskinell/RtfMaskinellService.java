package se.fk.github.rimfrost.vardavhusdjur.rtfmaskinell;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.regel.rtf.maskinell.*;

@ApplicationScoped
public class RtfMaskinellService
{

   public RattTillForsakring onRtfMaskinellResponse(RtfMaskinellResponseMessageData response)
   {
      System.out.printf("onRtfMaskinellResponse. received response: %s", response.toString());
      System.out.printf("Received RtfMaskinellResponse for processId: %s with rattTillForsakring: %s%n",
            response.getKundbehovsflodeId(),
            response.getRattTillForsakring().toString());
      return response.getRattTillForsakring();
   }

   public RtfMaskinellRequestMessageData createRtfMaskinellRequest(String kundbehovsflodeId)
   {
      System.out.printf("Created RtfMaskinellRequest with kundbehovsflodeId: %s%n", kundbehovsflodeId);
      RtfMaskinellRequestMessageData requestMessageData = new RtfMaskinellRequestMessageData();
      requestMessageData.setKundbehovsflodeId(kundbehovsflodeId);
      return requestMessageData;
   }

}
