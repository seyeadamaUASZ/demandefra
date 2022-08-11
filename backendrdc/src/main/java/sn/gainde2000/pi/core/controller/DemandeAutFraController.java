	
package sn.gainde2000.pi.core.controller;

import org.springframework.security.core.Authentication;
import sn.gainde2000.pi.core.entity.*;
import sn.gainde2000.pi.core.service.*;
import sn.gainde2000.pi.core.tools.Email;
import sn.gainde2000.pi.core.tools.TypeTransaction;
import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.service.IDocumentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.exolab.castor.xml.FieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.File;
import java.io.IOException;

@RestController
public class DemandeAutFraController {
	@Autowired
	IDemandeAutFraService demandeautfraService;
	@Autowired
	IProduitsService produitsService;	
	@Autowired
	IMotifrejetourenvoiService imotifrejetService;
	@Autowired
	IDocumentService documentService;
	@Autowired
	IUtilisateur userService;
	@Autowired
	IMotifrejetourenvoiService iMotifRenvoiService;
	@Autowired
	MailingService mailService;
	@Autowired 
	EntityManager entityManager;
	@Autowired
	Email email;

	@Autowired
	TransactionService transactionService;


	
    @PostMapping("demandeautfra/statistiques")
    public ServeurResponse getList(@Nullable  @RequestBody  SearchFilter search) {
    	ServeurResponse response = new ServeurResponse();
    	
    	String querystring = "select r from Produits r where r.demandeautfra.status=7  " ;
        
        
//        int i =0;
		for (Map.Entry<String, String> map : search.getFilter().entrySet()) {
			if (map.getValue()!=null && !map.getValue().trim().isEmpty()) {
//					if(i>=1) {

						if(map.getKey().equals("raisonsociale")) {
							querystring += " and " + "r." + map.getKey() + " like '%" + map.getValue() + "%'";
						}else if(map.getKey().equals("categorie")|| map.getKey().equals("status")){
							querystring+= " and " + "r."+map.getKey()+(map.getKey().equals("categorie")?".id":"")+" in ("+map.getValue()+") ";
						}else if(map.getKey().equals("statutJuridique")) {
							querystring+= " and " +  "r.demandeautfra."+map.getKey()+" in ("+spliteValues(map.getValue())+") ";

						}
						else {
							querystring+= " and " + "r.demandeautfra."+map.getKey()+".id in ("+map.getValue()+") ";

						}


			}
		}
		System.out.println(querystring);
		TypedQuery<Produits> query = entityManager.createQuery(querystring, Produits.class);
		List<Produits> list = query.getResultList();
		ArrayList<ProduitSearchResult> results = new ArrayList<>();
		for(Produits p: list) {
			ProduitSearchResult r = new ProduitSearchResult();
			r.setCategorie(p.getCategorie());
			r.setStatus(p.getStatus());
			r.setDemandeautfra(p.getDemandeautfra());
			r.setNature(p.getNature());
			r.setTypeemballage(p.getTypeemballage());
			 r.setMarque(p.getMarque());
			 r.setContenance(p.getContenance());
			  r.setDescriptionEtiquette(p.getDescriptionEtiquette());
			  r.setAutFra(p.getAutFra());
			results.add(r);
		}
		response.setStatut(true);
		response.setData(results);

    	
    	return response;
    }

    private String spliteValues(String vals) {
    	String[] vs = vals.split(",");
    	String vf = "";
    	int i =0;
    	for(String v : vs) {
    		if(i>0) {
    			vf+=",";
    		}
    		vf+="'"+v+"'";
    		i++;
    	}
    	return vf;
    	
    }
	@GetMapping("demandeautfra/list/{owner}")
	public ServeurResponse getAlldemandeAutFra(@PathVariable Long owner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listDemandeAutFraById(owner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/apayer/{owner}")
	public ServeurResponse getDemandeAPayer(@PathVariable Long owner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listDemandeAPayer(owner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/brouillon/{owner}")
	public ServeurResponse getAllDemandeEnBrouillon(@PathVariable Long owner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listDemandeEnBrouillon(owner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");
		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/demandeacorriger/{owner}")
	public ServeurResponse getAllDemandeACorriger(@PathVariable Long owner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listDemandeACorriger(owner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");
		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/demandeencours/{owner}")
	public ServeurResponse getAllDemandeEnCours(@PathVariable Long owner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listDemandeEnCours(owner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");
		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/autfraprodelivre/{owner}")
	public ServeurResponse getAutFraProDelivre(@PathVariable Long owner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listAutProDelivre(owner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");
		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/autfradefdelivre/{owner}")
	public ServeurResponse getAutFraDefDelivre(@PathVariable Long owner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listAutDefDelivre(owner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");
		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/demanderejetees/{owner}")
	public ServeurResponse getDemandesRejeteees(@PathVariable Long owner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listDemandeRejetees(owner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");
		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("demandeautfra/status/{id}/{status}")
	public ServeurResponse updateStatusDemandeAutFra(@PathVariable Long id, @PathVariable Long status) {
		DemandeAutFra demandeautfra = demandeautfraService.getOneDemandeAutFra(id);
		demandeautfra.setStatus(status);
		demandeautfra = demandeautfraService.save(demandeautfra);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@GetMapping("demandeautfra/task/{poowner}")
	public ServeurResponse getAllTask(@PathVariable Long poowner) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listDemandeAutFra(poowner);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/{id}")
	public ServeurResponse getDemandeById(@PathVariable Long id) {
		DemandeAutFra demandeautfra = demandeautfraService.getOneDemandeAutFra(id);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {

			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/list")
	public ServeurResponse getAlldemandeAutFra() {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.findAll();
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/mesdemandes/{email}")
	public ServeurResponse getMesDemandes(@PathVariable String email) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listeMesDemandes(email);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/mesdemandesrejetees/{email}")
	public ServeurResponse getMesDemandesRejetees(@PathVariable String email) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listeMesDemandesRejetees(email);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}
	
	@GetMapping("demandeautfra/mesdemandesacceptees/{email}")
	public ServeurResponse getMesDemandesAcceptees(@PathVariable String email) {
		Iterable<DemandeAutFra> demandeautfra = demandeautfraService.listeMesDemandesAcceptees(email);
		ServeurResponse response = new ServeurResponse();
		if (demandeautfra == null) {
			response.setStatut(false);
			response.setData(null);
			response.setDescription("Aucune élèment trouvé.");

		} else {
			response.setStatut(true);
			response.setData(demandeautfra);
			response.setDescription("Données récupérées.");
		}
		return response;
	}

	@PostMapping("demandeautfra/delete")
	public ServeurResponse delete(@RequestBody DemandeAutFra demandeautfra) {
		ServeurResponse response = new ServeurResponse();
		demandeautfraService.delete(demandeautfra);
		response.setStatut(true);
		response.setDescription("Données supprimé avec succès");
		return response;
	}

	@PostMapping(value = "demandeautfra/create", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServeurResponse createFiles(HttpServletRequest request, 
			@Nullable @RequestPart("certificatanalysesproduit") MultipartFile certificatanalysesproduit,
			@Nullable @RequestPart("registrecommerceetcreditmobilier") MultipartFile registrecommerceetcreditmobilier, 
			@Nullable @RequestPart("juridique") MultipartFile juridique,
			@Nullable @RequestPart("processusfabrication") MultipartFile processusfabrication, 
			@Nullable @RequestPart("copieninea") MultipartFile copieninea, 
			@Nullable @RequestPart("cnipasseport") MultipartFile cnipasseport) {
		ServeurResponse response = new ServeurResponse();
		DemandeAutFra demandeAutFra;
		try {
			demandeAutFra = new ObjectMapper().readValue(request.getParameter("demandeAutFra"),
					new TypeReference<DemandeAutFra>() {
					});
			if(certificatanalysesproduit != null) {
				demandeAutFra.setCertificatanalysesproduit(certificatanalysesproduit.getBytes());
			}
			
			if(registrecommerceetcreditmobilier != null) {
				demandeAutFra.setRegistrecommerceetcreditmobilier(registrecommerceetcreditmobilier.getBytes());
			} 
			
			if( juridique != null) {
				demandeAutFra.setJuridique(juridique.getBytes());
			} 
			
			if(processusfabrication != null) {
				demandeAutFra.setProcessusfabrication(processusfabrication.getBytes());
			}
			
			if(copieninea != null ) {				
				demandeAutFra.setCopieninea(copieninea.getBytes());
			}
			
			if(cnipasseport != null) {
				demandeAutFra.setCnipasseport(cnipasseport.getBytes());
			}
			demandeAutFra.setDateSoumission(new Date());
			demandeAutFra.setNumeroFacture(demandeautfraService.generateNumeroFature());
			DemandeAutFra demandewithid = demandeautfraService.save(demandeAutFra);
			
			if(!demandeAutFra.getProduits().isEmpty() || demandeAutFra.getProduits().size() > 0) {
				for(Produits dbp: demandeAutFra.getProduits()) {
					dbp.setDemandeautfra(demandewithid);
					dbp.setEtiquetteouemballage(dbp.getEtiquetteouemballage());
					produitsService.save(dbp);
					
				}
			}
			response.setStatut(true);
			response.setDescription("Enregistrement réussi");
			response.setData(demandeAutFra);
			if(demandewithid.getStatus() == 1L) {
				email.sendMail("no-reply@gainde2000.sn", demandewithid.getEmailResponsable(), "Votre demande a été enregistre en brouillon. Vous pouvez la reprendre quand vous voulez en utilisant votre adresse email et le numéro de dossier suivant: "+demandewithid.getNumdemande(), "Demande Autorisation FRA");
			} else {
				email.sendMail("no-reply@gainde2000.sn", demandewithid.getEmailResponsable(), "Bonjour "+demandewithid.getPrenomResponsable()+" "+demandewithid.getNomResponsable()+"\nNous accusons réception de votre demande d'autorisation FRA.\nPour pouvoir payer les frais d'analyse des produits,\nveuillez suivre votre dossier en utilisant le numéro suivant: "+demandewithid.getNumdemande(), "Demande Autorisation FRA");
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	@GetMapping("demandeautfra/downloadFile/certificatanalysesproduit/{id}")
	public ResponseEntity<ByteArrayResource> downloadfileCertificatanalysesproduit(
			@PathVariable String certificatanalysesproduit, @PathVariable Long id, HttpServletResponse response) {

		Optional<DemandeAutFra> data = demandeautfraService.findById(id);
		File file = new File(data.get().getNinea().toLowerCase());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=" + file.getName())
				.body(new ByteArrayResource(data.get().getCertificatanalysesproduit()));

	}

	@GetMapping("demandeautfra/downloadFile/registrecommerceetcreditmobilier/{id}")
	public ResponseEntity<ByteArrayResource> downloadfileRegistrecommerceetcreditmobilier(
			@PathVariable String registrecommerceetcreditmobilier, @PathVariable Long id,
			HttpServletResponse response) {

		Optional<DemandeAutFra> data = demandeautfraService.findById(id);
		File file = new File(data.get().getNinea().toLowerCase());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=" + file.getName())
				.body(new ByteArrayResource(data.get().getRegistrecommerceetcreditmobilier()));

	}

	@GetMapping("demandeautfra/downloadFile/juridique/{id}")
	public ResponseEntity<ByteArrayResource> downloadfileJuridique(@PathVariable String juridique,
			@PathVariable Long id, HttpServletResponse response) {

		Optional<DemandeAutFra> data = demandeautfraService.findById(id);
		File file = new File(data.get().getNinea().toLowerCase());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=" + file.getName())
				.body(new ByteArrayResource(data.get().getJuridique()));

	}

	@GetMapping("demandeautfra/downloadFile/processusfabrication/{id}")
	public ResponseEntity<ByteArrayResource> downloadfileProcessusfabrication(@PathVariable String processusfabrication,
			@PathVariable Long id, HttpServletResponse response) {

		Optional<DemandeAutFra> data = demandeautfraService.findById(id);
		File file = new File(data.get().getNinea().toLowerCase());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=" + file.getName())
				.body(new ByteArrayResource(data.get().getProcessusfabrication()));

	}

	@GetMapping("demandeautfra/downloadFile/copieninea/{id}")
	public ResponseEntity<ByteArrayResource> downloadfileCopieninea(@PathVariable String copieninea,
			@PathVariable Long id, HttpServletResponse response) {

		Optional<DemandeAutFra> data = demandeautfraService.findById(id);
		File file = new File(data.get().getNinea().toLowerCase());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=" + file.getName())
				.body(new ByteArrayResource(data.get().getCopieninea()));

	}

	@GetMapping("demandeautfra/downloadFile/cnipasseport/{id}")
	public ResponseEntity<ByteArrayResource> downloadfileCnipasseport(@PathVariable String cnipasseport,
			@PathVariable Long id, HttpServletResponse response) {

		Optional<DemandeAutFra> data = demandeautfraService.findById(id);
		File file = new File(data.get().getNinea().toLowerCase());
		System.out.println(file.getName());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=" + file.getName())
				.body(new ByteArrayResource(data.get().getCnipasseport()));

	}
	
	@GetMapping("demandeautfra/downloadAutFraPro/{id}")
    public ResponseEntity<ByteArrayResource> downloadFileAutFraPro(@PathVariable Long id) {
        Document data = documentService.findByDemande(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename="+data.getDctTitre())
                .body(new ByteArrayResource(data.getDctBlob()));
        
    }
	
	@GetMapping("demandeautfra/downloadAutFraDef/{id}")
    public ResponseEntity<ByteArrayResource> downloadFileAutFraDef(@PathVariable Long id) {
        Document data = documentService.findByProduits(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename="+data.getDctTitre())
                .body(new ByteArrayResource(data.getDctBlob()));
        
    }
	 
    @GetMapping("demandeautfra/atraiterchefbureau")
    public ServeurResponse getDemandeATraiterChefBureau() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraATraiterChefBureau();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @GetMapping("demandeautfra/atraiterchefdivision")
    public ServeurResponse getDemandeATraiterChefDivision() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraATraiterChefDivision();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    @GetMapping("demandeautfra/rejetees")
    public ServeurResponse getDemandeRejetees() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraRejetees();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("demandeautfra/renvoyees")
    public ServeurResponse getDemandeRenvoyer() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraRenvoyees();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("demandeautfra/renvoyeeschefdivision")
    public ServeurResponse getDemandeRenvoyees() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraRenvoyeesChefDivision();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("demandeautfra/demandeautfraterminee")
    public ServeurResponse getDemandeTerminees() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraTerminee();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("demandeautfra/demandeautfraprovisoire")
    public ServeurResponse getDemandeAutFraPro() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraDelivrePro();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("demandeautfra/demandeautfraatraiteranac")
    public ServeurResponse getDemandeAutFraATraiterAnac() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraATraiterANAC();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("demandeautfra/demandeautfratraiteranac")
    public ServeurResponse getDemandeAutFraTraiterAnac() {
        Iterable<DemandeAutFra> demandeAutFra = demandeautfraService.listeDemandeAutFraTraiterANAC();
        ServeurResponse response = new ServeurResponse();
        if (demandeAutFra == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {

            response.setStatut(true);
            response.setData(demandeAutFra);
            response.setDescription("Données récupérées.");
        }
        return response;
    }
    
    @GetMapping("demandeautfra/totaldemandeatraiter")
    private ServeurResponse totalDemandeATraiter() {
    	Integer totaldemandeatraiter = demandeautfraService.totalDemandeATraiter();
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre Commerciaux");
        response.setData(totaldemandeatraiter);
        return response;
    }
    
    @GetMapping("demandeautfra/totaldemandetraitees")
    private ServeurResponse totalDemandeTraiteesChefDivision() {
    	Integer totaldemandetraitees = demandeautfraService.totalDemandeTraitees();
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre Commerciaux");
        response.setData(totaldemandetraitees);
        return response;
    }
            
    @GetMapping("demandeautfra/totalproduitRejetes")
    private ServeurResponse totalDemandeRejetees() {
    	Integer totalproduitsRejetes = produitsService.totalDemandeRejetees();
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre Commerciaux");
        response.setData(totalproduitsRejetes);
        return response;
    }
    
    @GetMapping("demandeautfra/totalproduitAcceptes")
    private ServeurResponse totalDemandeTraitees() {
    	Integer totalproduitAcceptes = produitsService.totalDemandeAcceptees();
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre Commerciaux");
        response.setData(totalproduitAcceptes);
        return response;
    }
    
    @GetMapping("demandeautfra/totaldemandesoumise")
    private ServeurResponse totalDemandeSoumise() {
    	Integer totaldemandesoumise = demandeautfraService.totalDemandeSoumise();
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre Commerciaux");
        response.setData(totaldemandesoumise);
        return response;
    }
    
    @GetMapping("demandeautfra/totaldemandestraitees")
    private ServeurResponse totalDemandeTerminees() {
    	Integer totaldemandestraitees = demandeautfraService.totalDemandeTraitees();
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre Commerciaux");
        response.setData(totaldemandestraitees);
        return response;
    }

    @PostMapping("updateDemandeAutFraChefDeBureau")
    public ServeurResponse validerDemandeAutFraChefBureau(HttpServletRequest request) {
    	ServeurResponse response = new ServeurResponse();
    	Long idDemande = Long.parseLong(request.getParameter("id"));
    	DemandeAutFra demande = demandeautfraService.getOneDemandeAutFra(idDemande);
    	if(demande !=null) {
    		Long status = 3L;
    		Motifrejetourenvoi motifrejetourenvoi = new Motifrejetourenvoi();
    		motifrejetourenvoi.setIdLink(idDemande);
    		motifrejetourenvoi.setMotif(request.getParameter("commentaire"));
    		motifrejetourenvoi.setDateCommentaire(new Date());
    		motifrejetourenvoi.setPoOwner(Long.parseLong(request.getParameter("powner")));
    		motifrejetourenvoi.setOwner(Long.parseLong(request.getParameter("owner")));
    		motifrejetourenvoi.setStatus(status);
    		
    		demande.setStatus(status);
    		demandeautfraService.updateDemandeAutFra(idDemande, demande);
    		//add motifrejet
    		imotifrejetService.save(motifrejetourenvoi);
    		response.setStatut(true);
    		response.setData(demande);
    		response.setDescription("demande validee !");
    	}else {
    		response.setStatut(false);
    		response.setData(null);
    		response.setDescription("demande non validee !");
    	}
    	return response;
    }
    
    @PostMapping("rejetDemandeAutFraChefDeBureau")
    public ServeurResponse rejetDemandeChefDeBureau(HttpServletRequest request) {
    	ServeurResponse response = new ServeurResponse();
    	Long idDemande = Long.parseLong(request.getParameter("id"));
    	DemandeAutFra demande = demandeautfraService.getOneDemandeAutFra(idDemande);
    	if(demande !=null) {
    		Long status = 9L;
    		Motifrejetourenvoi motifrejetourenvoi = new Motifrejetourenvoi();
    		motifrejetourenvoi.setIdLink(idDemande);
    		motifrejetourenvoi.setMotif(request.getParameter("motif"));
    		motifrejetourenvoi.setDateCommentaire(new Date());
    		motifrejetourenvoi.setPoOwner(Long.parseLong(request.getParameter("powner")));
    		motifrejetourenvoi.setOwner(Long.parseLong(request.getParameter("owner")));
    		motifrejetourenvoi.setStatus(status);
    		
    		demande.setStatus(status);
    		demandeautfraService.updateDemandeAutFra(idDemande, demande);
    		//add motifrejet
    		imotifrejetService.save(motifrejetourenvoi);
    		response.setStatut(true);
    		response.setData(demande);
    		response.setDescription("demande validee !");
    		mailService.sendEmail("no-reply@gainde2000.sn", demande.getEmailResponsable(), "Bonjour,\n Nous sommes désolé de vous informer que votre demande a été renvoyé.\n Veuillez corriger votre dossier et le renvoyer.", "\n Demande Autorisation FRA");
    	}else {
    		response.setStatut(false);
    		response.setData(null);
    		response.setDescription("demande non validee !");
    	}
    	return response;
    }
    
    @GetMapping("demandeAutFra/nombreDemandeRenvoyeeChefBureau")
    public ServeurResponse nombreDemandesRejeteesChefBureau() {
    	ServeurResponse response = new ServeurResponse();
    	Integer nombreRejetee = demandeautfraService.nombreDemandeRenvoyeesChefBureau();
    	response.setStatut(true);
		response.setData(nombreRejetee);
		response.setDescription("nombre rejete !");
		return response;
    }
    
    @GetMapping("demandeAutFra/nombreDemandeATraiterChefBureau")
    public ServeurResponse nombreDemandesAtraiterChefBureau() {
    	ServeurResponse response = new ServeurResponse();
    	Integer nombreATraiter = demandeautfraService.nombreDemandeATraiterChefBureau();
    	response.setStatut(true);
		response.setData(nombreATraiter);
		response.setDescription("nombre a traiter!");
		return response;
    }
    
    @GetMapping("demandeAutFra/nombreDemandeATraiterChefDivision")
    public ServeurResponse nombreDemandesAtraiterChefDivision() {
    	ServeurResponse response = new ServeurResponse();
    	Integer nombreATraiterChef = demandeautfraService.nombreDemandeATraiterChefDivision();
    	response.setStatut(true);
		response.setData(nombreATraiterChef);
		response.setDescription("nombre a traiter!");
		return response;
    }
    
    @GetMapping("demandeAutFra/nombreDemandeATraiterANAC")
    public ServeurResponse nombreDemandesATraiterAnac() {
    	ServeurResponse response = new ServeurResponse();
    	Integer nombreATraiter = demandeautfraService.nombreDemandeATraiterANAC();
    	response.setStatut(true);
		response.setData(nombreATraiter);
		response.setDescription("nombre a traiter!");
		return response;
    }
    
    @GetMapping("demandeAutFra/nombreDemandeTraiterANAC")
    public ServeurResponse nombreDemandesTraiterAnac() {
    	ServeurResponse response = new ServeurResponse();
    	Integer nombreATraiter = demandeautfraService.nombreDemandeTraiterANAC();
    	response.setStatut(true);
		response.setData(nombreATraiter);
		response.setDescription("nombre a traiter!");
		return response;
    }
    
    @GetMapping("demandeAutFra/nombreDemandeSoumises/{email}")
    public ServeurResponse nombreDemandeSoumise(@PathVariable String email) {
    	ServeurResponse response = new ServeurResponse();
    	Integer nombreDemandeSoumise = demandeautfraService.nombreDemandeSoumises(email);
    	response.setStatut(true);
		response.setData(nombreDemandeSoumise);
		response.setDescription("nombre demandes soumises!");
		return response;
    }
    
    @GetMapping("demandeAutFra/nombreDemandeEnCours/{email}")
    public ServeurResponse nombreDemandeEnCours(@PathVariable String email) {
    	ServeurResponse response = new ServeurResponse();
    	Integer nombreDemandeEnCours = demandeautfraService.nombreDemandeEnCours(email);
    	response.setStatut(true);
		response.setData(nombreDemandeEnCours);
		response.setDescription("nombre demandes en cours!");
		return response;
    }
    
    @GetMapping("demandeAutFra/statistiqueChefAntenne/{owner}")
    public ServeurResponse statistiquesChefAntenne(@PathVariable Long owner) {
    	ServeurResponse response = new ServeurResponse();
    	List<Object[]> statistique = demandeautfraService.statistiquesChefAntenne(owner);
    	response.setStatut(true);
		response.setData(statistique);
		response.setDescription("statistique chef antenne!");
		return response;
    }
    
    @GetMapping("demandeAutFra/statistiqueDemande/{email}")
    public ServeurResponse statistiquesDemandeur(@PathVariable String email) {
    	ServeurResponse response = new ServeurResponse();
    	List<Object[]> statistique = demandeautfraService.statistiqueDemandeur(email);
    	response.setStatut(true);
		response.setData(statistique);
		response.setDescription("statistique demandeur!");
		return response;
    }
    
    @GetMapping("demandeAutFra/statistiqueLanac")
    public ServeurResponse statistiqueLanac() {
    	ServeurResponse response = new ServeurResponse();
    	List<Object[]> statistique = demandeautfraService.statistiqueLanac();
    	response.setStatut(true);
		response.setData(statistique);
		response.setDescription("statistique lanac!");
		return response;
    }
    
    @PostMapping("demandeautfra/suivredemande")
    public ServeurResponse suivreDemandeByEmailAndNumero(HttpServletRequest request) {
    	ServeurResponse response = new ServeurResponse();
        String email = request.getParameter("emailEntreprise");
		System.out.println("email responsable: "+email);
        String numero = request.getParameter("numdemande");
    	DemandeAutFra demandeAutFra = demandeautfraService.getDemandeAutFraByEmailAndNumero(email, numero);

    	if (demandeAutFra !=null) {
    		response.setData(demandeAutFra);
    		response.setStatut(true);
    		response.setDescription("demandefra !!!");
    	}else {
    		response.setData(null);
    		response.setDescription("Email ou numéro demande non conforme !!");
    		response.setStatut(false);
    	}
    	return response;
    }

	@PostMapping("demandeautfra/suivredemand")
	public ServeurResponse suivreDemandeByEmailAndNumero1(@RequestBody SuivreDemandeRequest request) {
		ServeurResponse response = new ServeurResponse();
		String email = request.getEmailEntreprise();
		String numero = request.getNumdemande();
		DemandeAutFra demandeAutFra = demandeautfraService.getDemandeAutFraByEmailAndNumero(email,numero);
		//Utilisateur utilisateur = userService.findByUtiUsername(authentication.getName());
		/*Transaction transaction = new Transaction();
		if (utilisateur!=null){
			transaction.setUtilisateur(utilisateur);
		}*/
		if (demandeAutFra !=null) {
			response.setData(demandeAutFra);
			response.setStatut(true);
			response.setDescription("demandefra !!!");

		}else {
			response.setData(null);
			response.setDescription("Email ou numéro demande non conforme !!");
			response.setStatut(false);
		}
		/*transaction.setTypeTransaction(TypeTransaction.SUIVRE_DEMANDE_AUT_FRA);
		transaction.setDateTransaction(new Date());
		transaction.setDescription(response.getDescription());
		transaction.setNomApplication("AUTORISATION_FRA");
		transactionService.addTransaction(transaction);*/

		return response;
	}



    
    @GetMapping("demandeautfra/nombredemandeautfrabypowner/{powner}")
    public ServeurResponse nombreDemandeAutFraByPowner(@PathVariable Long powner) {
    	ServeurResponse response = new ServeurResponse();
    	List<Object[]> nombres = demandeautfraService.nbrDemandeAutFraByDate(powner);
    	if(nombres.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(nombres);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @GetMapping("demandeautfra/nombredemandebatontraitees")
    public ServeurResponse nbDemandeTraitees() {
    	ServeurResponse response = new ServeurResponse();
    	List<Object[]> nombres = demandeautfraService.nbDemandeTraitees();
    	if(nombres.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(nombres);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @GetMapping("demandeautfra/nombredemandeautfrabymois/{owner}")
    public ServeurResponse nombreDemandeAutFraByMois(@PathVariable Long owner) {
    	ServeurResponse response = new ServeurResponse();
    	List<Object[]> nombres = demandeautfraService.nbrDemandeAutFraByMois(owner);
    	if(nombres.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(nombres);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @GetMapping("demandeautfra/nombredemandeautfrabyannee")
    public ServeurResponse nombreDemandeAutFraByAnnee() {
    	ServeurResponse response = new ServeurResponse();
    	List<Object[]> nombres = demandeautfraService.nbrDemandeAutFraByAnnee();
    	if(nombres.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(nombres);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @GetMapping("demandeautfra/nombrecirculaire/{email}")
    public ServeurResponse circulaireDemandesAutFra(@PathVariable String email) {
    	ServeurResponse response = new ServeurResponse();
    	List<Map<String, Object>> circulaires = demandeautfraService.circulaireDemandeur(email);
    	if(circulaires.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(circulaires);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @GetMapping("demandeautfra/nombrecirculairechefbureaudivision/{powner}")
    public ServeurResponse circulaireChefBureauAndDivision(@PathVariable Long powner) {
    	ServeurResponse response = new ServeurResponse();
    	List<Map<String, Object>> circulaires = demandeautfraService.circulaireChefBureauAndDivision(powner);
    	if(circulaires.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(circulaires);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @GetMapping("demandeautfra/nombrecirculairechefdivision")
    public ServeurResponse circulaireChefDivision() {
    	ServeurResponse response = new ServeurResponse();
    	List<Map<String, Object>> circulaires = demandeautfraService.circulaireDivision();
    	if(circulaires.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(circulaires);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @GetMapping("demandeautfra/nombrecirculaireantenne/{owner}")
    public ServeurResponse circulaireDemandeAntenne(@PathVariable Long owner) {
    	ServeurResponse response = new ServeurResponse();
    	List<Map<String, Object>> circulaireAntenne = demandeautfraService.circulaireDemandeAntenne(owner);
    	if(circulaireAntenne.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(circulaireAntenne);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @GetMapping("demandeautfra/nombrecirculairlaborantin")
    public ServeurResponse circulaireDemandeLaborantin() {
    	ServeurResponse response = new ServeurResponse();
    	List<Map<String, Object>> circulaireLaborantin = demandeautfraService.circulaireDemandeLaborantion();
    	if(circulaireLaborantin.isEmpty()) {
    		response.setData(null);
    		response.setDescription("pas d'aggrégation !!");
    		response.setStatut(false);
    	}else {
    		response.setData(circulaireLaborantin);
    		response.setDescription("aggrégation !!");
    		response.setStatut(true);
    	}
    	return response;
    }
    
    @PostMapping(value = "demandeautfra/contact")
    public ServeurResponse sendMailContact(HttpServletRequest request) throws MessagingException {
    	ServeurResponse response = new ServeurResponse();
    	String nom,email,message = null;
    	nom = request.getParameter("nom");
    	email = request.getParameter("email");
    	message = request.getParameter("message");
        Boolean emailContact = demandeautfraService.mailContact(email, nom, message);

        if(emailContact == true) {

        	response.setData(emailContact);
        	response.setDescription("Mail Envoyé avec succès");
        	response.setStatut(true);
        } else {

        	response.setData(null);
        	response.setDescription("Une erreur est survenue");
        	response.setStatut(false);
        }

        return response;
    }
    
    @PostMapping(value="demandeautfra/paiementmanuel", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ServeurResponse payerManuellement(HttpServletRequest request,@RequestPart("recuPaiement") MultipartFile recuPaiement) {
    	ServeurResponse response = new ServeurResponse();
    	DemandeAutFra demandeAutFra = null;
    	Long id = Long.parseLong(request.getParameter("id"));
    	String typeRecu = request.getParameter("typeFileRecu");
    	demandeAutFra = demandeautfraService.findById(id).get();
    	if(recuPaiement != null) {
    		try {
				demandeAutFra.setRecuPaiment(recuPaiement.getBytes());
				demandeAutFra.setStatus(2L);// changement de status à deja paye
				demandeAutFra.setRecuFileType(typeRecu);
				demandeAutFra.setPaiementManuel(true);
				demandeautfraService.save(demandeAutFra);
				response.setStatut(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else {
    		
    		response.setStatut(false);
    		response.setDescription("uploader un recu");
    	}
    	return response;
    }
    
    @PostMapping(value="demandeautfra/rejetpaiement")
    public ServeurResponse rejetPaiement(HttpServletRequest request) {
    	ServeurResponse response = new ServeurResponse();
    	DemandeAutFra demandeAutFra = null;
    	Long id = Long.parseLong(request.getParameter("id"));
    	demandeAutFra = demandeautfraService.findById(id).get();
    	demandeAutFra.setStatus(11L);
    	demandeautfraService.save(demandeAutFra);
		mailService.sendEmail("no-reply@gainde2000.sn", demandeAutFra.getEmailResponsable(), "Bonjour,\n Nous sommes désolé de vous informer que votre demande a été renvoyé.\n Veuillez télécharger un reçu de paiement valide et le renvoyer.", "\n Demande Autorisation FRA");

    	response.setStatut(true);
    	return response;
    }
}