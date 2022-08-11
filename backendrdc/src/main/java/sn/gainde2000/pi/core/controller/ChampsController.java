package sn.gainde2000.pi.core.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.ValueChamps;
import sn.gainde2000.pi.core.service.IChampsService;
import sn.gainde2000.pi.core.service.IValue;

@RestController
@CrossOrigin("*")
public class ChampsController {

    @Autowired
    IChampsService champsService;
    @Autowired
    private IValue valueService;
    //Liste des Champs

    @GetMapping("champs/list")
    public ServeurResponse getAllChamps() {
        Iterable<Champs> champs = champsService.getListChamps();
        ServeurResponse response = new ServeurResponse();
        if (champs == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucun élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(champs);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    //Liste des Champs pour un formulaire

    @GetMapping("champs/champsByForm/{id}")
    public ServeurResponse getAllChampsIdForm(@PathVariable Long id) {
        List<Champs> champs = champsService.listByFrmId(id);
        ServeurResponse response = new ServeurResponse();
        if (champs == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucun élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(champs);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @GetMapping("champs/fieldByForm/{id}")
    public ServeurResponse getFieldChampsIdForm(@PathVariable Long id) {
        Iterable<Champs> champs = champsService.listFieldByFrmId(id);
        ServeurResponse response = new ServeurResponse();
        if (champs == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucun élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(champs);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    @GetMapping("champs/deleteByForm/{id}")
    public ServeurResponse supprimerChampsIdForm(@PathVariable Long id) {
      champsService.supprimerByFormulaire(id);
        ServeurResponse response = new ServeurResponse();
   
            response.setStatut(true);
            response.setData(null);
            response.setDescription("suppression des champs");

     
        return response;
    }

    @GetMapping("champs/buttonByForm/{id}")
    public ServeurResponse getbuttonChampsIdForm(@PathVariable Long id) {
        Iterable<Champs> champs = champsService.listButtonByFrmId(id);
        ServeurResponse response = new ServeurResponse();
        if (champs == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucun élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(champs);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    //Ajouter Champs

    @PostMapping("champs/create")
    public ServeurResponse create(@RequestBody Champs champs) {
    	
        ServeurResponse response = new ServeurResponse();
       
        champsService.save(champs);
        response.setStatut(true);
        response.setDescription("Enregistrement réussi");
        response.setData(champs);

        return response;
    }
    //recuperation par id

    @GetMapping("champs/{id}")
    public Optional<Champs> findById(@PathVariable Long id) {
        return champsService.findById(id);
    }
    //Supprimer un champs

    @PostMapping("champs/delete")
    public ServeurResponse deletePays(@RequestBody Champs champs) {
    	List<ValueChamps> valeurs = valueService.listByChampId(champs.getChpId());
    	for(int i=0; i<valeurs.size(); i++) {
    		valueService.delete(valeurs.get(i));
    	}
        ServeurResponse response = new ServeurResponse();
        champsService.delete(champs);
        response.setStatut(true);
        return response;
    }

}
