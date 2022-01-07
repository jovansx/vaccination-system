package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;

import java.util.Optional;

public interface IZahtevZaSertifikatDAO extends IDao<ZahtevZaSertifikat> {

    Optional<ZahtevZaSertifikat> getByIdBroj(String id);


}
