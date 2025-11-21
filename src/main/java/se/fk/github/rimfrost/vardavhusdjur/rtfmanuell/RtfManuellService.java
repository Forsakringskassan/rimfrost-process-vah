package se.fk.github.rimfrost.vardavhusdjur.rtfmanuell;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.regel.rtf.manuell.RattTillForsakring;
import se.fk.rimfrost.regel.rtf.manuell.RtfManuellRequestMessageData;
import se.fk.rimfrost.regel.rtf.manuell.RtfManuellResponseMessageData;


@ApplicationScoped
public class RtfManuellService
{

   public RtfManuellRequestMessageData createRtfManuellRequest(String kundbehovsflodeId)
   {
      System.out.printf("Created RtfManuellRequestMessageData with kundbehovsflodeId: %s%n", kundbehovsflodeId);
      RtfManuellRequestMessageData rtfManuellRequestMessageData = new RtfManuellRequestMessageData();
      rtfManuellRequestMessageData.setKundbehovsflodeId(kundbehovsflodeId);
      return rtfManuellRequestMessageData;
   }

   public RattTillForsakring onRtfManuellResponse(RtfManuellResponseMessageData rtfManuellResponse)
   {
      System.out.printf("Received VahRtfManuellResponse for kundbehovsflodeId: %s with result: %s%n",
            rtfManuellResponse.getKundbehovsflodeId(), rtfManuellResponse.getRattTillForsakring());
      return rtfManuellResponse.getRattTillForsakring();
   }

}
