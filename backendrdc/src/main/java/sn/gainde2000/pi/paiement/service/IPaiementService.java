/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.paiement.service;

import java.util.Date;
import java.util.List;
import sn.gainde2000.pi.paiement.entity.Paiement;

/**
 *
 * @author yougadieng
 */
public interface IPaiementService {
    public void payer(Paiement p);
    public List<Paiement> listOperation();
    public List<Paiement> listOperationByReferenceClient(String username);
    public Paiement findByReferencePaiement(String referencePaiement);
    public Paiement findByIdFacture(Long idFacture);
    
}
