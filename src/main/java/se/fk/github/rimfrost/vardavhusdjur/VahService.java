package se.fk.github.rimfrost.vardavhusdjur;

import se.fk.rimfrost.VahHandlaggningRequestMessageData;
import se.fk.rimfrost.VahHandlaggningResponseMessageData;
import se.fk.rimfrost.framework.regel.Utfall;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VahService
{

   public String startProcess(VahHandlaggningRequestMessageData handlaggningRequest)
   {
      System.out.print("VahService.startProcess\n");
      System.out.printf("triggered by VahHandlaggningRequestMessageData: %s%n", handlaggningRequest.toString());
      var handlaggningId = handlaggningRequest.getHandlaggningId();
      System.out.printf("Started vård av husdjur process for handlaggning %s%n", handlaggningId);
      return handlaggningId;
   }

   public VahHandlaggningResponseMessageData informAboutDecision(String handlaggningId, Utfall utfall)
   {
      System.out.printf("VAH application for handlaggningId %s finished with result %s !%n", handlaggningId, utfall);
      VahHandlaggningResponseMessageData vahHandlaggningResponseMessageData = new VahHandlaggningResponseMessageData();
      vahHandlaggningResponseMessageData.setHandlaggningId(handlaggningId);
      vahHandlaggningResponseMessageData.setResultat(utfall == Utfall.JA ? "GODKÄND" : "EJ GODKÄND");
      return vahHandlaggningResponseMessageData;
   }

}
