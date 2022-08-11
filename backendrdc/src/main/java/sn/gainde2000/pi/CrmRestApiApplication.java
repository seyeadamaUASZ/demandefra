
package sn.gainde2000.pi;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collections;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import sn.gainde2000.pi.config.AppProperties;
import sn.gainde2000.pi.integration.signature.cf.client.test.SOAPClient_3;
import sn.gainde2000.pi.integration.signature.confiancefactory.wsdl_rasign.SignatureResult;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@PropertySources({ @PropertySource("classpath:application.properties") })
@ComponentScan("sn.gainde2000.pi")
@EnableAsync
public class CrmRestApiApplication  extends SpringBootServletInitializer{
    public static final String APP_NAME = "Industrialisation";
    @Autowired
    private Environment env;
    
        
    public static void main(String[] args) throws RemoteException{
   /* SOAPClient_3 soapc = new SOAPClient_3();
    	byte[] byteArrayScan = null;
		try {
			byteArrayScan = Files.readAllBytes(Paths.get("C:\\Users\\vwade\\Documents\\Ecarts FRA.pdf"));
			System.out.println("byteArrayDocument.length: " + byteArrayScan.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 SignatureResult sr =soapc.callMethd_Signer_fra(14874178, "CLE_FRA_USER", "394371", byteArrayScan, 5412794);
    	  byte[] doc_signe = sr.getContent(); 
    	 try {
			soapc.writeBytesToFile("C:\\Users\\vwade\\Documents\\Ecarts FRA2.pdf", doc_signe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        SpringApplication app= new SpringApplication(CrmRestApiApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "9090"));
        app.run(args);
        

    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
      return builder.sources(CrmRestApiApplication.class);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
    
    @Bean(name = "dataSourceJasper")
    public DataSource dataSourceJasper() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }
    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
        
}
