package se.fk.github.rimfrost.vardavhusdjur;

import se.fk.rimfrost.VahKundbehovsflodeRequestMessageData;
import se.fk.rimfrost.VahKundbehovsflodeResponseMessageData;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VahService
{

   public String startProcess(VahKundbehovsflodeRequestMessageData kundbehovsflodeRequest)
   {
      System.out.print("VahService.startProcess\n");
      System.out.printf("triggered by VahKundbehovsflodeRequestMessageData: %s%n", kundbehovsflodeRequest.toString());
      var kundbehovsflodeId = kundbehovsflodeRequest.getKundbehovsflodeId();
      System.out.printf("Started vård av husdjur process for kundbehovsflode %s%n", kundbehovsflodeId);
      return kundbehovsflodeId;
   }

   public VahKundbehovsflodeResponseMessageData informAboutDecision(String kundbehovsflodeId, String resultat)
   {
      System.out.printf("VAH application for kundbehovsflodeId %s finished with result %s !%n", kundbehovsflodeId, resultat);
      VahKundbehovsflodeResponseMessageData vahKundbehovsflodeResponseMessageData = new VahKundbehovsflodeResponseMessageData();
      vahKundbehovsflodeResponseMessageData.setKundbehovsflodeId(kundbehovsflodeId);
      vahKundbehovsflodeResponseMessageData.setResultat(resultat);
      return vahKundbehovsflodeResponseMessageData;
   }

}
