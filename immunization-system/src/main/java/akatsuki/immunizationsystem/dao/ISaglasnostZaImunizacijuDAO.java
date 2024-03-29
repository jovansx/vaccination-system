package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;

import java.util.Optional;

public interface ISaglasnostZaImunizacijuDAO extends IDao<SaglasnostZaImunizaciju> {

    Optional<SaglasnostZaImunizaciju> getByIdBroj(String idBroj);
}
