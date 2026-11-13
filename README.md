# Rimfrost vård av husdjur

Ett första exempel av en process, vård av husdjur.

Build it with `./mvnw -s settings.xml clean verify`.

A GitHub workflow will also create a Docker image, it is published to [repository](https://github.com/Forsakringskassan/repository). It can be started with:

```sh
docker run -d \
  -p 8080:8080 \
  ghcr.io/forsakringskassan/rimfrost-vard-av-husdjur-app:snapshot
```
## Processvy

Processen har en processvy (stegindikatorn) som visar handläggaren var i processen en uppgift befinner sig. Processvy-länken `processvy.url=/process/vah` i `application.properties` skickas med i regelanropen, och [processbeskrivning.json](src/main/resources/processbeskrivning.json) bredvid `vah.bpmn` listar processens steg och vilka aktivitets-ID:n som hör till varje steg. Stegen hämtas med `GET /processvy/steg?aktivitetId={uuid}`. Formatet beskrivs i [rimfrost-framework-process](https://github.com/Forsakringskassan/rimfrost-framework-process#processvy).

Den som ändrar aktiviteterna i `vah.bpmn` uppdaterar även processbeskrivningen. `VahProcessbeskrivningTest` går sönder om ett aktivitets-ID i BPMN-filen saknas i processbeskrivningen. Kraven finns i [docs/krav.md](docs/krav.md).

`VahProcessbeskrivningTest` validerar `processbeskrivning.json` med ramverkets inläsare, samma som läser filen när processen startar, och körs med de andra testerna i bygget och i `maven-ci`. Inläsaren kontrollerar att de obligatoriska fälten finns och att inga andra fält finns, att aktivitets-ID:n är UUID, att ett steg har antingen `steg` eller `aktiviteter`, att det finns högst tre nivåer, att id är unika och att ett aktivitets-ID bara hör till ett steg. Valideringen följer ramverksversionen i `pom.xml` och behöver inte uppdateras separat.

## Testing the docker image

src/test contains a test setup using Java Testcontainers (https://java.testcontainers.org/)<br>
The test launches a kafka broker and VAH as test containers and mocks the RTF kafka interactions.
The test uses DTOs generated from OpenAPI- and AsyncAPI-specifications.

Run tests with `./mvnw -s settings.xml clean verify`.

See also: [fk-maven](https://github.com/Forsakringskassan/fk-maven).
