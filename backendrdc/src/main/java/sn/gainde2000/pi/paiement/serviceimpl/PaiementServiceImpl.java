/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.serviceimpl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.paiement.entity.Paiement;
import sn.gainde2000.pi.paiement.repository.PaiementRepository;
import sn.gainde2000.pi.paiement.service.IPaiementService;

/**
 *
 * @author yougadieng
 */
@Service
public class PaiementServiceImpl implements IPaiementService {

    @Autowired
    PaiementRepository paiementRepository;

    @Override
    public List<Paiement> listOperation() {
       return paiementRepository.findAll();
    }

    @Override
    public Paiement findByReferencePaiement(String referencePaiement) {    
        return paiementRepository.findByPaiReferencePaiement(referencePaiement);
    }

    @Override
    public void payer(Paiement p) {
        paiementRepository.save(p);
    }

    @Override
    public Paiement findByIdFacture(Long idFacture) {
       return paiementRepository.findByPaiIdFacture(idFacture);
    }

    @Override
    public List<Paiement>listOperationByReferenceClient(String username) {
        return paiementRepository.findBypaiReferenceClient(username);
    }

   

   
}
