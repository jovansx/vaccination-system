package akatsuki.officialsystem.service;

import akatsuki.officialsystem.exceptions.BadRequestRuntimeException;
import akatsuki.officialsystem.model.potvrde.DrugaPotvrdaDTO;
import akatsuki.officialsystem.model.zahtevi.NeodobrenZahtevDTO;
import akatsuki.officialsystem.model.zahtevi.NeodobreniZahteviDTO;
import akatsuki.officialsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IzdavanjeDZSService {
    private final RestTemplate restTemplate;
    private final IModelMapper<NeodobreniZahteviDTO> neodobreniZahteviDTOmapper;

    public String getAllNeodobreniZahteviAndPotvrde() {

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/api/potvrde/druge", String.class);
        String drugePotvrdeXml = response.getBody();

        List<DrugaPotvrdaDTO> drugaPotvrdaDTOList = new ArrayList<>();
        String[] drugePotvrde = drugePotvrdeXml.split("<ns2:potvrda_o_vakcinaciji");
        for(String potvrda: drugePotvrde) {
            if(potvrda.equals("<drugePotvrdeDTO>")) continue;

            String idBroj = potvrda.split("<id_broj property=\"pred:id_broj\" datatype=\"xs:string\">")[1].split("</id_broj>")[0].trim();
            String doza1 = potvrda.split("<ns2:doza broj=\"1\">")[1].split("</ns2:doza>")[0].trim();
            String doza1Datum = doza1.split("<ns2:datum_davanja>")[1].split("</ns2:datum_davanja>")[0].trim();
            String doza1Serija = doza1.split("<ns2:serija>")[1].split("</ns2:serija>")[0].trim();

            String doza2 = potvrda.split("<ns2:doza broj=\"2\">")[1].split("</ns2:doza>")[0].trim();
            String doza2Datum = doza2.split("<ns2:datum_davanja>")[1].split("</ns2:datum_davanja>")[0].trim();
            String doza2Serija = doza2.split("<ns2:serija>")[1].split("</ns2:serija>")[0].trim();
            String zdravstvenaUstanova = potvrda.split("<ns2:zdravstvena_ustanova property=\"pred:zdravstvena_ustanova\" datatype=\"xs:string\">")[1].split("</ns2:zdravstvena_ustanova>")[0].trim();
            String nazivVakcine = potvrda.split("<ns2:naziv_vakcine property=\"pred:vakcina\" datatype=\"xs:string\">")[1].split("</ns2:naziv_vakcine>")[0].trim();

            drugaPotvrdaDTOList.add(new DrugaPotvrdaDTO(idBroj, doza1Datum, doza1Serija, doza2Datum, doza2Serija, zdravstvenaUstanova, nazivVakcine));
        }

        response = restTemplate.getForEntity("http://localhost:8081/api/zahtevi/neodobreni", String.class);
        String neodobreniZahteviXml = response.getBody();

        List<NeodobrenZahtevDTO> neodobrenZahtevDTOList = new ArrayList<>();
        String[] neodobreniZahtevi = neodobreniZahteviXml.split("<ns2:zahtev_za_sertifikat");
        for(String zahtev: neodobreniZahtevi) {
            if(zahtev.equals("<neodobreniZahteviDTO>")) continue;
            String ime = zahtev.split("<ime property=\"pred:ime\" datatype=\"xs:string\">")[1].split("</ime>")[0].trim();
            String prezime = zahtev.split("<prezime property=\"pred:prezime\" datatype=\"xs:string\">")[1].split("</prezime>")[0].trim();
            String idBroj = zahtev.split("<id_broj property=\"pred:id_broj\" datatype=\"xs:string\">")[1].split("</id_broj>")[0].trim();
            String pol = zahtev.split("<pol property=\"pred:pol\" datatype=\"xs:string\">")[1].split("</pol>")[0].trim();
            String datumRodjenja = zahtev.split("<datum_rodjenja>")[1].split("</datum_rodjenja>")[0].trim();
            String razlogPodnosenja = zahtev.split("<ns2:razlog_podnosenja_zahteva>")[1].split("</ns2:razlog_podnosenja_zahteva>")[0].trim();

            DrugaPotvrdaDTO drugaPotvrdaDTO = new DrugaPotvrdaDTO();

            boolean isZahtevValidan = false;
            for(DrugaPotvrdaDTO drugaPotvrda: drugaPotvrdaDTOList) {
                if(drugaPotvrda.getIdBroj().equals(idBroj)) {
                    isZahtevValidan = true;
                    drugaPotvrdaDTO = drugaPotvrda;
                }
            }

            neodobrenZahtevDTOList.add(new NeodobrenZahtevDTO(ime, prezime, idBroj, isZahtevValidan, pol, datumRodjenja, razlogPodnosenja, drugaPotvrdaDTO));
        }

        NeodobreniZahteviDTO neodobreniZahteviDTO = new NeodobreniZahteviDTO(neodobrenZahtevDTOList);

        String z = neodobreniZahteviDTOmapper.convertToXml(neodobreniZahteviDTO);
        return z;
    }

    public String createDZS(String digitalniSertifikatXml) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/api/digitalni-sertifikati", digitalniSertifikatXml, String.class);
            String id = response.getBody();
            restTemplate.put("http://localhost:8081/api/zahtevi/" + id, digitalniSertifikatXml);
            return id;
        } catch (Exception e) {
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");
        }
    }

    public void odbaciZahtev(String idBroj) {
        restTemplate.delete("http://localhost:8081/api/zahtevi/" + idBroj);
    }
}
