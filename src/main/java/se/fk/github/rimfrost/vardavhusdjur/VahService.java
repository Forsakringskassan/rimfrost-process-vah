package se.fk.github.rimfrost.vardavhusdjur;

import org.kie.kogito.internal.process.runtime.KogitoProcessContext;
import se.fk.rimfrost.VahKundbehovsflodeRequestMessagePayload;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VahService
{

   public String startProcess(VahKundbehovsflodeRequestMessagePayload kundbehovsflodeRequest, KogitoProcessContext context)
   {
      System.out.printf("Started vård av husdjur process for kundbehovsflode %s", kundbehovsflodeRequest);
      return kundbehovsflodeRequest.getKundbehovsflodeId();
   }

   public void informAboutDecision(String kundbehovsflodeId)
   {
      System.out.printf("Vård av husdjur application for kundbehovsflodeId %s finished with success!%n", kundbehovsflodeId);
   }

   public void registerDecline(String kundbehovsflodeId)
   {
      System.out.printf("Vård av husdjur application for kundbehovsflodeId %s is declined!%n", kundbehovsflodeId);
   }

}
