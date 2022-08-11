
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.controller;


import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Filter;
import sn.gainde2000.pi.core.entity.JbpmUserMapping;
import sn.gainde2000.pi.core.entity.Motifrejetourenvoi;
import sn.gainde2000.pi.core.entity.PasswordRecover;
import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.PwdCriteria;
import sn.gainde2000.pi.core.entity.TypeDeNotification;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.repository.*;
import sn.gainde2000.pi.core.service.IApplicationService;
import sn.gainde2000.pi.core.service.IHistoriqueSession;
import sn.gainde2000.pi.core.service.IJbpmUserService;
import sn.gainde2000.pi.core.service.IProfile;
import sn.gainde2000.pi.core.service.IPwdCriteriaService;
import sn.gainde2000.pi.core.service.ISession;
import sn.gainde2000.pi.core.service.ITypeDeNotificationService;
import sn.gainde2000.pi.core.service.IUtilisateur;
import sn.gainde2000.pi.core.tools.Email;
import sn.gainde2000.pi.core.tools.PasswordGenerator;
import sn.gainde2000.pi.core.tools.PasswordValidator;

/**
 *
 * @author yougadieng
 */
@RestController
@CrossOrigin("*")
@RequestMapping("utilisateur")
public class UtilisateurController {

    @Autowired
    IUtilisateur userService;
    @Autowired
    ISession sessionService;
    @Autowired
    IHistoriqueSession histSessionService;
    @Autowired
    IApplicationService applicationRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    PassRecovRepository recovRepository;
    @Autowired
    IJbpmUserService jbpmUserRepository;

    @Autowired
    IPwdCriteriaService pwdCriteriaService;
    @Autowired
    ITypeDeNotificationService typService;

    @Autowired
    Email email;
   
    @Autowired
    private HttpServletRequest serveletRequest;

    @Autowired
    IProfile profilService;
      @Autowired
    EntityManager entityManager;
     @PostMapping("search")
    public ServeurResponse search(@RequestBody Filter filter) {
          StringBuilder querystring= new StringBuilder();
          querystring = new StringBuilder("select r from Utilisateur r where ");
        int i = 0;
        boolean ok = false;
        for (Map.Entry<String, String> map : filter.getObject().entrySet()) {
            if (map.getValue() != null && !map.getValue().trim().isEmpty()) {
               // System.out.println("value----------"+map.getValue());
                System.out.println("value----------"+map.getKey());
                if(map.getKey().equals("proLibelle")){
                   System.out.println("okkkkk");
                   querystring = new StringBuilder("select r from Utilisateur r left join Profil p on r.uti_pro_id=p.proId where p.proLibelle like '%" + map.getValue() + "%'");
                   i=8;
                }

                if (i >= 1 && i!=8) {
                    // System.out.println("---------youpiii-----------" + querystring);

                    querystring.append(" and " + "r.").append(map.getKey()).append(" like '%").append(map.getValue()).append("%'");
                } 
                if(i==0) {
                    querystring.append(" r.").append(map.getKey()).append(" like '%").append(map.getValue()).append("%'");
                }

                /*else {
                    querystring += " and " + "r." + map.getKey() + ">=" + map.getValue();
                }
                 */
                i++;

            }
        }
        System.out.println("--------------------" + querystring);

        TypedQuery<Utilisateur> query = entityManager.createQuery(querystring.toString(), Utilisateur.class);
        List<Utilisateur> list = query.getResultList();
        if (list != null) {
            return new ServeurResponse(true, "list User", list, null);

        } else {
            return new ServeurResponse(false, "list User", list, null);

        }
    }

    

    /**
     * Liste tous les utilisateurs
     */
    @GetMapping("list")
    public ServeurResponse getAllUsers() {
        //System.out.println("-----------------------------serveletRequest.getRequestURI()-------------------"+serveletRequest.getRequestURI());       
        //System.out.println("-----------------------------serveletRequest.getServletPath()-------------------"+serveletRequest.getServletPath());        

        Iterable<Utilisateur> users = userService.listUtilisateur();
        ServeurResponse response = new ServeurResponse();
        if (users == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {
            response.setStatut(true);
            response.setData(users);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * Liste les utilisateurs par profil donné
     *
     * @param profil
     * @return ServeurResponse
     */
    @PostMapping("listUsersbyprofil")
    public ServeurResponse getUsersByProfil(@RequestBody Profil profil) {
        Iterable<Utilisateur> users = userService.findUsersByUtiProfil(profil);
        ServeurResponse response = new ServeurResponse();
        if (users == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Aucune élèment trouvé.");

        } else {
            response.setStatut(true);
            response.setData(users);
            response.setDescription("Données récupérées.");
        }
        return response;
    }

    /**
     * Récupère les informations détaillées de l'utiliateur connecté
     *
     * @param authentication
     * @return
     */
    @RequestMapping(value = "/infos", method = RequestMethod.GET)
    @ResponseBody
    public Utilisateur currentUserDetail(Authentication authentication) {
        ServeurResponse response = new ServeurResponse();
        Utilisateur user = userService.findByUtiUsername(authentication.getName());
        if (userService.findByUtiUsername(authentication.getName()) == null) {
            response.setStatut(false);
            response.setDescription("Cet utilisatueur n'existe pas.");
            response.setData(null);
        } else {
            try {
                if (user.getUtiLogo() != null) {
                    user.setUtiLogo(decompressBytes(user.getUtiLogo()));
                }
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
            response.setStatut(true);
            response.setData(user);

        }
        return user;
    }
    
    @GetMapping("username/{username}")
    public ServeurResponse UtilisateurParUsername(@PathVariable String username) {
    	ServeurResponse response = new ServeurResponse();
    	Utilisateur user = userService.findByUtiUsername(username);
    	if(user != null) {    		
    		response.setStatut(true);
    		response.setData(user);
    		response.setDescription("données recupérées avec succés!");
    	}else {
    		response.setStatut(false);
    		response.setData(null);
    		response.setDescription("demande non recupérées!");
    	}
    	return response;
    }
    

    /**
     * Met à jour les information de l'utilisateur
     *
     * @param authentication
     * @param userDetails
     * @return
     */
    @PostMapping("updateinfos")
    public ServeurResponse updateinfosUserDetail(Authentication authentication, @Valid @RequestBody Utilisateur userDetails) {
        ServeurResponse response = new ServeurResponse();
        Utilisateur user = userService.findByUtiUsername(authentication.getName());
        if (user == null) {
            response.setStatut(false);
            response.setDescription("Cet utilisatueur n'existe pas.");
            response.setData(null);
        } else {
            user.setUtiDateModification(new Date());
            user.setUtiNom(userDetails.getUtiNom());
            user.setUtiPrenom(userDetails.getUtiPrenom());
            user.setUtiAdresse(userDetails.getUtiAdresse());
            user.setUtiTelephone(userDetails.getUtiTelephone());
            user.setUtiEmail(userDetails.getUtiEmail());
            user.setUti_lng_id(userDetails.getUti_lng_id());
            user.setUti_thm_id(userDetails.getUti_thm_id());
            this.userService.addUtilisateur(user);
            try {
                if (user.getUtiLogo() != null) {
                    user.setUtiLogo(decompressBytes(user.getUtiLogo()));
                }
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
            response.setStatut(true);
            response.setData(user);
            response.setDescription("infos mis ajour.");
            /*sms.sendSms("775449216", "infos utilisaeur mis ajour");*/
        }

        return response;
    }

    /**
     * Ajoute une photo de l'utilisateur
     *
     * @param authentication
     * @param file
     * @return ServeurResponse
     * @throws IOException
     */
    @PostMapping("updatelogo")
    public ServeurResponse uplaodLogo(Authentication authentication, @RequestParam("imageFile") MultipartFile file) throws IOException {
        ServeurResponse response = new ServeurResponse();
        Utilisateur user = userService.findByUtiUsername(authentication.getName());
        byte[] logo = compressBytes(file.getBytes());
        try {
            user.setUtiLogo(logo);
            userService.addUtilisateur(user);
            response.setStatut(true);
            response.setDescription("Enregistrement reussi!");
            response.setData(user);
        } catch (Exception e) {
            response.setStatut(false);
            response.setDescription("erreure serveur");
            response.setData(user);
        }

        return response;
    }

    /**
     * Récupère la photo de l'utilisateur
     *
     * @param authentication
     * @return ServeurResponse
     * @throws IOException
     */
    @GetMapping("getlogouser")
    public ServeurResponse getLogoUser(Authentication authentication) throws IOException {
        ServeurResponse response = new ServeurResponse();
        Utilisateur user = userService.findByUtiUsername(authentication.getName());
        try {
            user.setUtiLogo(decompressBytes(user.getUtiLogo()));
            response.setStatut(true);
            response.setDescription("Enregistrement reussi!");
            response.setData(user);
        } catch (DataFormatException e) {
            response.setStatut(false);
            response.setDescription("erreur serveur");
            response.setData(null);
        }
        return response;
    }

    /**
     * Compresse une donnée byte pour réduire la taille
     *
     * @param data
     * @return static byte[]
     */
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toByteArray();
    }

    /**
     * decompressé la taille d'une donnée avant de le retourner sur application
     * angular
     *
     * @param data
     * @return static byte[]
     * @throws DataFormatException
     */
    public static byte[] decompressBytes(byte[] data) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        }
        return outputStream.toByteArray();
    }

    /**
     * Récupère le detail du compte de l'utilisateur
     *
     * @param request
     * @return ServeurResponse
     */
    @PostMapping(value = "detail", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ServeurResponse getUser(HttpServletRequest request) {
        String username = (String) request.getParameter("username");
        Utilisateur user = userService.findByUtiUsername(username);
        ServeurResponse response = new ServeurResponse();
        if (user == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("L'utilisateur '" + username + "' n'a pas été trouvé.");
        } else {
            response.setStatut(true);
            user.setUtiPassword("permission denied");
            response.setData(user);
            response.setDescription("L'utilisateur '" + username + "' a été trouvé.");
        }
        return response;
    }

    /**
     * Créer un compte utilisateur
     *
     * @param user
     * @return ServeurResponse
     * @throws IOException
     * @throws MessagingException
     */
    @PostMapping("create")
    public ServeurResponse create(@RequestBody Utilisateur user) throws IOException, MessagingException {
        String npwd = PasswordGenerator.GenerateRandomString();
        user.setUtiPassword(encoder().encode(npwd));
        user.setUtiActived(true);
        user.setUtiDateCreation(new Date());
        user.setUtiPremierConnect(true);
        ServeurResponse response = new ServeurResponse();
        if (userService.findUsersByUtiEmail(user.getUtiEmail()) == 0) {
            if (userService.findByUtiUsername(user.getUtiUsername()) == null) {
                userService.addUtilisateur(user);                
                response.setStatut(true);                
                response.setDescription("Enregistrement réussi");
                response.setData(user);
                email.sendMail("no-reply@gainde2000.sn", user.getUtiEmail(), "Bonjour "+user.getUtiPrenom()+" "+user.getUtiNom()+",\n Votre compte utilisateur a été créé. \n Vos paramétres de connexion sont: \n Nom d'utilisateur: "+user.getUtiUsername()+" \n Mot de passe: "+npwd, "Création de compte utilisateur");
            } else {
                response.setStatut(false);
                response.setDescription("Le nom d'utilisateur est déjà  attribué.");
                response.setData(null);
            }
        } else {
            response.setStatut(false);
            response.setDescription("L'email est déjà  attribué.");
            response.setData(null);
        }

        return response;
    }

    public JbpmUserMapping InstertToJbpmMapping(JbpmUserMapping jbpmUserMapping) {

        return jbpmUserRepository.addUserJbpm(jbpmUserMapping);
    }

    /**
     * Mets à jour un compte utilisateur
     *
     * @param user
     * @return ServeurResponse
     */
    @PostMapping("update")
    public ServeurResponse update(@RequestBody Utilisateur user, HttpServletResponse responseHttp) {
        ServeurResponse response = new ServeurResponse();
        String notif = "";
        Utilisateur findUser = userService.findByUtiUsername(user.getUtiUsername());
        if (findUser != null) {
            findUser.setUtiDateModification(new Date());
            findUser.setUtiPrenom(user.getUtiPrenom());
            findUser.setUtiNom(user.getUtiNom());
            findUser.setUtiTelephone(user.getUtiTelephone());
            findUser.setUtiEmail(user.getUtiEmail());
            findUser.setUtiAdresse(user.getUtiAdresse());
            findUser.setUti_pro_id(user.getUti_pro_id());
            this.userService.addUtilisateur(findUser);
            notif = user.getUtiPrenom() + "$";
            notif = notif + user.getUtiNom() + "$";
            notif = notif + user.getUtiEmail() + "$";
            notif = notif + user.getUtiTelephone() + "$";
            notif = notif + user.getUtiAdresse() + "$";
            //response.setNotifInfos(notif);
            response.setStatut(true);
            response.setDescription("Enregistrement réussi");
            response.setData(findUser);
        } else {
            response.setStatut(false);
            response.setDescription("Echec d'enregistrement");
        }
        System.out.println("------------------------notifinfos------------------------" + notif);
        responseHttp.setHeader("notifinfos", notif);
        return response;
    }

    /**
     * Récupère les infos de l'utilisateur
     *
     * @param id
     * @return Optional<Utilisateur>
     */
    @GetMapping("/{id}")
    public Optional<Utilisateur> findByid(@PathVariable Long id) {
        return userService.findById(id);
    }

    /**
     * Supprimer un utilisateur
     *
     * @param user1
     * @return ServeurResponse
     */
    @PostMapping("delete")
    public ServeurResponse delete(@RequestBody Utilisateur user1) {
        userService.deleteUtilisateur(user1);
        ServeurResponse response = new ServeurResponse();
        Utilisateur user = userService.findByUtiUsername(user1.getUtiUsername());
        if (user == null) {
            response.setStatut(true);
            response.setDescription("Suppression réussie.");
            response.setData(null);
        } else {
            response.setStatut(false);
            response.setDescription("Echec suppression.");
            response.setData(user);
        }
        return response;
    }

    /**
     * Récupère les paramètres concernant l'utilisateur (langue, theme,...)
     *
     * @param username
     * @return ServeurResponse
     */
    @GetMapping("get/{username}")
    public ServeurResponse getParametre(@PathVariable String username) {
        ServeurResponse response = new ServeurResponse();
        Utilisateur user = userService.verifyLangueEtTheme(username);
        if (user != null) {
            response.setStatut(true);
            response.setDescription("Langue et theme du user récupérés avec succès");
            response.setData(user);
        } else {
            response.setStatut(true);
            response.setDescription("Echec récupération");
            response.setData(null);
        }
        return response;
    }

    /**
     * Changer le mot de passe de l'utilisateur
     *
     * @param request
     * @return ServeurResponse
     */
    //@RequestMapping(value = "changerpwd", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    //@RequestMapping(value = "changerpwd", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PostMapping(value = "changerpwd")
    public ServeurResponse changerPwd(HttpServletRequest request) {
        ServeurResponse response = new ServeurResponse();
        String username = (String) request.getParameter("username");
        String oldpwd = (String) request.getParameter("oldpwd");
        String vrfpwd = (String) request.getParameter("newpwd");
        String newpwd = (String) request.getParameter("newpwd");
        newpwd = encoder().encode(newpwd);
        Utilisateur user = userService.findByUtiUsername(username);
        //Boolean rep = passwordValidator.validate(vrfpwd);     
        if (user != null) {
            if (!encoder().matches(oldpwd, user.getUtiPassword())) {
                response.setStatut(false);
                response.setDescription("L'ancien mot de passe est incorrect. Veuillez réessayer.");
            } else {
                PwdCriteria pwd = pwdCriteriaService.getListPwd().get(0);
                Boolean rep = true;
                if (pwd != null) {
                    PasswordValidator passwordValidator = new PasswordValidator();
                    rep = passwordValidator.validate(vrfpwd, pwd.getPwdCarMin(), pwd.getPwdDigMin(), pwd.getPwdMajMin(), pwd.getPwdSpcMin());
                }
                if (rep != false) {
                    user.setUtiPassword(newpwd);
                    user.setUtiPremierConnect(false);
                    user.setUtiPwdDateModif(new Date());
                    userService.addUtilisateur(user);
                    response.setStatut(true);
                    response.setDescription("Le mot de passe est modifié.");
                } else {
                    response.setStatut(false);
                    response.setDescription("Le mot de passe doit comporter entre " + pwd.getPwdCarMin() + " et 20 caractères dont au moins une minuscule, au moins " + pwd.getPwdMajMin() + " majuscule(s), au moins " + pwd.getPwdDigMin() + " chiffre(s), au moins " + pwd.getPwdSpcMin() + " caractère(s) spécial(s)!");
                }
            }
        } else {
            response.setStatut(false);
            response.setDescription("Aucun utilisateur n'a été trouvé");
        }

        return response;
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Réinitialise le mot de passe de l'utilisateur
     *
     * @param user
     * @return ServeurResponse
     */
    @PostMapping("reinitialisation")
    private ServeurResponse reinit(@RequestBody Utilisateur user) {
        ServeurResponse response = new ServeurResponse();
        user.setUtiPassword(encoder().encode(PasswordGenerator.GenerateRandomString()));
        userService.addUtilisateur(user);
        if (user != null) {
            response.setStatut(true);
            response.setDescription("Réinitialisation réussie");
        }
        return response;
    }

    /**
     * Vérifier le token de l'utilisateur
     *
     * @param token
     * @return ServeurResponse
     * @throws NoSuchAlgorithmException
     * @throws MessagingException
     */
    @GetMapping("/veriftoken/{token}")
    public ServeurResponse getTkn(@PathVariable String token) throws NoSuchAlgorithmException, MessagingException {
        ServeurResponse response = new ServeurResponse();
        String tkn = recovRepository.verifTkn(token, new Date());
        if (tkn == null) {
            response.setStatut(false);
            response.setData(null);
            response.setDescription("Le token est expiré .");
        } else {
            response.setStatut(true);
            String email1 = recovRepository.recupEmail(token);
            Utilisateur utilisateur = userService.findByUtiEmail(email1);
            utilisateur.setUtiPremierConnect(true);
            String npwd = PasswordGenerator.GenerateRandomString();
            utilisateur.setUtiPassword(encoder().encode(npwd));
            userService.addUtilisateur(utilisateur);
            String from = "no-reply@gainde2000.sn";
            String to = email1;
            String subject = "Réinitialisation du mot de passe";
            String message = "Votre mot de passe par defaut est : " + npwd;
            //Email email = new Email();
            email.sendMail(from, to, message, subject);
            response.setDescription("Un Email contenant votre mot de passe par defaut vous a  été envoyé");
        }
        return response;
    }

    /**
     * Envoie de token au mail
     *
     * @param passwordRecover
     * @return ServeurResponse
     * @throws NoSuchAlgorithmException
     * @throws MessagingException
     * @throws javax.mail.MessagingException
     */
    @PostMapping("recover")
    public ServeurResponse addEmailToken(@RequestBody PasswordRecover passwordRecover) throws NoSuchAlgorithmException, MessagingException, javax.mail.MessagingException {
        ServeurResponse response = new ServeurResponse();
        if (userService.findByUtiEmail(passwordRecover.getPwrEmail()) == null) {
            response.setStatut(false);
            response.setDescription("Cet email n'existe pas !");
            response.setData(null);
        } else {
            byte[] salt = getSalt();
            //byte salt[] = new byte[6];
            String securePassword = get_SHA_1_SecurePassword("123456789", salt);
            Date toom = DateUtils.addDays(new Date(), +1);
            passwordRecover.setPwrDateCreation(new Date());
            passwordRecover.setPwdDateExpiration(toom);
            passwordRecover.setPwrEmail(passwordRecover.getPwrEmail());
            passwordRecover.setPwrResetToken(securePassword);
            recovRepository.save(passwordRecover);
            String userName = passwordRecover.getPwrEmail();
            String from = "no-reply@gainde2000.sn";
            String to = passwordRecover.getPwrEmail();
            String subject = "Réinitialisation du mot de passe";
            String message = "Votre processus de réinitialisation de mot de passe en cours.\nVeuillez finaliser le processus en saisissant ces identifiants de vérification:\t" + securePassword;
            email.sendMail(from, to, message, subject);
            response.setStatut(true);
            response.setDescription("Un email de vérification vous a été envoyé ");
            response.setData(passwordRecover);
        }
        return response;

    }

    /**
     * Termine l'action de réinitialisation du mot de passe
     *
     * @param request
     * @return ServeurResponse
     */
    @RequestMapping(value = "resetpwd", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ServeurResponse resetPwd(HttpServletRequest request) {
        ServeurResponse response = new ServeurResponse();
        String email = (String) request.getParameter("email");
        String oldpwd = (String) request.getParameter("oldpwd");
        String newpwd = (String) request.getParameter("newpwd");
        newpwd = encoder().encode(newpwd);
        Utilisateur user = userService.findByUtiUsername(email);
        if (user != null) {
            if (!encoder().matches(oldpwd, user.getUtiPassword())) {
                response.setStatut(false);
                response.setDescription("L'ancien mot de passe est incorrect. Veuillez réessayer.");
            } else {
                user.setUtiPassword(newpwd);
                user.setUtiPremierConnect(false);
                userService.addUtilisateur(user);
                response.setStatut(true);
                response.setDescription("Le mot de passe a été modifié avec succès.");
            }
        } else {
            response.setStatut(false);
            response.setDescription("Aucun utilisateur n'a été trouvé");
        }

        return response;
    }

    //Add salt
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[5];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Génère un mot de passe par aléatoire
     *
     * @param passwordToHash
     * @param salt
     * @return tatic String
     */
    private static String get_SHA_1_SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 5).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * retourne le Nombre d'utilisateur
     *
     * @return ServeurResponse
     */
    @GetMapping("nombreUser")
    private ServeurResponse nombreUser() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre utilisateur");
        response.setData(userService.nombreUtilisateur());
        return response;
    }

    /**
     * Retourne le nombre d'utilisateurs connectés
     *
     * @return ServeurResponse
     */
    @GetMapping("nombreUserConnect")
    private ServeurResponse nombreUserConnectee() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre utilisateur connecte");
        response.setData(sessionService.nombreUserConnecter());
        return response;
    }

    /**
     * Retourne le nombre d'applications créées
     *
     * @return ServeurResponse
     *
     */
    @GetMapping("nombreApplication")
    private ServeurResponse nombreApplication() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre application");
        response.setData(applicationRepository.nombreAplication());
        return response;
    }

    /**
     * Retourne le nombre de modules
     *
     * @return ServeurResponse
     */
    @GetMapping("nombreModule")
    private ServeurResponse nombreModule() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre application");
        response.setData(moduleRepository.nombreModule());
        return response;
    }

    /**
     * Retourne le nombre d'intégrateurs
     *
     * @return ServeurResponse
     */
    @GetMapping("nombreIntegrateur")
    private ServeurResponse nombreIntegrateur() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre integrateur");
        response.setData(userService.nombreUtilisateurByProfil("Integrateur"));
        return response;
    }

    /**
     * Retourne le nombre d'administrateurs
     *
     * @return ServeurResponse
     */
    @GetMapping("nombreAdministrateur")
    private ServeurResponse nombreAdministrateur() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre administrateur");
        response.setData(userService.nombreUtilisateurByProfil("Administrateur"));
        return response;
    }

    /**
     * Retourne le nombre de Commerciaux
     *
     * @return ServeurResponse
     */
    @GetMapping("nombreCommerciaux")
    private ServeurResponse nombreCommerciaux() {
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Nombre Commerciaux");
        response.setData(userService.nombreUtilisateurByProfil("Commercial"));
        return response;
    }

    /**
     * Active un compte désactivé
     *
     * @return ServeurResponse
     */
    @GetMapping("actived/{id}")
    public ServeurResponse activer(@PathVariable Long id) {
        Utilisateur user = new Utilisateur();
        user = userService.listByUtid(id);
        user.setUtiActived(true);
        userService.addUtilisateur(user);
        ServeurResponse response = new ServeurResponse();
        userService.addUtilisateur(user);
        response.setStatut(true);
        response.setDescription("Votre compte a été activé");
        response.setData(user);
        return response;
    }

    /**
     * Désactive un compte utilisateur
     *
     * @param id
     * @return ServeurResponse
     */
    @GetMapping("desactived/{id}")
    public ServeurResponse desactiver(@PathVariable Long id) {
        Utilisateur user = new Utilisateur();
        user = userService.listByUtid(id);
        user.setUtiActived(false);
        userService.addUtilisateur(user);
        ServeurResponse response = new ServeurResponse();
        //userRepository.save(user);
        response.setStatut(true);
        response.setDescription("Votre compte a été desactivé");
        response.setData(user);
        return response;
    }

    @GetMapping("bloquer/{username}")
    public ServeurResponse bloquerUser(@PathVariable String username) {
        Utilisateur user = new Utilisateur();
        user = userService.findByUtiUsername(username);
        user.setUtiActived(false);
        Date dureeBlocage = DateUtils.addMinutes(new Date(), +5);
        user.setUti_temps_session(dureeBlocage);
        userService.addUtilisateur(user);
        //String tempsRestant= userService.verifierTempsSession(username);
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Votre compte a été bloqué");
        response.setData(user);
        return response;
    }

    @GetMapping("debloquer/{username}")
    public ServeurResponse débloquerUser(@PathVariable String username) {
        //Utilisateur user = new Utilisateur();
        Utilisateur user = userService.findByUtiUsername(username);
        //Activer l'utilisateur
        user.setUtiActived(true);
        //Date dureeBlocage = DateUtils.addMinutes(new Date(), +30);
        //Mettre temps session à Null
        user.setUti_temps_session(null);
        userService.addUtilisateur(user);
        //String tempsRestant= userService.verifierTempsSession(username);
        ServeurResponse response = new ServeurResponse();
        response.setStatut(true);
        response.setDescription("Votre compte a été débloqué");
        response.setData(user);
        return response;
    }

    /**
     * Vérifie la session de l'utilisateur
     *
     * @param username
     * @return ServeurResponse
     */
    @GetMapping("verifySession/{username}")
    public ServeurResponse verifySession(@PathVariable String username) {
        ServeurResponse response = new ServeurResponse();
        int n = sessionService.VerifierSession(username);
        if (n >= 1) {
            response.setStatut(true);
            response.setDescription("Déja connecté");
            response.setData(n);
        } else {
            response.setStatut(false);
            response.setDescription("Pas connecté");
            response.setData(n);
        }
        return response;
    }

    /**
     * Créer un lot d'utilisateurs à partir d'un fichier excel
     *
     * @param file
     * @return ServeurResponse
     */
    @PostMapping("/upload")
    public ServeurResponse insertExcel(@RequestParam("excelFile") MultipartFile file) {
        ArrayList<String> values = new ArrayList<>();
        ServeurResponse response = new ServeurResponse();
        String npwd = "";
        try {
            InputStream input = new ByteArrayInputStream(file.getBytes());
            //POIFSFileSystem fs = new POIFSFileSystem(input);
            org.apache.poi.ss.usermodel.Workbook wb = new XSSFWorkbook(input);
            org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();

            //to iterate
            while (rows.hasNext()) {
                values.clear();
                XSSFRow row = (XSSFRow) rows.next();
                if (row.getRowNum() > 0) {
                    Iterator cells = row.cellIterator();
                    //to iterate cell
                    while (cells.hasNext()) {
                        XSSFCell cell = (XSSFCell) cells.next();
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            values.add(Integer.toString((int) cell.getNumericCellValue()));
                        } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            values.add(cell.getStringCellValue());
                        }
                    }
                    npwd = PasswordGenerator.GenerateRandomString();
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setUtiPrenom(values.get(0));
                    utilisateur.setUtiNom(values.get(1));
                    utilisateur.setUtiUsername(values.get(2));
                    utilisateur.setUtiPassword(encoder().encode(npwd));
                    utilisateur.setUtiTelephone(values.get(3));
                    utilisateur.setUtiEmail(values.get(4));
                    utilisateur.setUtiAdresse(values.get(5));
                    if (userService.findUsersByUtiEmail(utilisateur.getUtiEmail()) < 1) {
                        if (userService.findUsersByUtiUsername(utilisateur.getUtiUsername()) < 1) {
                            Profil profil = profilService.findByProLibelle(values.get(6));
                            utilisateur.setUti_pro_id(profil);
                            userService.addUtilisateur(utilisateur);
                            String from = "no-reply@gainde2000.sn";
                            email.sendMail(from, utilisateur.getUtiEmail(), "Bienvenue dans votre plateforme, vos identifiants de connexion : \n Nom utilisateur: " + utilisateur.getUtiUsername() + "\n Mot de passe: " + npwd, "Création de compte utilisateur");
                            response.setStatut(true);
                            response.setDescription("Enregistrement réussi");
                            response.setData(utilisateur);
                        } else {
                            response.setStatut(false);
                            response.setDescription("Le nom d'utilisateur est déjà attribué.");
                            response.setData(null);
                        }
                    } else {
                        response.setStatut(false);
                        response.setDescription("L'email est déjà attribué.");
                        response.setData(null);
                    } 
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException ex) {
            Logger.getLogger(UtilisateurController.class.getName()).log(Level.WARNING, "Erreur envoi de mail!", ex);
        }
        //insert data

        return response;
    }

    /**
     * Met à jour la langue de l'utilisateur
     *
     * @param user
     * @return ServeurResponse
     */
    @PostMapping("updateLangue")
    public ServeurResponse updateLangueUser(@RequestBody Utilisateur user) {
        ServeurResponse response = new ServeurResponse();
        Utilisateur user1 = userService.findByUtiUsername(user.getUtiUsername());
        if ((user1 != null)) {
            user1.setUti_lng_id(user.getUti_lng_id());
            this.userService.addUtilisateur(user1);
            response.setStatut(true);
            response.setDescription("Langue user changé avec succès");
            return response;
        } else {
            response.setStatut(false);
            response.setDescription("Langue user non changés");
            return response;
        }
    }

    /**
     * Met à jour le thème de l'utilisateur
     *
     * @param user
     * @return ServeurResponse
     */
    @PostMapping("updateTheme")
    public ServeurResponse updateThemeUser(@RequestBody Utilisateur user) {
        ServeurResponse response = new ServeurResponse();
        Utilisateur user1 = userService.findByUtiUsername(user.getUtiUsername());
        if ((user1 != null)) {
            user1.setUti_thm_id(user.getUti_thm_id());
            this.userService.addUtilisateur(user1);
            response.setStatut(true);
            response.setDescription("Theme user changé avec succès");
            return response;
        } else {
            response.setStatut(false);
            response.setDescription("Theme user non changés");
            return response;
        }
    }

    @GetMapping("nbrindusutipardate")
    private ServeurResponse nbrIndusUtiParDate() {
        ServeurResponse response = new ServeurResponse();
        List<Object[]> donneecount = userService.nbrUtiIndusByDate();

        response.setStatut(true);
        response.setDescription("Nombre integrateur");
        response.setData(donneecount);
        return response;
    }
    
@GetMapping("utipardateexport")
    private ServeurResponse  UtiParDateExport() {
        ServeurResponse response = new ServeurResponse();
        List<Map<String, Object>> donneecountexport = userService.nbrUtiIndusByDateExport();

        response.setStatut(true);
        response.setDescription("Nombre integrateur");
        response.setData(donneecountexport);
        return response;
    }
    @GetMapping("nbrindusutiparprofil")
    private ServeurResponse nbrIndusUtiParProfil() {
        ServeurResponse response = new ServeurResponse();
        List<Map<String, Object>> donneecountprofil = userService.nbrUtiIndusByProfil();

        response.setStatut(true);
        response.setDescription("Nombre integrateur");
        response.setData(donneecountprofil);
        return response;
    }

    @GetMapping("nbrcourbeindusutiparprofil")
    private ServeurResponse nbrCourbeIndusUtiParProfil() {
        ServeurResponse response = new ServeurResponse();
        List<Map<String, Object>> donneecourbecountprofil = userService.nbrCourbeUtiIndusByProfil();

        response.setStatut(true);
        response.setDescription("Nombre integrateur");
        response.setData(donneecourbecountprofil);
        return response;
    }

    @GetMapping("nbrindusutiparannee")
    private ServeurResponse nbrIndusUtiParAnnee() {
        ServeurResponse response = new ServeurResponse();
        List<Map<String, Object>> donneecount = userService.nbrUtiIndusByAnnee();

        response.setStatut(true);
        response.setDescription("Nombre integrateur");
        response.setData(donneecount);
        return response;
    }

    @GetMapping("nbrindusutiparprofilparannee")
    private ServeurResponse nbrIndusUtiParProfilParAnnee() {
        ServeurResponse response = new ServeurResponse();
        List<Map<String, Object>> donneecountprofil = userService.nbrUtiIndusByProfilParAnnee();

        response.setStatut(true);
        response.setDescription("Nombre integrateur");
        response.setData(donneecountprofil);
        return response;
    }
    
    @GetMapping(value="email/{email}")
    public ServeurResponse getUserByEmail(@PathVariable String email) {
        ServeurResponse response = new ServeurResponse();
        Utilisateur user = userService.findByUtiEmail(email);
        if (user != null) {
            response.setStatut(true);            
            response.setData(user);
            response.setDescription("Succés de la recupération");
        } else {
            response.setStatut(true);            
            response.setData(null);
            response.setDescription("Aucune données recupérées");
        }
        return response;
    }
}

