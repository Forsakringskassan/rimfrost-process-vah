package se.fk.github.rimfrost.vardavhusdjur.rtf;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.RattTillForsakring;
import se.fk.rimfrost.VahRtfRequestMessageData;
import se.fk.rimfrost.VahRtfResponseMessageData;

@ApplicationScoped
public class RtfService
{

   public RattTillForsakring onRtfMaskinellResponse(RtfMaskinellResponseMessageData response)
   {
      System.out.print(response.toString());
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
