package se.fk.github.rimfrost.vardavhusdjur.rtfmanuell;

import jakarta.enterprise.context.ApplicationScoped;
import se.fk.rimfrost.RtfManuellRequestMessageData;
import se.fk.rimfrost.RtfManuellResponseMessageData;

@ApplicationScoped
public class RtfManuellService
{

   public RtfManuellRequestMessageData createRtfManuellRequest(String kundbehovsflodeId)
   {
      System.out.printf("Created RtfManuellRequestMessageData with kundbehovsflodeId: %s%N", kundbehovsflodeId);
      RtfManuellRequestMessageData rtfManuellRequestMessageData = new RtfManuellRequestMessageData();
      rtfManuellRequestMessageData.setKundbehovsflodeId(kundbehovsflodeId);
      return rtfManuellRequestMessageData;
   }

   public String onRtfManuellResponse(RtfManuellResponseMessageData rtfManuellResponse)
   {
      System.out.printf("Received VahRtfManuellResponse for kundbehovsflodeId: %s with result: %s%n",
            rtfManuellResponse.getKundbehovsflodeId(), rtfManuellResponse.getResult());
      return rtfManuellResponse.getResult();
   }

}
