# Vaccination-system

Uputstvo za pokretanje projekta:

- Preuzeti zip / Klonirati repoziturijum
- Preuzeti baze sa datog linka:
- Otpakovati preuzete direktorijume

- Pokretanje baza -> apache-tomme-plus-7.1.4 -> bin -> startup.sh
  - Server za baze (Tomcat) je pokrenut na portu 8082
  - Pristupiti RDF bazama /fuseki i /fuseki2
  - Pristupiti XML bazama /exist i /exist2
  - !!! Proveriti da li je kreiran u /fuseki VaccinationDataset (Ukoliko nije, kreirati ga rucno pod tim nazivom)
  - !!! Proveriti da li je kreiran u /fuseki2 OfficialDataset (Ukoliko nije, kreirati ga rucno pod tim nazivom)
  
- Pokrenuti backend i frontend aplikacije
  - Frontend za sluzbenika je pokrenut na portu 4201
  - Frontend za doktora i pacijenta je pokrenut na portu 4200
