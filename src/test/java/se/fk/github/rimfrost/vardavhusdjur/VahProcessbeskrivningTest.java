package se.fk.github.rimfrost.vardavhusdjur;

import org.junit.jupiter.api.Test;
import se.fk.rimfrost.framework.process.helpers.ProcessbeskrivningVerifier;

class VahProcessbeskrivningTest
{
   @Test
   void processbeskrivning_should_contain_all_aktiviteter()
   {
      ProcessbeskrivningVerifier.assertAllAktiviteterInProcessbeskrivning("vah.bpmn");
   }
}
