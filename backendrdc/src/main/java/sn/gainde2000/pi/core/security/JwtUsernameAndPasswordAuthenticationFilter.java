
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.security;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.net.InetAddress;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sn.gainde2000.pi.core.repository.HistoriqueSessionRepository;
import sn.gainde2000.pi.core.repository.SessionRepository;
import sn.gainde2000.pi.core.repository.UtilisateurRepository;
import sn.gainde2000.pi.core.entity.HistoriqueSession;
import sn.gainde2000.pi.core.entity.Menu;
import sn.gainde2000.pi.core.entity.PwdCriteria;
import sn.gainde2000.pi.core.entity.Session;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.repository.ParametreRepository;
import sn.gainde2000.pi.core.service.IMenuService;
import sn.gainde2000.pi.core.service.IPwdCriteriaService;
import sn.gainde2000.pi.core.service.impl.UtilisateurImpl;

/**
 *
 * @author yougadieng
 */
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final SessionRepository sessionRepository;
    private final HistoriqueSessionRepository historiqueSessionRepository;
    private final ParametreRepository parametreRepository;
    private final AuthenticationManager authManager;
    private final IMenuService menuService;
    private final UtilisateurImpl userService;

    private final JwtConfig jwtConfig;

    private final UtilisateurRepository repository;
    
    private final IPwdCriteriaService pwdCriteriaService;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig, UtilisateurRepository repository, SessionRepository session, HistoriqueSessionRepository historiqueSessionRepository, ParametreRepository paramRepo, IMenuService menuService, UtilisateurImpl userService, IPwdCriteriaService pwdcrit) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;
//      this.service = userService;
        this.repository = repository;
        this.sessionRepository = session;
        this.historiqueSessionRepository = historiqueSessionRepository;
        this.parametreRepository = paramRepo;
        this.menuService = menuService;
        this.userService = userService;
        this.pwdCriteriaService = pwdcrit;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {

            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getUsername(), creds.getPassword(), Collections.emptyList());
            //System.out.println( creds.getUsername()+"++++"+creds.getPassword());
            return authManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        String username = ((User) auth.getPrincipal()).getUsername();
        //System.out.println(username);
        Long now = System.currentTimeMillis();

        String token = Jwts.builder()
                .setSubject(username)
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();

        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
        sn.gainde2000.pi.core.entity.Utilisateur usr = repository.findByUtiUsername(username);
        // sn.gainde2000.pi.core.entity.Parametre param = parametreRepository.findByUsername(username);

        if (!usr.isUtiActived()) {
            String json = "";
//>>>>>>> 6f3bbe4cbef1fdb6c9cc9a4cd974239d65c861d2
            ServeurResponse serveurResponse = new ServeurResponse();
            serveurResponse.setStatut(false);
            int tempsSession=userService.verifierTempsSession(username);
            if (tempsSession > 0) {
                int tempsRestant = userService.verifierTempsSession(username);
                serveurResponse.setDescription("Votre session sera active dans " + tempsRestant + " mn");
                json = "{"
                        + "\"statut\":"
                        + serveurResponse.isStatut()
                        + ","
                        + "\"description\":\"" + serveurResponse.getDescription() + "\""
                        + ","
                        + "\"Tempssession\":" + "\"" + tempsRestant + "\""
                        + "}";

            } else if (tempsSession < 0) {
                //déloquer le user
                //update la colonne à Null
                //int tempsRestant = userService.verifierTempsSession(username);
                
                Utilisateur user = userService.findByUtiUsername(username);
                //Activer l'utilisateur
                user.setUtiActived(true);
                //mettre le temps de session à Null
                user.setUti_temps_session(null);
                userService.addUtilisateur(user);

                HashMap<String, String> hash = new HashMap<>();
                hash.put("username", username);
                hash.put("token", token);
                hash.put("profile", usr.getUti_pro_id().getProLibelle());
                hash.put("prenom", usr.getUtiPrenom());
                hash.put("nom", usr.getUtiNom());

                Session session = new Session();
                session.setSesDateConnexion(new Date());
                session.setSesIp(InetAddress.getLocalHost().toString());
                session.setSesLogin(username);
                session.setSesUtiId(usr.getUtiId());
                Session s = this.sessionRepository.save(session);
                HistoriqueSession historiqueSession = new HistoriqueSession();
                historiqueSession.setHseDateConnexion(new Date());
                historiqueSession.setHseDateDecConnexion(null);
                historiqueSession.setHseIp(InetAddress.getLocalHost().toString());
                historiqueSession.setHseLogin(username);
                historiqueSession.setHseUtiId(user.getUtiId());
                historiqueSession.setHseSesId(s.getSesId());
                this.historiqueSessionRepository.save(historiqueSession);
                Iterable<Menu> listMenu = menuService.getListMenu();
                serveurResponse.setStatut(true);
                serveurResponse.setDescription("authentification.succes-login");
                serveurResponse.setData(hash);
                json = "{"
                        + "\"statut\":"
                        + serveurResponse.isStatut()
                        + ","
                        + "\"premiereConnec\":"
                        + usr.isUtiPremierConnect() + ","
                        + "\"description\":\"" + serveurResponse.getDescription() + "\""
                        + ",\"data\":{" + "\"username\":" + "\"" + username + "\","
                        + "\"token\":\"" + token + "\","
                        + "\"profile\":" + "\"" + usr.getUti_pro_id().getProId() + "\","
                        + "\"profileLib\":" + "\"" + usr.getUti_pro_id().getProLibelle() + "\","
                        + "\"prenom\":" + "\"" + usr.getUtiPrenom() + "\","
                        + "\"nom\":" + "\"" + usr.getUtiNom() + "\","
                        + "\"id\":" + "\"" + usr.getUtiId() + "\","
                        + "\"session\":" + "\"" + s.getSesId() + "\","
                        + "\"menu\":" + "\"" + listMenu + "\""
                        + "}}";

            } else {
                serveurResponse.setDescription("authentification.disable-account-message");
                json = "{"
                        + "\"statut\":"
                        + serveurResponse.isStatut()
                        + ","
                        + "\"description\":\"" + serveurResponse.getDescription() + "\""
                        + "}";
            }
            //serveurResponse.setStatut(false);

            response.setContentType("application/json");
            response.getWriter().write(json);
        } else if (!usr.isUtiPremierConnect()) {
            //if (usr.isUtiActived()) {
            HashMap<String, String> hash = new HashMap<>();
            hash.put("username", username);
            hash.put("token", token);
            hash.put("profile", usr.getUti_pro_id().getProLibelle());
            hash.put("prenom", usr.getUtiPrenom());
            hash.put("nom", usr.getUtiNom());
            ServeurResponse serveurResponse = new ServeurResponse();            
            serveurResponse.setStatut(true);
            serveurResponse.setDescription("authentification.succes-login");
            serveurResponse.setData(hash);
            //recup parametre critere mot de passe expiré            
            boolean isPwdExpired = false;
            PwdCriteria pwd = pwdCriteriaService.getListPwd().get(0);
            Date dateModif = (usr.getUtiPwdDateModif()!=null)?usr.getUtiPwdDateModif():new Date();            
            int diffInDays = (int) ChronoUnit.DAYS.between(dateModif.toInstant(), new Date().toInstant());
            int dureePwd = (pwd.getPwdDure()!=null)?Integer.parseInt(pwd.getPwdDure()):0;            
            if((dureePwd!=0)&&(diffInDays > dureePwd)){
                isPwdExpired = true;
                serveurResponse.setDescription("authentification.login-pwdexpired");               
            }
            Session session = new Session();
            session.setSesDateConnexion(new Date());
            session.setSesIp(InetAddress.getLocalHost().toString());
            session.setSesLogin(username);
            session.setSesUtiId(usr.getUtiId());
            //si l'utilisateur a deja une session en cours
            if (this.sessionRepository.VerifierSession(usr.getUtiUsername()) >= 1) {
                sessionRepository.deleteUtiSession(usr.getUtiId());
                // System.out.println("after le delete du id : " + usr.getUtiId());
                historiqueSessionRepository.updateDate(usr.getUtiId());
                //  System.out.println("after le update du id : " + usr.getUtiId());
            }
            Session s = this.sessionRepository.save(session);
            HistoriqueSession historiqueSession = new HistoriqueSession();
            historiqueSession.setHseDateConnexion(new Date());
            historiqueSession.setHseDateDecConnexion(null);
            historiqueSession.setHseIp(InetAddress.getLocalHost().toString());
            historiqueSession.setHseLogin(username);
            historiqueSession.setHseUtiId(usr.getUtiId());
            historiqueSession.setHseSesId(s.getSesId());
            this.historiqueSessionRepository.save(historiqueSession);
            Iterable<Menu> listMenu = menuService.getListMenu();            
            String json = "{"
                    + "\"statut\":"
                    + serveurResponse.isStatut()
                    + ","
                    + "\"expiredpwd\":"
                    + isPwdExpired + ","                  
                    + "\"premiereConnec\":"
                    + usr.isUtiPremierConnect() + ","
                    + "\"description\":\"" + serveurResponse.getDescription() + "\""
                    + ",\"data\":{" + "\"username\":" + "\"" + username + "\","
                    + "\"token\":\"" + token + "\","
                    + "\"profile\":" + "\"" + usr.getUti_pro_id().getProId() + "\","
                    + "\"profileLib\":" + "\"" + usr.getUti_pro_id().getProLibelle() + "\","
                    + "\"prenom\":" + "\"" + usr.getUtiPrenom() + "\","
                    + "\"nom\":" + "\"" + usr.getUtiNom() + "\","
                    + "\"id\":" + "\"" + usr.getUtiId() + "\","
                    + "\"session\":" + "\"" + s.getSesId() + "\","
                    + "\"menu\":" + "\"" + listMenu + "\""
                    + "}}";
            response.setContentType("application/json");
            response.getWriter().write(json);
            /*} else {
                ServeurResponse serveurResponse = new ServeurResponse();
                serveurResponse.setStatut(false);
                serveurResponse.setDescription("authentification.disable-account-message");
                String json = "{"
                        + "\"statut\":"
                        + serveurResponse.isStatut()
                        + ","
                        + "\"description\":\"" + serveurResponse.getDescription() + "\""
                        + "}";
                response.setContentType("application/json");
                response.getWriter().write(json);
            }*/
        } else {
            ServeurResponse serveurResponse = new ServeurResponse();
            serveurResponse.setStatut(true);
            serveurResponse.setData(usr);
            serveurResponse.setDescription("authentification.first-connect-message");
            String json = "{"
                    + "\"statut\":"
                    + serveurResponse.isStatut()
                    + ","
                    + "\"premiereConnec\":"
                    + usr.isUtiPremierConnect() + ","
                    + "\"description\":\"" + serveurResponse.getDescription() + "\""
                    + ",\"data\":{" + "\"username\":" + "\"" + username + "\","
                    + "\"token\":\"" + token + "\","
                    + "\"profile\":" + "\"" + usr.getUti_pro_id().getProId() + "\","
                    + "\"profileLib\":" + "\"" + usr.getUti_pro_id().getProLibelle() + "\","
                    + "\"prenom\":" + "\"" + usr.getUtiPrenom() + "\","
                    + "\"nom\":" + "\"" + usr.getUtiNom() + "\""
                    + "}}";
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed); //To change body of generated methods, choose Tools | Templates.

        response.setContentType("application/json");
        response.getWriter().write(""
                + "{"
                + "\"statut\": false,"
                + "\"description\": \"authentification.nomatch-login\""
                + "}"
                + "");
    }

    private static class UserCredentials {

        private String username, password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
