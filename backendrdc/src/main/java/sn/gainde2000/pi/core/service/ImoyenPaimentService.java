package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.paiement.entity.MoyenPaiement;

import java.util.List;
import java.util.Optional;

public interface ImoyenPaimentService {
    public List<MoyenPaiement> getListMoyen();
    public Optional<MoyenPaiement> findByMoyenId(Long id);
    public MoyenPaiement save(MoyenPaiement m);
    public void delete(MoyenPaiement m);
}
