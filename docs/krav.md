# Krav — rimfrost-process-vah

## 1. Funktionella krav

### PVAH-FR-01 — Processbeskrivning

- **PVAH-FR-01.1** Processen ska ha en processbeskrivning bredvid `vah.bpmn` enligt FPROC-FR-02.1 i `rimfrost-framework-process`.
- **PVAH-FR-01.2** Processbeskrivningen ägs och underhålls av den som ritar och förvaltar BPMN:en.

### PVAH-FR-02 — Processvy-länk

- **PVAH-FR-02.1** Processens konfiguration ska innehålla länken `/process/vah` till processens mikrofrontend.

## 2. Icke-funktionella krav

### PVAH-NFR-01 — Testbarhet

- **PVAH-NFR-01.1** Ett test ska kontrollera att varje aktivitets-ID i `vah.bpmn` finns i processbeskrivningen (FPROC-NFR-01.1), så att bygget går sönder om de glider isär.
