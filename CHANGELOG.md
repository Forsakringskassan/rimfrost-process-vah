# rimfrost-process-vah changelog

Changelog of rimfrost-process-vah.

## 1.1.3 (2026-06-16)

### Bug Fixes

-  add process_id (#46) ([35e47](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/35e4717d385283a) NilsElveros)  
-  Replace quarkus-smallrye-reactive-messaging-kafka with quarkus-messaging-kafka ([a620c](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/a620c8800283e6f) Lars Persson)  

## rimfrost-1.1 (2026-06-03)

## 1.1.2 (2026-06-03)

### Bug Fixes

-  add test for maskinell JA path that skips manuell kontroll ([4ffdf](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/4ffdff2301d6d23) Ulf Slunga)  
-  migrate integration tests from Docker containers to @QuarkusTest ([6541c](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/6541c0d1cadbb28) Ulf Slunga)  
-  simplify createTopic by removing redundant parameters ([b7df9](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/b7df912189f80f9) Ulf Slunga)  
-  extract container base class and add error log assertion for regel errors ([bd10c](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/bd10c82e45a8490) Ulf Slunga)  
-  filter Kafka responders by handlaggningId to prevent test cross-contamination ([09548](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/09548e8ff739c53) Ulf Slunga)  
-  filter Kafka reads by handlaggningId to prevent test cross-contamination ([4ab38](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/4ab3863f425c994) Ulf Slunga)  
-  add smoke test for maskinell error path ending in Avsluta process med error ([0b668](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/0b668d191de00e5) Ulf Slunga)  
-  split maskinell gateway into separate error and utfall checks ([e281d](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/e281dad6016d7b4) Ulf Slunga)  
-  remove redundant getError() == null guard on Utredning gateway condition ([d74b1](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/d74b1c0d893b1c8) Ulf Slunga)  

### Other changes

**Fix/add postgre (#43)**

* fix: added postgre support 
* fix: bump subprocess and procesframework versions 
* fix: cleanup application.properties 
* fix: bump versions and fix tests 
* fix: removed testcontainer stuff 
* fix: simplifed datasource properties 

[90621](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/90621cb1ed0e5d7) NilsElveros *2026-06-03 09:33:24*

**test instead of verify**


[b80e6](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/b80e64d16b11455) Ulf Slunga *2026-06-01 11:07:55*


## 1.1.1 (2026-05-25)

### Bug Fixes

-  Bump dependency versions ([2db3f](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/2db3fd9fa17121f) Lars Persson)  

## 1.1.0 (2026-05-22)

### Features

-  **FKPOC-635**  add error handling gateways to vah process ([ec88a](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/ec88a7dbf0da78c) Jorgen Lindstrom)  

### Bug Fixes

-  **deps**  update dependency se.fk.rimfrost.framework.process:rimfrost-framework-process to v1.1.0 ([54ce8](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/54ce8eb20fa5a82) renovate[bot])  

### Dependency updates

- update dependency org.apache.maven.plugins:maven-dependency-plugin to v3.10.0 ([c95eb](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/c95eb3d61787b61) renovate[bot])  
### Other changes

**refactor/FKPOC-774: replace error script tasks with onExit scripts on call activities**


[e7215](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/e7215e685cd8c9c) Jorgen Lindstrom *2026-05-21 13:50:16*

**feat/FKPOC-774: refactor VAH process error handling to use RegelErrorInformation via sharedErrorInfo variable**


[6b3b7](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/6b3b7e889fb1aed) Jorgen Lindstrom *2026-05-21 11:30:32*

**fix/FKPOC-774: Clean up diagram layout in visual editor**


[69311](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/69311c3dfab63c2) Jorgen Lindstrom *2026-05-21 08:07:21*

**chore/FKPOC-774: remove unused rimfrost-framework-regel-asyncapi dependency**


[c66aa](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/c66aad9ec04e90d) Jorgen Lindstrom *2026-05-20 08:56:07*

**feat/FKPOC-774: update vah.bpmn to use RegelProcessResult and call endProcessWithError directly per subprocess**


[4db39](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/4db39442e0d90ae) Jorgen Lindstrom *2026-05-20 08:28:05*

**feat/FKPOC-774: update endProcess input type to RegelProcessResult in vah.bpmn**


[f7817](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/f78174da7ea85ab) Jorgen Lindstrom *2026-05-19 09:18:08*

**Merge branch 'main' of https://github.com/Forsakringskassan/rimfrost-process-vah**


[a6a07](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/a6a075d304e3e7a) Jorgen Lindstrom *2026-05-18 07:57:42*

**add extra gateway**


[32005](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/32005a469ea126e) Jorgen Lindstrom *2026-04-28 11:28:20*


## 1.0.0 (2026-04-28)

### Breaking changes

-  release 1.0 (#36) ([b9f08](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/b9f086acb3df01c) NilsElveros)  

### Features

-  release 1.0 (#36) ([b9f08](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/b9f086acb3df01c) NilsElveros)  

### Bug Fixes

-  bump rimfrost-framework-version (#35) ([79374](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/7937474b9aee592) NilsElveros)  

## 0.1.4 (2026-04-17)

### Bug Fixes

-  use bpmn files from other repos (#34) ([f0ed1](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/f0ed1e1061bb44c) NilsElveros)  

## 0.1.3 (2026-03-17)

### Bug Fixes

-  Bump process asyncapi version to 0.1.4 ([8c14e](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/8c14eea6270346a) Lars Persson)  
-  Add support for aktivitetId ([0e63c](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/0e63c8abbd8905a) Lars Persson)  
-  **deps**  update dependency se.fk.maven:fk-maven-quarkus-parent to v1.12.0 ([ce69f](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/ce69f3ee7e432f7) renovate[bot])  
-  Use handlaggning-responses as return topic ([6cf28](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/6cf2829dfe51e4f) Lars Persson)  
-  renaming handlaggning ([5586d](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/5586d44c4de803b) Ulf Slunga)  

## 0.1.2 (2026-03-04)

### Bug Fixes

-  renaming handlaggning ([1dd85](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/1dd85f8d94d66cd) Ulf Slunga)  

## 0.1.1 (2026-03-03)

### Bug Fixes

-  Bump to trigger release flow ([143d0](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/143d095628237b0) Lars Persson)  

## 0.1.0 (2026-02-22)

### Features

-  Add bekraftabeslut as subprocess to vah process ([eaf4a](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/eaf4a5661bc5939) Lars Persson)  

### Bug Fixes

-  update to use framework package instead of common (#25) ([2ac44](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/2ac446377905150) NilsElveros)  
-  Update vah process to support common consumer and producer ([15fb2](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/15fb2f0b395a41c) Lars Persson)  

### Dependency updates

- update dependency org.apache.maven.plugins:maven-failsafe-plugin to v3.5.5 ([c8575](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/c857534fe3e4311) renovate[bot])  
- update testcontainers-java monorepo to v1.21.4 (#20) ([c0f7a](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/c0f7affa57a1bde) renovate[bot])  
### Other changes


## 0.0.1 (2026-01-14)

### Features

-  stegar API och Docker ([e01d6](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/e01d60f1b1dadad) Tomas Bjerre)  
-  use Spotless plugin with code standard from jar ([d9046](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/d9046c2b6e81fb0) Tomas Bjerre)  
-  publish till gemensamt repo ([ffe1b](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/ffe1b02eed2716e) Tomas Bjerre)  
-  publicerar till gemensamt repository ([5ba77](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/5ba77e7ce9b3acb) Tomas Bjerre)  
-  parent ([92b63](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/92b63d6dac54057) Tomas Bjerre)  
-  parent ([6b9ac](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/6b9ac72b380f482) Tomas Bjerre)  

### Bug Fixes

-  Update to quarkus version 3.20.3 (#22) ([7a0db](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/7a0db4de1b8cfe6) NilsElveros)  
-  bygge av lokal docker image och startup av container ([b28f7](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/b28f7294a80184c) Ulf Slunga)  
-  smoke test använder DTOs genererade från AsyncAPI-specen ([a92ae](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/a92ae1b876e8da2) Ulf Slunga)  
-  build docker image with mvn package ([3c6d6](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/3c6d6ba65de622d) Ulf Slunga)  
-  Added containerized test ([dceeb](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/dceeb4e28474835) Ulf Slunga)  
-  update VahRtfResponse to match the message sent by the RTF rule (#10) ([0d677](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/0d67724a0c5b627) NilsElveros)  
-  **deps**  update dependency se.fk.maven:fk-maven-parent to v1.11.1 ([81259](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/8125932269a6f51) renovate[bot])  
-  update VahRtfResponse to match the message sent by the RTF rule ([859e4](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/859e491e388f9b3) Nils Elveros)  
-  Add version to pom ([b8a5c](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/b8a5c1289302979) Ulf Slunga)  
-  **deps**  update dependency se.fk.maven:fk-maven-parent to v1.6.4 (#5) ([a120c](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/a120c41d8a4435e) forsakringskassan[bot])  
-  add snapshot repository for kogito snapshot ([05305](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/05305789b4772a1) David Söderberg)  
-  **deps**  update quarkus.platform.version to v3.28.3 ([78422](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/78422ef0506b569) renovate[bot])  

### Dependency updates

- update dependency io.quarkus:quarkus-maven-plugin to v3.30.2 ([4e0fd](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/4e0fd8eb769172e) renovate[bot])  
- update testcontainers-java monorepo to v1.21.3 ([d74f4](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/d74f42543165599) renovate[bot])  
- update dependency io.quarkus:quarkus-maven-plugin to v3.29.3 ([8c207](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/8c2073cd1620c71) renovate[bot])  
- update dependency org.apache.maven.plugins:maven-failsafe-plugin to v3.5.4 ([eaf30](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/eaf305260d63323) renovate[bot])  
- update dependency io.quarkus:quarkus-maven-plugin to v3.29.0 ([b6cce](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/b6cce203f1796bc) renovate[bot])  
- update dependency io.quarkus:quarkus-maven-plugin to v3.29.0 ([ebad4](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/ebad4c5616574c9) renovate[bot])  
- add renovate.json ([0e79a](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/0e79a91d01daf96) renovate[bot])  
- update dependency org.apache.maven.plugins:maven-compiler-plugin to v3.14.1 ([41ac0](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/41ac0d364cf6123) renovate[bot])  
- fk-maven ([ce078](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/ce078ccdc0d9179) Tomas Bjerre)  
- update dependency org.apache.maven.plugins:maven-compiler-plugin to v3.14.1 ([e1c97](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/e1c9733a48854e2) renovate[bot])  
### Other changes

**Update smoke-test.yaml**


[a2b68](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/a2b68529e3e6460) Ulf Slunga *2025-11-14 08:32:22*

**Create smoke-test.yaml**


[509af](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/509afc26296308f) Ulf Slunga *2025-11-14 07:54:59*

**Merge branch 'main' of https://github.com/Forsakringskassan/rimfrost-vard-av-husdjur into refactor/FKPOC-109-refactor-flow-to-use-generated-classes**


[dea5c](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/dea5cfd5c2270a8) David Söderberg *2025-11-10 08:20:17*

**formatting**


[69730](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/697309d64a6a2f0) Ulf Slunga *2025-10-31 13:24:58*

**Merge branch 'feature/parent'**


[8c9a8](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/8c9a89e55dcf2fd) Tomas Bjerre *2025-10-09 16:09:20*

**first commit**


[18d4a](https://github.com/Forsakringskassan/rimfrost-process-vah/commit/18d4ab1d6d92ad5) Tomas Bjerre *2025-10-09 10:47:31*


