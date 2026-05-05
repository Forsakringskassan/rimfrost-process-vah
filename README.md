# Rimfrost vård av husdjur

Ett första exempel av en process, vård av husdjur.

Build it with `./mvnw -s settings.xml clean verify`.

A GitHub workflow will also create a Docker image, it is published to [repository](https://github.com/Forsakringskassan/repository). It can be started with:

```sh
docker run -d \
  -p 8080:8080 \
  ghcr.io/forsakringskassan/rimfrost-vard-av-husdjur-app:snapshot
```
## Testing the docker image

src/test contains a test setup using Java Testcontainers (https://java.testcontainers.org/)<br>
The test launches a kafka broker and VAH as test containers and mocks the RTF kafka interactions.
The test uses DTOs generated from OpenAPI- and AsyncAPI-specifications.

Run tests with `./mvnw -s settings.xml clean verify`.

See also: [fk-maven](https://github.com/Forsakringskassan/fk-maven).

## Felhantering

Processen hanterar fel från regel-subprocesserna (rtf_maskinell, rtf_manuell och bekraftabeslut). 
Om en subprocess returnerar utfall `Error` loggas felet och processen skickar ett svar på 
`handlaggning-responses` med `resultat: "FEL"` samt felkod `REGEL_FEL` och ett felmeddelande.

Felhanteringen är implementerad i tre lager:
- **rimfrost-process-asyncapi** – `HandlaggningResponseMessageData` innehåller ett `error`-fält med `felkod` och `felmeddelande`
- **rimfrost-framework-process** – `ProcessService.endProcessWithError()` bygger felsvaret
- **vah.bpmn** – processgateways dirigerar felflöden till logging och avslut med Kafka-svar
