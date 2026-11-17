package se.fk.github.rimfrost.vardavhusdjur;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class VahProcessvyTest
{
   private static final String rtfMaskinellAktivitetId = "d4ab4820-68d9-41e0-abe1-cd8f9865d275";
   private static final String rtfManuellAktivitetId = "c58dd666-b3c1-4a30-91b8-76c3495668c6";
   private static final String bekraftaBeslutAktivitetId = "8cde2355-aea5-4951-916f-08319b2f1e99";
   private static final String kommuniceringAktivitetId = "50ad36b9-db3d-41ce-ab88-0c9493e1cdb9";

   @Test
   void steg_should_mark_maskinell_kontroll_as_pagar()
   {
      given()
            .queryParam("aktivitetId", rtfMaskinellAktivitetId)
            .when()
            .get("/processvy/steg")
            .then()
            .statusCode(200)
            .body("processId", equalTo("vah"))
            .body("processnamn", equalTo("Vård av husdjur"))
            .body("steg.id", contains("maskinell-kontroll", "manuell-kontroll", "bekrafta-beslut", "kommunicering"))
            .body("steg.status", contains("PAGAR", "KOMMANDE", "KOMMANDE", "KOMMANDE"));
   }

   @Test
   void steg_should_mark_manuell_kontroll_as_pagar()
   {
      given()
            .queryParam("aktivitetId", rtfManuellAktivitetId)
            .when()
            .get("/processvy/steg")
            .then()
            .statusCode(200)
            .body("steg.status", contains("KLAR", "PAGAR", "KOMMANDE", "KOMMANDE"));
   }

   @Test
   void steg_should_mark_bekrafta_beslut_as_pagar()
   {
      given()
            .queryParam("aktivitetId", bekraftaBeslutAktivitetId)
            .when()
            .get("/processvy/steg")
            .then()
            .statusCode(200)
            .body("steg.status", contains("KLAR", "KLAR", "PAGAR", "KOMMANDE"));
   }

   @Test
   void steg_should_mark_kommunicering_as_pagar()
   {
      given()
            .queryParam("aktivitetId", kommuniceringAktivitetId)
            .when()
            .get("/processvy/steg")
            .then()
            .statusCode(200)
            .body("steg.status", contains("KLAR", "KLAR", "KLAR", "PAGAR"));
   }

   @Test
   void steg_should_return_404_for_unknown_aktivitet()
   {
      given()
            .queryParam("aktivitetId", UUID.randomUUID().toString())
            .when()
            .get("/processvy/steg")
            .then()
            .statusCode(404);
   }
}
