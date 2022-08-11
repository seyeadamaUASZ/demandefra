/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.security;

import sn.gainde2000.pi.core.service.UserService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import sn.gainde2000.pi.core.repository.HistoriqueSessionRepository;
import sn.gainde2000.pi.core.repository.SessionRepository;
import sn.gainde2000.pi.core.repository.UtilisateurRepository;
import sn.gainde2000.pi.core.repository.ParametreRepository;
import sn.gainde2000.pi.core.service.IMenuService;
import sn.gainde2000.pi.core.service.IPwdCriteriaService;
import sn.gainde2000.pi.core.service.impl.UtilisateurImpl;

/**
 *
 * @author leyu
 */
@Configuration
@EnableWebSecurity
@CrossOrigin
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;
    private String[] USERS_ACCESS = {"ADMIN", "OPERATEUR"};

    @Autowired
    UserService userService;
    @Autowired
    UtilisateurRepository repository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    HistoriqueSessionRepository historiqueSessionRepository;
    @Autowired
    ParametreRepository parametreRepository;
    @Autowired
    IMenuService menuService;
    @Autowired
    UtilisateurImpl userService1;
    @Autowired
    IPwdCriteriaService pwdCriteriaService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, repository, sessionRepository, historiqueSessionRepository, parametreRepository, menuService, userService1, pwdCriteriaService))
                .addFilter(new JwtTokenAuthenticationFilter(jwtConfig, authenticationManager()))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
                .antMatchers("/utilisateur/recover").permitAll()
                .antMatchers("/utilisateur/email/{email}").permitAll()
                .antMatchers("/utilisateur/get/{username}").permitAll()
                .antMatchers("/utilisateur/updateLangue/**").permitAll()
                .antMatchers("/demandeautfra/**").permitAll()
                .antMatchers("/demandeautfra/contact").permitAll()
                .antMatchers("/antenneRegionaleDepartementale/list").permitAll()
                .antMatchers("/antenneRegionaleDepartementale/listeAntenneParRegion/{id}").permitAll()                
                .antMatchers("/region/list").permitAll()
                .antMatchers("/transaction/list").permitAll()
                .antMatchers("/produits/**").permitAll()
                .antMatchers("/produits/validationfra").permitAll()   
                .antMatchers("/motifrejetourenvoi/motifrejet/{idLink}").permitAll() 
                .antMatchers("/motifrejetourenvoi/historique/{idLink}/{status}").permitAll()
                .antMatchers("/motifrejetourenvoi/motifrejet/{idLink}/{status}").permitAll()
                .antMatchers("/categorie/list/**").permitAll()                
                .antMatchers("/utilisateur/veriftoken/**").permitAll()
                .antMatchers("/moyenPaiement/crypter").permitAll()                
                .antMatchers("/traduction/i18n/**").permitAll()
                .antMatchers("/parametre").permitAll()
                .antMatchers("/menu").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/utilisateur/bloquer/**").permitAll()
                .antMatchers("/inscription/create/**").permitAll()
                .antMatchers("/zk/identificationuser/**").permitAll()
                .antMatchers("/zk/actionidentificationFinger/**").permitAll()
                .antMatchers("/zk/findbyempriente/{id}").permitAll()
                .antMatchers("/paiement/notification/**").permitAll()
                .antMatchers("/rapportanalyse/produits/{idProduit}").permitAll()
                .antMatchers("/landing/get/**").permitAll()
                .antMatchers("/langue/**").permitAll()
                .antMatchers("/profil/listInscri/**").permitAll()
                .antMatchers("/paiement/redirect/**").permitAll()
                .antMatchers("/application/publicationliste/**").permitAll()
                .antMatchers("/application/publicationliste2/**").permitAll()
                .antMatchers("/produits/validationfra/**").permitAll()
                .antMatchers("/get/**").permitAll()
                //.antMatchers("/profil/listInscri/**").permitAll()

                .antMatchers("/genotpconf").permitAll()
                .antMatchers("/genotp/**").permitAll()
                .antMatchers("/genotpEvenement").permitAll()
                .antMatchers("/genotpBydureee").permitAll()
                .antMatchers("/verificationCode/**").permitAll()
                .antMatchers("/genotpEvenement/**").permitAll()
                .antMatchers("/genotpconf/**").permitAll()
                .antMatchers("/envoyercodeByLibelle/**").permitAll()
                .antMatchers("typeDeNotification/create").permitAll()
                 .antMatchers("/genotpconf").permitAll()
                 .antMatchers("/genotp/**").permitAll()
                 .antMatchers("/genotpEvenement").permitAll()
                .antMatchers("/genotpEvenement").permitAll()
                 .antMatchers("/genotpBydureee").permitAll()
                 .antMatchers("/verificationCode/**").permitAll()
                 .antMatchers("/genotpEvenement/**").permitAll()
                 .antMatchers("/genotpconf/**").permitAll()
                 .antMatchers("/envoyercodeByLibelle/**").permitAll() 
                 .antMatchers("/otp/deleteparametre/{id}").permitAll()
                 
                 .antMatchers("typeDeNotification/create").permitAll()
                .antMatchers("typeDeNotification/list").permitAll()
                .antMatchers("typeDeNotification/{search}").permitAll()
                .antMatchers("/application/publicationliste/**").permitAll()
                .antMatchers("/get/**").permitAll()
                .antMatchers("/otp/addParametre/**").permitAll()
                .antMatchers("/otp/list/**").permitAll()
                .antMatchers("/capteur/list").permitAll()
                .antMatchers("/otp/generateotp/**").permitAll()
                .antMatchers("/otp/update/**").permitAll()               
                .antMatchers("/typeConnexion/list/**").permitAll()
                .antMatchers("/partenaire/get").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/configuration/ui").permitAll()

                .antMatchers("/configuration/security").permitAll()

                .antMatchers( "/configuration/security").permitAll()

                // .antMatchers("/socket").permitAll()
                .anyRequest().authenticated();
        http.cors();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
//        return new CustomBasicAuthenticationEntryPoint();
//    }
    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
