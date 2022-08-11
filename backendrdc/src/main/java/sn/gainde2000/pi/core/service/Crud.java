package sn.gainde2000.pi.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;
import sn.gainde2000.pi.core.entity.Application;
import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.ChampsRapport;
import sn.gainde2000.pi.core.entity.Fichier;
import sn.gainde2000.pi.core.entity.Formulaire;
import sn.gainde2000.pi.core.entity.Langue;
import sn.gainde2000.pi.core.entity.Module;
import sn.gainde2000.pi.core.entity.Parametre;
import sn.gainde2000.pi.core.entity.PwdCriteria;
import sn.gainde2000.pi.core.entity.Rapport;
import sn.gainde2000.pi.core.entity.RegleGestion;
import sn.gainde2000.pi.core.entity.Task;
import sn.gainde2000.pi.core.entity.TaskStatus;
import sn.gainde2000.pi.core.entity.Theme;
import sn.gainde2000.pi.core.entity.TimerTask;
import sn.gainde2000.pi.core.entity.Transition;
import sn.gainde2000.pi.core.entity.ValueChamps;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.Workflow;
import sn.gainde2000.pi.core.entity.WorkflowItem;
import sn.gainde2000.pi.core.entity.WorkflowSecteur;
import sn.gainde2000.pi.core.tools.StringProcess;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;
import sn.gainde2000.pi.ged.entity.Document;

public class Crud {

    public String repo = "/opt/industrialisation/codesource";
    public String cheminZip = "/opt/industrialisation/depotzip";
    public String packagename = "sn.gainde2000.pi.core";
    public String importKey = "import";
    public String packageKey = "package";
    public String dossierPackage = "backendrdc/src/main/java/sn/gainde2000/pi/core/";
    public String fichierConfig = "/opt/industrialisation/fileconfig";
    @Autowired
    IThemeService iThemeService;

    public Crud() {
        String SE = System.getProperty("os.name").toLowerCase();
        if (SE.indexOf("nux") >= 0) {
            this.repo = "/opt/industrialisation/codesource";
            this.cheminZip = "/opt/industrialisation/depotzip";
            this.fichierConfig = "/opt/industrialisation/fileconfig";
        } else {
            this.repo = System.getProperty("user.dir") + "/opt/industrialisation/codesource";
            this.cheminZip = System.getProperty("user.dir") + "/opt/industrialisation/depotzip";
            this.fichierConfig = System.getProperty("user.dir") + "/opt/industrialisation/fileconfig";
        }
    }

    public String getMappingObjet(String nomObjet) {
        // String mots = nomObjet.substring(3);
        String mots = "";
        String[] tab = nomObjet.split("_");
        for (int i = 1; i < tab.length; i++) {
            mots = mots + tab[i];
        }
        return mots;
    }

    public String getMappingChamps(String champs) {
        StringProcess uppercasefunct = new StringProcess();
        String[] tab = champs.split("_");
        String mots = tab[0] + uppercasefunct.capitalize(tab[1]);
        return mots;
    }
    // debut back

    /*
	 * +++++++++++++++++++ Generation des fichiers backend
	 * model+++++++++++++++++++++++++
     */
    public void generationFileModelBack(String composant, List<Champs> listChamps) throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        File packentity = new File(this.repo + "/" + this.dossierPackage + "entity");
        packentity.mkdirs();
        File controllerfiles = new File(packentity, "" + uppercasefunct.capitalize(composant) + ".java");
        controllerfiles.createNewFile();
        String filejava = this.packageKey + " " + this.packagename + ".entity;\n" + "\n"
                + "import java.io.Serializable;\n" + "import java.math.BigInteger;\n"
                + "import javax.persistence.Basic;\n" + "import javax.persistence.Column;\n"
                + "import javax.persistence.Entity;\n" + "import javax.persistence.GeneratedValue;\n"
                + "import javax.persistence.GenerationType;\n" + "import javax.persistence.Id;\n"
                + "import javax.persistence.Table;\n" + "import javax.validation.constraints.NotNull;\n"
                + "import javax.persistence.JoinColumn;\r\n" + "import javax.persistence.ManyToOne;\n"
                + "import javax.persistence.Temporal;\r\n" + "import javax.persistence.TemporalType;\n"
                + "import sn.gainde2000.pi.core.*;\n" + "import java.util.Date;\n" + "@Entity\n" + "@Table(name = \""
                + uppercasefunct.capitalize(composant) + "\")\n" + "public class "
                + uppercasefunct.capitalize(composant) + " implements Serializable {\n"
                + "    private static final long serialVersionUID = 1L;\n";

        filejava = filejava + "@Id\n" + "@GeneratedValue(strategy = GenerationType.AUTO)\n"
                + "@Basic(optional = false)\n" + "@NotNull\n" + "@Column(name = \"id\")\n" + "private Long id;\n";
        /* generation declaration des attributs */
        int i = 0;
        for (i = 0; i < listChamps.size(); i++) {

            if (listChamps.get(i).getChpType().equals("text") || listChamps.get(i).getChpType().equals("textarea")
                    || listChamps.get(i).getChpType().equals("phone") || listChamps.get(i).getChpType().equals("email")
                    || listChamps.get(i).getChpType().equals("radio")
                    || listChamps.get(i).getChpType().equals("checkbox")
                    || listChamps.get(i).getChpType().equals("autocomplete")
                    || listChamps.get(i).getChpType().equals("textarea")) {
                filejava = filejava + "@Column(name = \"" + listChamps.get(i).getChpNom() + "\")\n";
                filejava = filejava + "private String " + listChamps.get(i).getChpNom() + ";\n" + "";
            } else if (listChamps.get(i).getChpType().equals("number")) {
                filejava = filejava + "@Column(name = \"" + listChamps.get(i).getChpNom() + "\")\n";
                filejava = filejava + "private Long " + listChamps.get(i).getChpNom() + ";\n" + "";
            } else if (listChamps.get(i).getChpType().equals("file")) {
                filejava = filejava + "@Column(name = \"" + listChamps.get(i).getChpNom()
                        + "\", columnDefinition = \"MEDIUMBLOB\")\n";
                filejava = filejava + "private byte[] " + listChamps.get(i).getChpNom() + ";\n" + "";
                filejava = filejava + "@Column(name = \"extention\")\n";
                filejava = filejava + "private String extention ;\n" + "";
            } else if (listChamps.get(i).getChpType().equals("relation")) {
                filejava = filejava + "@ManyToOne\r\n" + "@JoinColumn(name=\""
                        + getMappingObjet(listChamps.get(i).getChpNom()).toString() + "\", nullable=false)\n"
                        + "private "
                        + uppercasefunct.capitalize(getMappingObjet(listChamps.get(i).getChpNom()).toString()) + " "
                        + getMappingObjet(listChamps.get(i).getChpNom()).toString() + ";\n" + "";
            } else if (listChamps.get(i).getChpType().equals("date")) {
                filejava = filejava + "@Column(name = \"" + listChamps.get(i).getChpNom() + "\")\n";
                filejava = filejava + "@Temporal(TemporalType.TIMESTAMP)\n" + "private Date "
                        + listChamps.get(i).getChpNom() + ";";
            } else {
                filejava = filejava + " ";
            }

            filejava = filejava + "\n" + "";

        }

        /* generation declaration des getters setters */
        String filejavagettersetter = "";
        int j = 0;
        for (j = 0; j < listChamps.size(); j++) {

            if (listChamps.get(j).getChpType().equals("text") || listChamps.get(j).getChpType().equals("textarea")
                    || listChamps.get(j).getChpType().equals("email") || listChamps.get(j).getChpType().equals("phone")
                    || listChamps.get(j).getChpType().equals("autocomplete")
                    || listChamps.get(j).getChpType().equals("radio")
                    || listChamps.get(j).getChpType().equals("checkbox")) {
                filejavagettersetter = filejavagettersetter + "public String get"
                        + uppercasefunct.capitalize(listChamps.get(j).getChpNom()) + "() {\n" + "        return "
                        + listChamps.get(j).getChpNom() + ";\n" + "    }\n" + "    public void set"
                        + uppercasefunct.capitalize(listChamps.get(j).getChpNom()) + "(String "
                        + listChamps.get(j).getChpNom() + ") {\n" + "        this." + listChamps.get(j).getChpNom()
                        + " = " + listChamps.get(j).getChpNom() + ";\n" + "    }   \n" + "";
            } else if (listChamps.get(j).getChpType().equals("number")) {
                filejavagettersetter = filejavagettersetter + "public Long get"
                        + uppercasefunct.capitalize(listChamps.get(j).getChpNom()) + "() {\n" + "        return "
                        + listChamps.get(j).getChpNom() + ";\n" + "    }\n" + "    public void set"
                        + uppercasefunct.capitalize(listChamps.get(j).getChpNom()) + "(Long "
                        + listChamps.get(j).getChpNom() + ") {\n" + "        this." + listChamps.get(j).getChpNom()
                        + " = " + listChamps.get(j).getChpNom() + ";\n" + "    }   \n" + "";
            } else if (listChamps.get(j).getChpType().equals("relation")) {
                filejavagettersetter = filejavagettersetter + "public "
                        + uppercasefunct.capitalize(getMappingObjet(listChamps.get(j).getChpNom()).toString()) + " get"
                        + uppercasefunct.capitalize(getMappingObjet(listChamps.get(j).getChpNom()).toString())
                        + "() {\n" + "        return " + getMappingObjet(listChamps.get(j).getChpNom()).toString()
                        + ";\n" + "    }\n" + "    public void set"
                        + uppercasefunct.capitalize(getMappingObjet(listChamps.get(j).getChpNom()).toString()) + "("
                        + uppercasefunct.capitalize(getMappingObjet(listChamps.get(j).getChpNom()).toString()) + " "
                        + getMappingObjet(listChamps.get(j).getChpNom()).toString() + ") {\n" + "        this."
                        + getMappingObjet(listChamps.get(j).getChpNom()).toString() + " = "
                        + getMappingObjet(listChamps.get(j).getChpNom()).toString() + ";\n" + "    }   \n" + "";
            } else if (listChamps.get(j).getChpType().equals("date")) {
                filejavagettersetter = filejavagettersetter + "public Date get"
                        + uppercasefunct.capitalize(listChamps.get(j).getChpNom()) + "() {\n" + "        return "
                        + listChamps.get(j).getChpNom() + ";\n" + "    }\n" + "    public void set"
                        + uppercasefunct.capitalize(listChamps.get(j).getChpNom()) + "(Date "
                        + listChamps.get(j).getChpNom() + ") {\n" + "        this." + listChamps.get(j).getChpNom()
                        + " = " + listChamps.get(j).getChpNom() + ";\n" + "    }   \n" + "";
            } else if (listChamps.get(j).getChpType().equals("file")) {
                filejavagettersetter = filejavagettersetter + "public byte[] get"
                        + uppercasefunct.capitalize(listChamps.get(j).getChpNom()) + "() {\n" + "        return "
                        + listChamps.get(j).getChpNom() + ";\n" + "    }\n" + "    public void set"
                        + uppercasefunct.capitalize(listChamps.get(j).getChpNom()) + "(byte[] "
                        + listChamps.get(j).getChpNom() + ") {\n" + "        this." + listChamps.get(j).getChpNom()
                        + " = " + listChamps.get(j).getChpNom() + ";\n" + "    }   \n" + "";
                filejavagettersetter = filejavagettersetter + "public Long getId() {\r\n" + "		return id;\r\n"
                        + "	}\r\n" + "	public void setId(Long id) {\r\n" + "		this.id = id;\r\n" + "	}   \r\n"
                        + "\n";

                filejavagettersetter = filejavagettersetter + "public String getExtention() {\r\n"
                        + "		return extention;\r\n" + "	}\r\n"
                        + "	public void setExtention(String extention) {\r\n" + "		this.extention = extention;\r\n"
                        + "	}  \n";
            } else {
                filejavagettersetter = filejavagettersetter + " ";
            }
            filejavagettersetter = filejavagettersetter + "\n" + "";

        }

        filejava = filejava + filejavagettersetter + "\n" + "}";
        FileWriter myWriterentity = new FileWriter(packentity + "\\" + composant + ".java");
        myWriterentity.write("\n" + filejava);
        myWriterentity.close();

    }

    /*
	 * +++++++++++++++++++ Fin generation des fichiers
	 * model+++++++++++++++++++++++++
     */
 /*
	 * +++++++++++++++++++ Generation des fichiers
	 * repository+++++++++++++++++++++++++
     */
    public void generationFileRepositoryBack(String nomrepo) throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        File packrepo = new File(this.repo + "/" + this.dossierPackage + "repository");
        packrepo.mkdirs();
        File repofiles = new File(packrepo, "" + uppercasefunct.capitalize(nomrepo) + "Repository.java");
        repofiles.createNewFile();
        String repositoryfile = this.packageKey + " " + this.packagename + ".repository;\n" + "\n" + this.importKey
                + " " + this.packagename + ".entity." + uppercasefunct.capitalize(nomrepo) + ";\n"
                + "import org.springframework.data.jpa.repository.JpaRepository;\n"
                + "import org.springframework.stereotype.Repository;\n" + "\n" + "@Repository\n" + "public interface "
                + uppercasefunct.capitalize(nomrepo) + "Repository extends JpaRepository<"
                + uppercasefunct.capitalize(nomrepo) + ", Long> {\n" + "\n" + "}";

        FileWriter myWriterrepo = new FileWriter(packrepo + "\\" + nomrepo + "Repository.java");
        myWriterrepo.write("\n" + repositoryfile);
        myWriterrepo.close();
    }

    /*
	 * +++++++++++++++++++ Fin generation des fichiers
	 * repository+++++++++++++++++++++++++
     */

 /*
	 * +++++++++++++++++++ Generation des fichiers service+++++++++++++++++++++++++
     */
    public void generationFileServiceBack(String nomserviceimpl) throws IOException {
        // String nomserviceimpl = form.getFrmNom();
        StringProcess uppercasefunct = new StringProcess();
        File packservice = new File(this.repo + "/" + this.dossierPackage + "service");
        packservice.mkdirs();

        File serviceimplfiles = new File(packservice, "I" + uppercasefunct.capitalize(nomserviceimpl) + "Service.java");
        serviceimplfiles.createNewFile();
        String servicefileimpl = this.packageKey + " " + this.packagename + ".service;\n" + this.importKey + " "
                + this.packagename + ".entity." + uppercasefunct.capitalize(nomserviceimpl) + ";\n"
                + "import java.util.List;\n" + "import java.util.Optional;\n" + "\n" + "public interface I"
                + uppercasefunct.capitalize(nomserviceimpl) + "Service {\n" + "\n" + "Optional<"
                + uppercasefunct.capitalize(nomserviceimpl) + "> findById(Long id);\n" + "List<"
                + uppercasefunct.capitalize(nomserviceimpl) + "> findAll();\n"
                + uppercasefunct.capitalize(nomserviceimpl) + " save(" + uppercasefunct.capitalize(nomserviceimpl) + " "
                + nomserviceimpl.toLowerCase() + ");\n" + "void delete(" + uppercasefunct.capitalize(nomserviceimpl)
                + " " + nomserviceimpl.toLowerCase() + ");\n" + "}";

        FileWriter myWriteservicesimpl = new FileWriter(
                packservice + "/I" + uppercasefunct.capitalize(nomserviceimpl) + "Service.java");
        myWriteservicesimpl.write("\n" + servicefileimpl);
        myWriteservicesimpl.close();

        File packservi = new File(this.repo + "/" + this.dossierPackage + "service/impl");
        packservi.mkdirs();
        File servicefiles = new File(packservi, uppercasefunct.capitalize(nomserviceimpl) + "ServiceImpl.java");
        servicefiles.createNewFile();
        String servicefile = this.packageKey + " " + this.packagename + ".service.impl;\n" + "\n" + this.importKey + " "
                + this.packagename + ".entity." + uppercasefunct.capitalize(nomserviceimpl) + ";\n" + this.importKey
                + " " + this.packagename + ".service.I" + uppercasefunct.capitalize(nomserviceimpl) + "Service;\n"
                + "import org.springframework.beans.factory.annotation.Autowired;\n"
                + "import org.springframework.stereotype.Service;\n" + this.importKey + " " + this.packagename
                + ".repository." + uppercasefunct.capitalize(nomserviceimpl) + "Repository;" + "\n"
                + "import java.util.List;\n" + "import java.util.Optional;\n" + "\n" + "@Service\n" + "public class "
                + uppercasefunct.capitalize(nomserviceimpl) + "ServiceImpl implements I"
                + uppercasefunct.capitalize(nomserviceimpl) + "Service {\n" + "\n" + "    @Autowired\n" + "    private "
                + uppercasefunct.capitalize(nomserviceimpl) + "Repository " + nomserviceimpl.toLowerCase()
                + "Repository;\n" + "\n" + "    @Override\n" + "    public List<"
                + uppercasefunct.capitalize(nomserviceimpl) + "> findAll() {\n" + "\n" + "        return "
                + nomserviceimpl.toLowerCase() + "Repository.findAll();\n" + "    }\n" + "    @Override\n"
                + "    public " + uppercasefunct.capitalize(nomserviceimpl) + " save("
                + uppercasefunct.capitalize(nomserviceimpl) + " " + nomserviceimpl.toLowerCase() + ") {\n" + "\n"
                + "        return " + nomserviceimpl.toLowerCase() + "Repository.save(" + nomserviceimpl.toLowerCase()
                + ");\n" + "    }\n" + "    @Override\n" + "    public void delete("
                + uppercasefunct.capitalize(nomserviceimpl) + " " + nomserviceimpl.toLowerCase() + ") {\n" + "\n" + "  "
                + nomserviceimpl.toLowerCase() + "Repository.delete(" + nomserviceimpl.toLowerCase() + ");\n"
                + "    }\n" + "	@Override\r\n" + "	public Optional<" + uppercasefunct.capitalize(nomserviceimpl)
                + "> findById(Long id) {\r\n" + "		return " + nomserviceimpl.toLowerCase()
                + "Repository.findById(id);\r\n" + "	}" + "}";

        FileWriter myWriteservices = new FileWriter(
                packservi + "/" + uppercasefunct.capitalize(nomserviceimpl) + "ServiceImpl.java");
        myWriteservices.write("\n" + servicefile);
        myWriteservices.close();

    }

    /*
	 * +++++++++++++++++++ Fin generation des fichiers
	 * service+++++++++++++++++++++++++
     */
 /*
	 * ++++++++++++++++++++++++generation
	 * controlleur++++++++++++++++++++++++++++++++++
	 * 
     */
    public String functionCreateFormWithFile(String composant, List<Champs> listChamps, List<Champs> listChampsfile) {

        StringProcess uppercasefunct = new StringProcess();

        String function = "@PostMapping(value=\"" + composant + "/create\",method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)\n\r";

        function += "public ServeurResponse createFiles(HttpServletRequest request";

        for (Champs champs : listChampsfile) {

            function += " , MultipartFile " + champs.getChpNom();

        }

        function += "){\n\r";

        function += "ServeurResponse response = new ServeurResponse();\n"
                + uppercasefunct.capitalize(composant) + " " + composant.toLowerCase() + ";";

        function += "try{\n" + composant.toLowerCase() + " = new ObjectMapper().readValue(\n"
                + "request.getParameter(\"" + composant.toLowerCase() + "\"), new TypeReference<" + uppercasefunct.capitalize(composant) + ">(){}\n"
                + ");"
                + "}\ncatch(Exception ex){\n ex.printStackTrace();}";

        function += "try {\n";

        for (Champs champs : listChampsfile) {

            function += composant.toLowerCase() + ".set" + uppercasefunct.capitalize(champs.getChpNom()) + "(" + champs.getChpNom() + ".getBytes());\n";

        }

        function
                += " } catch (IOException e1) {\n"
                + " e1.printStackTrace();\n"
                + " }";

        function += "}\n\r";

        for (Champs champs : listChampsfile) {
            function += " @GetMapping(\"" + composant.toLowerCase() + "/downloadFile/{" + champs.getChpNom() + "}/{id}\")\r\n"
                    + "	public ResponseEntity<ByteArrayResource> downloadfile(@PathVariable String " + champs.getChpNom() + ",@PathVariable Long id,  HttpServletResponse response){\r\n"
                    + "	 \r\n" + "		Optional<" + uppercasefunct.capitalize(composant) + "> data = "
                    + composant.toLowerCase() + "Service.findById(id);\r\n"
                    + "             File file = new File(data.get().get"
                    + uppercasefunct.capitalize(champs.getChpLabel()) + "().toLowerCase());\r\n"
                    + "             System.out.println(file.getName());\r\n"
                    + "		return ResponseEntity.ok()\r\n"
                    + "				.contentType(MediaType.APPLICATION_PDF)\r\n"
                    + "				.header(HttpHeaders.CONTENT_DISPOSITION,\"attachment:filename=\"+file.getName())\r\n"
                    + "				.body(new ByteArrayResource(data.get().get"
                    + uppercasefunct.capitalize(champs.getChpNom()) + "()));\r\n" + "             \r\n"
                    + "            \r\n" + "	}\n\r";
        }

        return function;

    }

    public void generationFileControllerBack(String nomcontroller, List<Champs> listChamps, List<Champs> listChampsfile) throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        File packcontroller = new File(this.repo + "/" + this.dossierPackage + "controller");
        packcontroller.mkdirs();
        File cntrlfiles = new File(packcontroller, "" + uppercasefunct.capitalize(nomcontroller) + "Controller.java");
        cntrlfiles.createNewFile();

        String controllerfile = this.packageKey + " " + this.packagename + ".controller;\n" + "\n" + this.importKey
                + " " + this.packagename + ".entity." + uppercasefunct.capitalize(nomcontroller) + ";\n"
                + this.importKey + " " + this.packagename + ".service.I" + uppercasefunct.capitalize(nomcontroller)
                + "Service;\n" + "import java.util.Optional;\n" + "\n"
                + "import org.springframework.beans.factory.annotation.Autowired;\n"
                + "import org.springframework.web.bind.annotation.CrossOrigin;\n"
                + "import org.springframework.web.bind.annotation.GetMapping;\n"
                + "import org.springframework.web.bind.annotation.PathVariable;\n"
                + "import org.springframework.web.bind.annotation.PostMapping;\n"
                + "import org.springframework.web.bind.annotation.RequestBody;\n"
                + "import org.springframework.web.bind.annotation.RestController;\n"
                + "import javax.validation.constraints.NotNull;\n" + "import javax.servlet.http.HttpServletRequest;\n"
                + "import org.springframework.web.bind.annotation.RequestParam;\n"
                + "import org.springframework.web.multipart.MultipartFile;\n" + "\r\n"
                + "import com.fasterxml.jackson.core.JsonParseException;\r\n"
                + "import com.fasterxml.jackson.core.type.TypeReference;\r\n"
                + "import com.fasterxml.jackson.databind.JsonMappingException;\r\n"
                + "import com.fasterxml.jackson.databind.ObjectMapper;\r\n" + "import java.io.IOException;\n"
                + "import javax.ws.rs.core.HttpHeaders;\r\n" + "import javax.servlet.http.HttpServletRequest;\r\n"
                + "import javax.servlet.http.HttpServletResponse;\n"
                + "import org.springframework.core.io.ByteArrayResource;\r\n"
                + "import org.springframework.http.MediaType;\n" + "import org.springframework.http.ResponseEntity;\n"
                + "import java.io.File;" + "\n" + this.importKey + " " + this.packagename
                + ".ServeurResponse.ServeurResponse;" + "\n" + "@RestController\n" + "public class "
                + uppercasefunct.capitalize(nomcontroller) + "Controller {\n" + "\n" + " @Autowired \n" + "I"
                + uppercasefunct.capitalize(nomcontroller) + "Service " + nomcontroller.toLowerCase() + "Service;\n"
                + "@GetMapping(\"" + nomcontroller.toLowerCase() + "/list\")\n" + "    public ServeurResponse getAll"
                + nomcontroller + "() {\n" + "        Iterable<" + uppercasefunct.capitalize(nomcontroller) + "> "
                + nomcontroller.toLowerCase() + " = " + nomcontroller.toLowerCase() + "Service.findAll();\n"
                + "        ServeurResponse response = new ServeurResponse();\n" + " "
                + "  if (" + nomcontroller.toLowerCase() + " == null) {\n" + "          "
                + "  response.setStatut(false);\n"
                + "            response.setData(null);\n"
                + "            response.setDescription(\"Aucune élèment trouvé.\");\n" + "\n" + " } "
                + "else {\n"
                + "      response.setStatut(true);\n" + " "
                + "     response.setData(" + nomcontroller.toLowerCase() + ");\n"
                + "         response.setDescription(\"Données récupérées.\");\n" + "       "
                + " }\n"
                + "        return response;\n"
                + "}\n"
                + " @PostMapping(\"" + nomcontroller.toLowerCase() + "/create\")\n"
                + "public ServeurResponse create(@RequestBody " + uppercasefunct.capitalize(nomcontroller) + " " + nomcontroller.toLowerCase() + ") {\n" + "\n"
                + "ServeurResponse response = new ServeurResponse();\n" + "\n" + "" + nomcontroller.toLowerCase()
                + "Service.save(" + nomcontroller.toLowerCase() + ");\n" + "response.setStatut(true);\n"
                + "response.setDescription(\"Enregistrement réussi\");\n" + "response.setData("
                + nomcontroller.toLowerCase() + ");\n" + "\n" + "return response;\n" + "}\n"
                + "@PostMapping(\"" + nomcontroller.toLowerCase() + "/update\")\n"
                + "     public ServeurResponse update(@RequestBody " + uppercasefunct.capitalize(nomcontroller) + " " + nomcontroller.toLowerCase() + ") {\n"
                + "          ServeurResponse response = new ServeurResponse();\n" + "\n" + "          "
                + nomcontroller.toLowerCase() + "Service.save(" + nomcontroller.toLowerCase() + ");\n"
                + "          response.setStatut(true);\n"
                + "               response.setDescription(\"Données mis à jour\");\n" + "          return response;\n"
                + "     }\n"
                + " @PostMapping(\"" + nomcontroller.toLowerCase() + "/delete\")\n"
                + "     public ServeurResponse delete(@RequestBody " + uppercasefunct.capitalize(nomcontroller) + " "
                + nomcontroller.toLowerCase() + ") {\n"
                + "          ServeurResponse response = new ServeurResponse();\n" + "          "
                + nomcontroller.toLowerCase() + "Service.delete(" + nomcontroller.toLowerCase() + ");\n"
                + "          response.setStatut(true);\n"
                + "          response.setDescription(\"Données supprimé avec succès\");\n"
                + "          return response;\n"
                + "}\n";
        for (int k = 0; k < listChampsfile.size(); k++) {
            if (listChampsfile.size() <= 1) {
                controllerfile = controllerfile
                        + " @PostMapping(\"" + nomcontroller.toLowerCase() + "/createfile\")\n"
                        + "   public ServeurResponse create(HttpServletRequest request, @RequestParam(\""
                        + nomcontroller.toLowerCase() + "\") MultipartFile file) {\r\n" + "\r\n"
                        + "        ServeurResponse response = new ServeurResponse();\r\n" + "\r\n" + "        "
                        + uppercasefunct.capitalize(nomcontroller) + " " + nomcontroller.toLowerCase() + ";\r\n"
                        + "        try {\r\n" + "            " + nomcontroller.toLowerCase()
                        + " = new ObjectMapper().readValue(\r\n" + "request.getParameter(\""
                        + nomcontroller.toLowerCase() + "\"), new TypeReference<"
                        + uppercasefunct.capitalize(nomcontroller) + ">() {\r\n" + "            }\r\n"
                        + "            );\r\n" + "\r\n" + "            " + nomcontroller.toLowerCase() + ".set"
                        + uppercasefunct.capitalize(listChamps.get(k).getChpNom()) + "(file.getBytes());\r\n" + ""
                        + nomcontroller.toLowerCase() + ".setExtention(file.getContentType());" + "\r\n" + ""
                        + nomcontroller.toLowerCase() + "Service.save(" + nomcontroller.toLowerCase() + ");\n"
                        + "            response.setStatut(true);\r\n"
                        + "            response.setDescription(\"Enregistrement réussi\");\r\n"
                        + "            response.setData(" + nomcontroller.toLowerCase() + ");\r\n"
                        + "        } catch (JsonParseException e) {\r\n" + " e.printStackTrace();\r\n"
                        + "        } catch (JsonMappingException e) {\r\n" + "e.printStackTrace();\r\n"
                        + "        } catch (IOException e) {\r\n" + " e.printStackTrace();\r\n"
                        + "        } catch (Exception e) {\r\n" + " e.printStackTrace();\r\n"
                        + "        }\r\n return response;\r\n" + "  }\r\n";

                controllerfile = controllerfile + " @GetMapping(\"" + nomcontroller.toLowerCase() + "/downloadFile/{id}\")\r\n"
                        + "	public ResponseEntity<ByteArrayResource> downloadfile(@PathVariable Long id,  HttpServletResponse response){\r\n"
                        + "	 \r\n" + "		Optional<" + uppercasefunct.capitalize(nomcontroller) + "> data = "
                        + nomcontroller.toLowerCase() + "Service.findById(id);\r\n"
                        + "             File file = new File(data.get().get"
                        + uppercasefunct.capitalize(listChamps.get(k).getChpLabel()) + "().toLowerCase());\r\n"
                        + "             System.out.println(file.getName());\r\n"
                        + "		return ResponseEntity.ok()\r\n"
                        + "				.contentType(MediaType.APPLICATION_PDF)\r\n"
                        + "				.header(HttpHeaders.CONTENT_DISPOSITION,\"attachment:filename=\"+file.getName())\r\n"
                        + "				.body(new ByteArrayResource(data.get().get"
                        + uppercasefunct.capitalize(listChamps.get(k).getChpNom()) + "()));\r\n" + "             \r\n"
                        + "            \r\n" + "	}";
            }

        }
        if (listChampsfile.size() > 1) {
            controllerfile = controllerfile + functionCreateFormWithFile(nomcontroller, listChamps, listChampsfile);
        }

        controllerfile = controllerfile + "}";

        FileWriter myWritecontroller = new FileWriter(packcontroller + "\\" + nomcontroller + "Controller.java");
        myWritecontroller.write("\n" + controllerfile);
        myWritecontroller.close();

    }
    /// fin back

    // zipper le projet
    public ZipOutputStream zipper(Long id, String nomApp, String desc) throws IOException {
        String dossier_src = this.repo;
        String fichier_zip_desc = this.repo + "\\" + id + "-" + nomApp + ".zip";
        List<String> liste;
        final int BUFFER = 2048;

        File node = new File(dossier_src);
        liste = new ArrayList<>();
        listeFichiers(node, liste, dossier_src);

        byte buffer[] = new byte[BUFFER];

        FileOutputStream fileos = new FileOutputStream(fichier_zip_desc);
        ZipOutputStream zipos = new ZipOutputStream(fileos);

        for (String file : liste) {
            ZipEntry zipentry = new ZipEntry(file);
            zipos.putNextEntry(zipentry);
            FileInputStream in = new FileInputStream(dossier_src + File.separator + file);

            int lec;
            while ((lec = in.read(buffer)) > 0) {
                zipos.write(buffer, 0, lec);
            }

            in.close();
            System.out.println("Fichier " + file + " ajouté");
        }

        zipos.closeEntry();
        zipos.close();

        System.out.println("Fichier compressé créé!");
        return zipos;

    }

    // preparation zip
    public static List listeFichiers(File node, List<String> liste, String dossier_src) {

        // ajouter les fichiers
        if (node.isFile()) {
            String file = node.getAbsoluteFile().toString();
            String chemin = file.substring(dossier_src.length() + 1, file.length());
            liste.add(chemin);
        }

        if (node.isDirectory()) {
            String[] sousFichier = node.list();
            for (String filename : sousFichier) {
                listeFichiers(new File(node, filename), liste, dossier_src);
            }
        }
        return liste;
    }

    public boolean createProjet(String name) {
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("cmd", "/C", this.repo + "/createprojet.sh " + name).inheritIO()
                        .start();

                exitValue = process.waitFor();
            } else {
                Process process = new ProcessBuilder("cmd", "/C", this.repo + "/createprojet.bat " + name).inheritIO()
                        .start();

                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean zipperProjet(String destination) {
        int exitValue = 1;
        File packentity = new File(this.cheminZip);
        packentity.mkdirs();
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c", this.fichierConfig + "/zipper.sh "
                        + this.cheminZip + "/" + destination.toLowerCase() + " " + this.repo).inheritIO().start();
                exitValue = process.waitFor();
            } else {
                Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig + "/zipper.bat " + this.cheminZip
                        + "/" + destination.toLowerCase() + " " + this.repo).inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean createComposant(String nomProjet, String name) {
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        System.out.println(SE);
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c",
                        this.fichierConfig + "/component.sh " + name.toLowerCase() + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            } else {
                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/component.bat " + name.toLowerCase() + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean deleteComposant(String nomProjet, String name) {
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {

            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c", this.fichierConfig + "/supprimer.sh src/app/"
                        + name.toLowerCase() + "/" + " " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();
            } else {

                Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig + "/supprimer.bat src/app/"
                        + name.toLowerCase() + "/" + " " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean deleteZip() {
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {

            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c",
                        this.fichierConfig + "/supprimerzip.sh " + " " + this.cheminZip).inheritIO().start();
                exitValue = process.waitFor();
            } else {

                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/supprimerzip.bat " + " " + this.cheminZip).inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean createComposantadd(String nomProjet, String name) {
        StringProcess uppercasefunct = new StringProcess();
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c",
                        this.fichierConfig + "/component.sh " + name.toLowerCase() + "/components/add"
                        + uppercasefunct.capitalize(name.toLowerCase()) + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            } else {
                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/component.bat " + name.toLowerCase() + "/components/add"
                        + uppercasefunct.capitalize(name.toLowerCase()) + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean createComposantview(String nomProjet, String name) {
        StringProcess uppercasefunct = new StringProcess();
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c",
                        this.fichierConfig + "/component.sh " + name.toLowerCase() + "/components/view"
                        + uppercasefunct.capitalize(name.toLowerCase()) + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            } else {
                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/component.bat " + name.toLowerCase() + "/components/view"
                        + uppercasefunct.capitalize(name.toLowerCase()) + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean createRouting(String nomProjet, String name) {
        StringProcess uppercasefunct = new StringProcess();
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c", this.fichierConfig + "/createrouting.sh "
                        + name.toLowerCase() + " " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();

            } else {
                Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig + "/createrouting.bat "
                        + name.toLowerCase() + " " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean createComposantList(String nomProjet, String name) {
        StringProcess uppercasefunct = new StringProcess();
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c",
                        this.fichierConfig + "/component.sh " + name.toLowerCase() + "/components/list"
                        + uppercasefunct.capitalize(name.toLowerCase()) + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();

            } else {
                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/component.bat " + name.toLowerCase() + "/components/list"
                        + uppercasefunct.capitalize(name.toLowerCase()) + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean createComposantEdit(String nomProjet, String name) {
        StringProcess uppercasefunct = new StringProcess();
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c",
                        this.fichierConfig + "/component.sh " + name.toLowerCase() + "/components/edit"
                        + uppercasefunct.capitalize(name.toLowerCase()) + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();

            } else {
                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/component.bat " + name.toLowerCase() + "/components/edit"
                        + uppercasefunct.capitalize(name.toLowerCase()) + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean createService(String name, String nomProjet) throws IOException {
        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c",
                        this.fichierConfig + "/service.sh " + name.toLowerCase() + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();

            } else {
                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/service.bat " + name.toLowerCase() + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean createModel(String name, String nomProjet) throws IOException {

        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c", this.fichierConfig + "/createclass.sh "
                        + name.toLowerCase() + " " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();
            } else {
                Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig + "/createclass.bat "
                        + name.toLowerCase() + " " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean deleteModel(String name, String nomProjet) throws IOException {

        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c", this.fichierConfig + "/supprimer.sh src/app/"
                        + name.toLowerCase() + "/model/" + name.toLowerCase() + "/" + " " + this.repo + "/" + nomProjet)
                        .inheritIO().start();
                exitValue = process.waitFor();
            } else {
                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/supprimer.bat src/app/" + name.toLowerCase() + "/model/"
                        + name.toLowerCase() + "/" + "  " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public boolean deleteService(String name, String nomProjet) throws IOException {

        int exitValue = 1;
        String SE = System.getProperty("os.name").toLowerCase();
        try {
            if (SE.indexOf("nux") >= 0) {
                Process process = new ProcessBuilder("/bin/bash", "-c",
                        this.fichierConfig + "/supprimer.sh src/app/" + name.toLowerCase() + "/service/"
                        + name.toLowerCase() + "/" + " " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();
            } else {
                Process process = new ProcessBuilder("cmd", "/C",
                        this.fichierConfig + "/supprimer.bat src/app/" + name.toLowerCase() + "/service/"
                        + name.toLowerCase() + "/" + "  " + this.repo + "/" + nomProjet).inheritIO().start();
                exitValue = process.waitFor();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitValue == 0;
    }

    public void updateComponentCss(String composant, String nomProjet) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/add-"
                    + composant.toLowerCase() + "/" + "add-" + composant.toLowerCase() + ".component.scss");
            myWriter.write(".image{\r\n" + "  background: url(\"/assets/images/gainde/ia.jpg\");\r\n" + "}\r\n" + "\r\n"
                    + "#test {\r\n" + "  background-image: url();\r\n" + "  background-size: cover;\r\n"
                    + "  /* background: url(http://www.petite-annonce-gratuite.com/layout_images/carte-france.png) no-repeat;\r\n"
                    + "  width: 100%;\r\n" + "  height: 390px;*/\r\n" + "}\r\n" + "\r\n" + "mat-grid-tile {\r\n"
                    + "  background: rgb(238, 239, 240);\r\n" + "}\r\n" + "\r\n"
                    + "@media screen and (min-width: 100%) and (max-width: 100%) {\r\n" + "  #test {\r\n"
                    + "      width: 100%;\r\n" + "      background-size: cover;\r\n" + "  }\r\n" + "\r\n" + "}\r\n"
                    + "body{\r\n" + "  background-image: url(\"/assets/images/gainde/ia1.jpg\");\r\n"
                    + "  background-size: cover;\r\n" + "\r\n" + "}\r\n" + ".right{\r\n" + "  margin-left: 20%;\r\n"
                    + "}\r\n" + ".top{\r\n" + "  margin-top: 11%;\r\n" + "}\r\n" + ".text{\r\n"
                    + "  font-size: 11px;\r\n" + "}\r\n" + "\r\n" + ".center{\r\n" + "  margin-bottom: 2%;\r\n"
                    + "  display: block;\r\n" + "  margin:auto;\r\n" + "}\r\n" + "\r\n" + ".login-page{\r\n"
                    + " height: 90vh;\r\n" + " //width: 100%;\r\n" + "  margin-top: -10%;\r\n"
                    + " overflow-wrap: scroll;" + "\r\n" + "}\r\n" + "\r\n" + ".login-spacer{\r\n"
                    + "  height: 12vh;\r\n" + "}\r\n" + "\r\n" + ".login-container{\r\n" + "  margin: auto;\r\n"
                    + "  width: 300px;\r\n" + "  padding: 1vh;\r\n" + "  background-color: #fff;\r\n" + "}\r\n" + "\r\n"
                    + ".login-form{\r\n" + "  display: flex;\r\n" + "  flex-direction: column;\r\n" + "}\r\n" + "\r\n"
                    + "#login-fab {\r\n" + "border-radius: 50%;\r\n" + "  height: 56px;\r\n" + "  margin: auto;\r\n"
                    + "  min-width: 56px;\r\n" + "  width: 56px;\r\n" + "  overflow: hidden;\r\n"
                    + "  background: #1e88e5;\r\n"
                    + "  box-shadow: 0 1px 1.5px 0 rgba(0,0,0,.12), 0 1px 1px 0 rgba(0,0,0,.24);\r\n"
                    + "  margin-top: -35px;\r\n" + "  text-align: center;\r\n" + "  left: 0;\r\n" + "  right: 0;\r\n"
                    + "}\r\n" + "\r\n" + "#lock-icon{\r\n" + "  line-height: 56px;\r\n" + "  color: #fff;\r\n" + "}\r\n"
                    + ".card-heading{\r\n" + "  text-align: center;\r\n" + "  color: rgba(0, 0, 0, 0.31);\r\n" + "}\r\n"
                    + "\r\n" + ".buttons .login{\r\n" + "  padding-top: 2vh;\r\n" + "  padding-bottom: 2vh;\r\n"
                    + "}\r\n" + "\r\n" + ".buttons .login > button{\r\n" + "  width: 40%;\r\n" + "}\r\n" + "button{\r\n"
                    + "  margin: 5%;\r\n" + "}\r\n" + ".buttons .register{\r\n" + "  display: flex;\r\n"
                    + "  padding-top: 4vh;\r\n" + "  padding-bottom: 2vh;\r\n" + "  justify-content: space-between;\r\n"
                    + "}\r\n" + ".app-header{\r\n" + "  color: #fff;\r\n" + "      background-color: #1e88e5;\r\n"
                    + "      text-align: center;\r\n" + "      margin-top: -3px;\r\n" + "      padding: 2px;\r\n"
                    + "  -webkit-box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n"
                    + "  -moz-box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n"
                    + "  box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n" + "  }\r\n" + "");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

// ON SUBMIT IF CONDITION
    public String conditionValueSubmitFalse(String formName, List<Champs> listChamps) {
        String condition = "";
        int size = listChamps.size();
        int i = 0;

        for (Champs champs : listChamps) {
            if (i == 1 && champs.getChpObligatoire() != null) {
                i = 0;
                condition += " || ";
            }

            if (champs.getChpObligatoire() != null && champs.getChpObligatoire()) {

                condition += formName + ".value." + champs.getChpNom() + "";
                if (champs.getChpType().equals("number")) {
                    condition += " <0";
                } else {
                    condition += "==\"\"";
                }
                i = 1;
            }

        }

        return condition;
    }

    public void updateComponent(String composant, String nomProjet, List<Champs> listChamps, List<Champs> listChampsfile) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/add-" + composant.toLowerCase() + "/" + "add-" + composant.toLowerCase()
                    + ".component.ts");
            myWriter.write("import { " + uppercasefunct.capitalize(composant) + "Service } from '../../service/" + composant.toLowerCase() + ".service';\r\n"
                    + "import { " + uppercasefunct.capitalize(composant) + " } from '../../model/" + composant.toLowerCase() + "';\r\n"
                    + "import { Component, OnInit,Input } from '@angular/core';\r\n"
                    + "import { Router } from '@angular/router';\r\n"
                    + "import { FormBuilder, Validators } from '@angular/forms';\r\n"
                    + "import { MatDialogRef, MatSnackBar } from '@angular/material';\r\n"
                    + "import { TranslateService } from '@ngx-translate/core';"
                    + "\r\n"
                    + "import { NotificationService } from '../../../shared/services/notification.service';\r\n"
                    + "@Component({\r\n"
                    + "  selector: 'app-add-" + composant.toLowerCase() + "',\r\n"
                    + "  templateUrl: './add-" + composant.toLowerCase() + ".component.html',\r\n"
                    + "  styleUrls: ['./add-" + composant.toLowerCase() + ".component.scss']\r\n"
                    + "})\r\n"
                    + "export class Add" + uppercasefunct.capitalize(composant) + "Component implements OnInit {\r\n"
                    + "result:any;\n"
                    + "  public " + composant.toLowerCase() + ": Array<File>;\n"
                    + " @Input() accept = '.';"
                    + "resp:any;\n"
                    + " isFileValid: boolean;\r\n"
                    + "  images;\r\n"
                    + "  href:any\r\n"
                    + "  base64Data: any;\r\n"
                    + "  based\r\n"
                    + "  isUpload: boolean;\n"
                    + "isfile:boolean;\n"
                    + "extention:any;\n"
                    + "startDate:any\n");
            for (int j = 0; j < listChamps.size(); j++) {
                if (listChamps.get(j).getChpType().equals("relation")) {
                    myWriter.write("" + getMappingObjet(listChamps.get(j).getChpNom().toLowerCase()).toString() + ":any\n");
                }
            }

            myWriter.write("\r\n");
            myWriter.write("" + uppercasefunct.capitalize(composant) + "Form = this.formbuild.group({");
            for (int i = 0; i < listChamps.size(); i++) {
                if (listChamps.get(i).getChpType().equals("relation")) {
                    myWriter.write(getMappingObjet(listChamps.get(i).getChpNom()).toString() + " :['', Validators.required],\r\n");
                } else {
                    myWriter.write(listChamps.get(i).getChpNom() + " :['', Validators.required],\r\n");
                }

            }
            myWriter.write(" });");
            myWriter.write("  constructor(private " + composant.toLowerCase() + "Service: " + uppercasefunct.capitalize(composant) + "Service,\r\n"
                    + "    private router: Router,"
                    + " private formbuild: FormBuilder, \r\n"
                    + "    private _snackBar: MatSnackBar,\r\n"
                    + "	private translate:TranslateService,\r\n"
                    + "private notification: NotificationService,\r\n"
                    + "    public dialogRef: MatDialogRef<Add" + uppercasefunct.capitalize(composant) + "Component >) {"
                    + "this.isfile=false }\r\n"
                    + "\r\n"
                    + "  ngOnInit() {\r\n");
            for (int j = 0; j < listChamps.size(); j++) {
                if (listChamps.get(j).getChpType().equals("relation")) {
                    myWriter.write("this.get" + uppercasefunct.capitalize(getMappingObjet(listChamps.get(j).getChpNom()).toString()) + "();");
                }
            }
            myWriter.write("  }\r\n"
                    + "\r\n");
            if (listChampsfile.size() != 0) {
                if (listChampsfile.get(0).getChpType().equals("file")) {

                    myWriter.write(" onSubmitFile() {\r\n"
                            + "    if (this." + uppercasefunct.capitalize(composant) + "Form.value) {\r\n"
                            + "      this." + composant.toLowerCase() + "Service.add" + uppercasefunct.capitalize(composant) + "(this." + composant.toLowerCase() + "[0],this." + uppercasefunct.capitalize(composant) + "Form.value).subscribe(data => {\r\n"
                            + "        if (data.statut) {\r\n"
                            + "          let ReportSaveSuccess;\r\n"
                            + "          this.translate.get('" + composant.toLowerCase() + ".confirmEnr').subscribe((res: string) => {\r\n"
                            + "            ReportSaveSuccess = res;\r\n"
                            + "          });\r\n"
                            + "          this.translate.get(ReportSaveSuccess).subscribe((res: string) => {\r\n"
                            + "            this.notification.success(res);\r\n"
                            + "          });\r\n"
                            + "\r\n"
                            + "         this." + uppercasefunct.capitalize(composant) + "Form.reset();\r\n"
                            + "          this.closeDialog();\r\n"
                            + "        }\r\n"
                            + "      }, error => {\r\n"
                            + "        let ReportSaveError;\r\n"
                            + "        this.translate.get('" + composant.toLowerCase() + ".erreurEnr').subscribe((res: string) => {\r\n"
                            + "          ReportSaveError = res;\r\n"
                            + "        });\r\n"
                            + "        this.translate.get(ReportSaveError).subscribe((res: string) => {\r\n"
                            + "          this.notification.error(res);\r\n"
                            + "        });\r\n"
                            + "      });\r\n"
                            + "    } else {\r\n"
                            + "      let errorChamps;\r\n"
                            + "      let form;\r\n"
                            + "      this.translate.get('control.error').subscribe((res: string) => {\r\n"
                            + "        form = res;\r\n"
                            + "      });\r\n"
                            + "\r\n"
                            + "      this.translate.get('control.required').subscribe((res: string) => {\r\n"
                            + "        errorChamps = res;\r\n"
                            + "      });\r\n"
                            + "      this.translate.get(errorChamps).subscribe((res: string) => {\r\n"
                            + "        this.notification.error(res);\r\n"
                            + "      });\r\n"
                            + " \r\n"
                            + "    }\r\n"
                            + "  }\n");
                    myWriter.write("  uploadImage(event: any) {\r\n"
                            + "this.isfile=true \n"
                            + "    if (event.target.files[0]) {\r\n"
                            + "       this.extention= event.target.files[0].name.split('.')[1].toLowerCase();\r\n"
                            + "       \r\n"
                            + "      this." + composant.toLowerCase() + " = event.target.files;\r\n"
                            + "      if (this." + composant.toLowerCase() + "[0].size >3000000){\r\n"
                            + "        this.translate.get(\"Verifier la taille du document!!\").subscribe((res: string) => {\r\n"
                            + "          this.notification.warn(res);\r\n"
                            + "        });\r\n"
                            + "        return;\r\n"
                            + "\r\n"
                            + "      }\r\n"
                            + "      this.isUpload = true;\r\n"
                            + "    }\r\n"
                            + "  }\r\n");

                }
            } else {
                myWriter.write("onSubmit() {\r\n"
                        + "if(" + conditionValueSubmitFalse("this." + uppercasefunct.capitalize(composant) + "Form", listChamps) + "){\r\n"
                        + "this.translate.get('error.champsrequired').subscribe((res: string)=>{\r\n"
                        + "this.notification.warn(res);\r\n"
                        + "return;"
                        + "});"
                        + "    this." + composant.toLowerCase() + "Service.create" + uppercasefunct.capitalize(composant) + "(this." + uppercasefunct.capitalize(composant) + "Form.value).subscribe(data => {\r\n"
                        + "  \r\n"
                        + "  this.result=data \n\r"
                        + "      if (this.result.statut) {\r\n"
                        + "        this.notification.info(this.result.description);\r\n"
                        + "        this." + uppercasefunct.capitalize(composant) + "Form.reset();\r\n"
                        + "        this.closeDialog();\r\n"
                        + "      }\r\n"
                        + "    }, error => {\r\n"
                        + "     this.notification.error('" + uppercasefunct.capitalize(composant) + " invalide');\r\n"
                        + "    });\r\n"
                        + "  }\r\n"
                        + "\r\n"
                        + "}\r\n");

            }
            myWriter.write("  closeDialog() {\r\n"
                    + "    this.dialogRef.close();\r\n"
                    + "  }\r\n");

            for (int j = 0; j < listChamps.size(); j++) {
                if (listChamps.get(j).getChpType().equals("relation")) {
                    myWriter.write("  get" + uppercasefunct.capitalize(getMappingObjet(listChamps.get(j).getChpNom()).toString()) + "(){\r\n"
                            + "         this." + composant.toLowerCase() + "Service.get" + uppercasefunct.capitalize(getMappingObjet(listChamps.get(j).getChpNom()).toString()) + "().subscribe(data=>{"
                            + "this.resp=data;\n"
                            + "this." + getMappingObjet(listChamps.get(j).getChpNom().toLowerCase()).toString() + "=this.resp.data})\r\n"
                            + "}\r\n");
                }

            }

            myWriter.write("}");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void updateComponentList(String composant, String nomProjet, List<Champs> listChamps) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/list-" + composant.toLowerCase() + "/" + "list-" + composant.toLowerCase()
                    + ".component.ts");
            myWriter.write("import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';\r\n"
                    + "import { ActivatedRoute, Router } from '@angular/router';\r\n"
                    + "import { MatPaginator, MatTableDataSource, MatSort, MatDialog, MatSnackBar } from '@angular/material';\r\n"
                    + "import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';\r\n"
                    + "import { Add" + uppercasefunct.capitalize(composant) + "Component } from '../add-" + composant.toLowerCase() + "/add-" + composant.toLowerCase() + ".component';\r\n"
                    + "import { Edit" + uppercasefunct.capitalize(composant) + "Component } from '../edit-" + composant.toLowerCase() + "/edit-" + composant.toLowerCase() + ".component';\r\n"
                    + "import { View" + uppercasefunct.capitalize(composant) + "Component } from '../view-" + composant.toLowerCase() + "/view-" + composant.toLowerCase() + ".component';\r\n"
                    + "import { " + uppercasefunct.capitalize(composant) + "Service } from '../../service/" + composant.toLowerCase() + ".service';\r\n"
                    + "import { " + uppercasefunct.capitalize(composant) + "} from '../../model/" + composant.toLowerCase() + "';\r\n"
                    + "import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';\r\n"
                    + "import { TranslateService } from '@ngx-translate/core';\r\n"
                    + "import { merge, Observable } from 'rxjs';\r\n"
                    + "import { NotificationService } from '../../../shared/services/notification.service';\r\n"
                    + "import * as fileSaver from 'file-saver';"
                    + "\r\n"
                    + "\r\n"
                    + "@Component({\r\n"
                    + "	selector: 'list-" + composant + "',\r\n"
                    + "	templateUrl: './list-" + composant + ".component.html',\r\n"
                    + "	styleUrls: ['./list-" + composant + ".component.scss']\r\n"
                    + "})\r\n"
                    + "export class List" + uppercasefunct.capitalize(composant) + "Component implements AfterViewInit {\r\n"
                    + "	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;\r\n"
                    + "	@ViewChild(MatSort) sort: MatSort;\r\n"
                    + "	form;\r\n result:any\r\n"
                    + "	dataSource: MatTableDataSource<" + uppercasefunct.capitalize(composant) + ">;\r\n"
                    + "   langue;\r\n"
                    + "	constructor(private " + composant.toLowerCase() + "Service: " + uppercasefunct.capitalize(composant) + "Service, private dialogRef: MatDialog,\r\n"
                    + "		private route: ActivatedRoute,\r\n"
                    + "		private formbuild: FormBuilder,\r\n"
                    + "		private _snackBar: MatSnackBar, \r\n"
                    + "		private translate: TranslateService,\r\n"
                    + "		private notification: NotificationService,"
                    + "		private router: Router) {\r\n"
                    + "	}\r\n"
                    + "	displayedColumns: string[] = [");
            for (int k = 0; k < listChamps.size(); k++) {
                if (listChamps.get(k).getChpType().equals("relation")) {
                    myWriter.write("'" + getMappingObjet(listChamps.get(k).getChpNom()).toString() + "',");
                } else {
                    myWriter.write("'" + listChamps.get(k).getChpNom() + "',");
                }

            }
            myWriter.write("'action'];\r\n");
            myWriter.write("\n\r columnDefinitions = [\n\r");
            for (int k = 0; k < listChamps.size(); k++) {
                myWriter.write("{ def: '" + listChamps.get(k).getChpNom() + "', label: '" + listChamps.get(k).getChpNom() + "', hide: false},");

            }

            myWriter.write("];\r\n");
            myWriter.write("formchamps:FormGroup = new FormGroup({\n\r");
            for (int k = 0; k < listChamps.size(); k++) {
                myWriter.write(listChamps.get(k).getChpNom() + ": new FormControl(false),\r\n");

            }
            myWriter.write("});\n\r");

            for (int k = 0; k < listChamps.size(); k++) {
                myWriter.write(listChamps.get(k).getChpNom() + "= this.formchamps.get('" + listChamps.get(k).getChpNom() + "');\r\n");

            }

            myWriter.write(" getDisplayedColumns():string[] {\r\n"
                    + "    return this.columnDefinitions.filter(cd=>!cd.hide).map(cd=>cd.def);\r\n"
                    + "  }\n\r");

            myWriter.write("mesChamps(){");
            String ok = "";
            for (int k = 0; k < listChamps.size(); k++) {
                myWriter.write("let o" + k + ":Observable<boolean> = this." + listChamps.get(k).getChpNom() + ".valueChanges;\n\r");

                if (k != 0) {
                    ok += ",";
                }
                ok += "o" + k;

            }
            myWriter.write("merge(" + ok + ").subscribe( v => { \n\r");
            for (int k = 0; k < listChamps.size(); k++) {

                myWriter.write("this.columnDefinitions[" + k + "].hide = this." + listChamps.get(k).getChpNom() + ".value;\n\r");

            }
            myWriter.write("});\n\r");
            myWriter.write("}");

            myWriter.write("\r\n"
                    + "	applyFilter(event: Event) {\r\n"
                    + "		const filterValue = (event.target as HTMLInputElement).value;\r\n"
                    + "		this.dataSource.filter = filterValue.trim().toLowerCase();\r\n"
                    + "	}\r\n"
                    + "\r\n"
                    + "	ngAfterViewInit() {\r\n"
                    + "		this.list" + uppercasefunct.capitalize(composant) + "();\r\n"
                    + "this.mesChamps();"
                    + "	}\r\n"
                    + "	ngOnInit() {\r\n"
                    + "		}\r\n"
                    + "\r\n"
                    + "	list" + uppercasefunct.capitalize(composant) + "() {\r\n"
                    + "		this." + composant.toLowerCase() + "Service.get" + uppercasefunct.capitalize(composant) + "All().subscribe(data => {\r\n"
                    + "this.form = data"
                    + "\r\n"
                    + "			if (this.form.statut) {\r\n"
                    + "				//console.log('------------------------------');\r\n"
                    + "				console.log(this.form);\r\n"
                    + "				this.dataSource = new MatTableDataSource<" + uppercasefunct.capitalize(composant) + ">(this.form.data);\r\n"
                    + "				this.dataSource.paginator = this.paginator;\r\n"
                    + "				this.dataSource.sort = this.sort;\r\n"
                    + "			} else {\r\n"
                    + "				window.alert(this.form.description);\r\n"
                    + "			}\r\n"
                    + "		})\r\n"
                    + "	}\r\n"
                    + "\r\n"
                    + "\r\n"
                    + "	openDialogAdd(): void {\r\n"
                    + "		const dialog = this.dialogRef.open(Add" + uppercasefunct.capitalize(composant) + "Component, {\r\n"
                    + "			width: '700px',\r\n,"
                    + "			 disableClose: true,\n"
                    + "\r\n"
                    + "		}).afterClosed().subscribe(result => {\r\n"
                    + "			//location.reload();\r\n"
                    + "			this.list" + uppercasefunct.capitalize(composant) + "();\r\n"
                    + "		});\r\n"
                    + "\r\n"
                    + "	}\r\n"
                    + "	openDialogUpdate(username) {\r\n"
                    + "		console.log(username);\r\n"
                    + "		const dialog1 = this.dialogRef.open(Edit" + uppercasefunct.capitalize(composant) + "Component, {\r\n"
                    + "			width: '700px',\r\n"
                    + "			data: username,\r\n"
                    + "			 disableClose: true,\n"
                    + "		}).afterClosed().subscribe(result => {\r\n"
                    + "			//location.reload();\r\n"
                    + "			this.list" + uppercasefunct.capitalize(composant) + "();\r\n"
                    + "		});\r\n"
                    + "	}\r\n"
                    + "openDialogDeleteUser(username) {\r\n"
                    + "		const message = \"utilisateur.alert-suppression\";\r\n"
                    + "		const dialogData = new ConfirmDialogModel(\"utilisateur.titre-suppression\", message);\r\n"
                    + "		const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {\r\n"
                    + "			maxWidth: \"400px\",\r\n"
                    + "			data: dialogData\r\n"
                    + "		});\r\n"
                    + "		dialogRef.afterClosed().subscribe(dialogResult => {\r\n"
                    + "			if (dialogResult === true) {\r\n"
                    + "				this.delete(username);\r\n"
                    + "			}\r\n"
                    + "		});\r\n"
                    + "	}\r\n"
                    + " delete(data) {\r\n"
                    + "		let messageSuccess;\r\n"
                    + "		let messageError;\r\n"
                    + "		this.translate.get('" + composant.toLowerCase() + ".confirm-suppression').subscribe((res: string) => {\r\n"
                    + "			messageSuccess = res;\r\n"
                    + "		});\r\n"
                    + "		this.translate.get('" + composant.toLowerCase() + ".erreur-suppression').subscribe((res: string) => {\r\n"
                    + "			messageError = res;\r\n"
                    + "		});\r\n"
                    + "		this." + composant.toLowerCase() + "Service.delete" + uppercasefunct.capitalize(composant) + "(data).subscribe(data => {\r\n"
                    + "				this.result=data\r\n"
                    + "			if (this.result.statut) {\r\n"
                    + "				 this.notification.info(messageSuccess);\r\n"
                    + "			} else {\r\n"
                    + "				 this.notification.error(messageError);\r\n"
                    + "			}\r\n"
                    + "			this.list" + uppercasefunct.capitalize(composant) + "();\r\n"
                    + "		});\r\n"
                    + "	}\r\n"
                    + "openDialogView(username) {\r\n"
                    + "		console.log(username);\r\n"
                    + "		const dialog1 = this.dialogRef.open(View" + uppercasefunct.capitalize(composant) + "Component, {\r\n"
                    + "			width: '700px',\r\n"
                    + "			data: username\r\n"
                    + "		}).afterClosed().subscribe(result => {\r\n"
                    + "			//location.reload();\r\n"
                    + "			this.list" + uppercasefunct.capitalize(composant) + "();\r\n"
                    + "		});\r\n"
                    + "	}\n"
                    + "");
            for (int w = 0; w < listChamps.size(); w++) {
                if (listChamps.get(w).getChpType().equals("file")) {
                    myWriter.write("downloadImage(data){\r\n"
                            + "		this." + composant.toLowerCase() + "Service.download(data.id).subscribe(resp=>{\r\n"
                            + "		this.saveFile(resp.body, \"fichier:\"+data.id,data.extention);\r\n"
                            + "\r\n"
                            + "		});\r\n"
                            + "\r\n"
                            + "	  }\r\n"
                            + "	  saveFile(data: any,  filename?: string, extention?:any) {\r\n"
                            + "      let blob = new Blob([data], {type:extention});\r\n"
                            + "		fileSaver.saveAs(blob,  filename);\r\n"
                            + "	  }");
                    break;
                }
            }
            myWriter.write("}");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void updateRoutingForm(String nomprojet, String composant) throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        FileWriter myWriter = new FileWriter(
                this.repo + "/" + nomprojet + "/src/app/" + composant.toLowerCase() + "/" + composant.toLowerCase() + "-routing.module.ts");
        myWriter.write("import { NgModule } from '@angular/core';\r\n"
                + "import { Routes, RouterModule } from '@angular/router';\r\n"
                + "import { " + uppercasefunct.capitalize(composant) + "Component } from './" + composant.toLowerCase() + ".component';\r\n"
                + "import { List" + uppercasefunct.capitalize(composant) + "Component } from './components/list-" + composant.toLowerCase() + "/list-" + composant.toLowerCase() + ".component';\r\n"
                + "\r\n"
                + "const routes: Routes = [\r\n"
                + "  {\r\n"
                + "    path: '', component: " + uppercasefunct.capitalize(composant) + "Component,\r\n"
                + "    children: [\r\n"
                + "      { path: '', component: List" + uppercasefunct.capitalize(composant) + "Component },\r\n"
                + "    ],\r\n"
                + "\r\n"
                + "  },\r\n"
                + "  { path: '**', redirectTo: '' }\r\n"
                + "\r\n"
                + "];\r\n"
                + "\r\n"
                + "@NgModule({\r\n"
                + "  imports: [RouterModule.forChild(routes)],\r\n"
                + "  exports: [RouterModule]\r\n"
                + "})\r\n"
                + "export class " + uppercasefunct.capitalize(composant) + "RoutingModule { }");
        myWriter.close();
    }

    public void updateRoutingApp(String nomprojet, String composant, List<Formulaire> listFormulaire) throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        FileWriter myWriter = new FileWriter(
                this.repo + "/" + nomprojet + "/src/app/app-routing.module.ts");
        myWriter.write("import { NgModule } from '@angular/core';\r\n"
                + "import { Routes, RouterModule } from '@angular/router';\r\n"
                + "import { LoginComponent } from './login/login.component';\r\n"
                + "//import { HomeComponent } from './home/home.component';\r\n"
                + "import { GuardGuard } from './utilisateur/services/guard.guard';\r\n"
                + "import { AuthGuard } from './login/services/auth/auth.guard';\r\n"
                + "import { LandingComponent } from './login/components/landing/landing.component';\n\r"
                + "import { PaiementRedirectComponent } from './paiement/components/paiement-redirect/paiement-redirect.component';\n\r"
                + "const routes: Routes = [ { path: 'login',loadChildren: './login/login.module#LoginModule' },\r\n"
                + "  { path: 'landing',loadChildren: './login/login.module#LoginModule',component:LandingComponent },\r\n"
                + "  { path: 'home',canActivate: [AuthGuard], loadChildren: './home/home.module#HomeModule' },\r\n"
                + "  { path: 'application',canActivate: [AuthGuard], loadChildren: './application/application.module#ApplicationModule' },\r\n"
                + "  { path: 'utilisateur',canActivate: [AuthGuard], loadChildren: './utilisateur/utilisateur.module#UtilisateurModule' },\r\n"
                + "  { path: 'workflow', canActivate: [AuthGuard], loadChildren: './workflow/workflow.module#WorkflowModule' },\r\n"
                + "  { path: 'parametrage', canActivate: [AuthGuard], loadChildren: './parametrage/parametrage.module#ParametrageModule' },\r\n"
                + "  { path: 'formulaire', canActivate: [AuthGuard], loadChildren: './formulaire/formulaire.module#FormulaireModule' },\r\n"
                + "  { path: 'fichier', canActivate: [AuthGuard], loadChildren: './fichier/fichier.module#FichierModule' },\r\n"
                + "  { path: 'procedures', canActivate: [AuthGuard], loadChildren: './procedures/procedures.module#ProceduresModule' },\r\n"
                + "  { path: 'exception',loadChildren: './exception/exception.module#ExceptionModule' },\r\n"
                + "  { path: 'paiement/redirect',loadChildren: './paiement/paiement.module#PaiementModule', component: PaiementRedirectComponent },\r\n"
                + "  { path: 'paiement', canActivate: [AuthGuard], loadChildren: './paiement/paiement.module#PaiementModule' },\r\n"
                + "  { path: 'qrcode', canActivate: [AuthGuard], loadChildren: './qrcode/qrcode.module#QrcodeModule' },\r\n"
                + "  { path: 'document', canActivate: [AuthGuard], loadChildren: './documents/document.module#DocumentModule' },\r\n"
                + "  { path: 'configuration', canActivate: [AuthGuard], loadChildren: './configuration/configuration.module#ConfigurationModule' },\r\n"
                + "  { path: 'inscription', loadChildren: './inscription/inscription.module#InscriptionModule'},");
        for (int k = 0; k < listFormulaire.size(); k++) {
            myWriter.write("{ path: '" + listFormulaire.get(k).getFrmNom().toLowerCase() + "', canActivate: [GuardGuard], loadChildren: './" + listFormulaire.get(k).getFrmNom().toLowerCase() + "/" + listFormulaire.get(k).getFrmNom().toLowerCase() + ".module#" + uppercasefunct.capitalize(listFormulaire.get(k).getFrmNom()) + "Module' },\r\n");
        }

        myWriter.write("\r\n"
                + "  { path: '**', redirectTo: '/login', pathMatch: 'full' },\r\n"
                + "];\r\n"
                + "\r\n"
                + "@NgModule({\r\n"
                + "  imports: [RouterModule.forRoot(routes)],\r\n"
                + "  exports: [RouterModule],\r\n"
                + "  providers: [GuardGuard]\r\n"
                + "})\r\n"
                + "export class AppRoutingModule { }\r\n"
                + "");
        myWriter.close();
    }

    public void updateComposantFormTs(String nomprojet, String composant) throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        FileWriter myWriter = new FileWriter(
                this.repo + "/" + nomprojet + "/src/app/" + composant.toLowerCase() + "/" + composant.toLowerCase() + ".component.ts");
        myWriter.write("import { Component, OnInit } from '@angular/core';\r\n"
                + "import { MatIconRegistry } from '@angular/material';\r\n"
                + "import { DomSanitizer } from '@angular/platform-browser';\r\n"
                + "\r\n"
                + "@Component({\r\n"
                + "  selector: 'app-" + composant.toLowerCase() + "',\r\n"
                + "  template: `\r\n"
                + "  <app-sidenav></app-sidenav>\r\n"
                + "  `,\r\n"
                + "  styles: []\r\n"
                + "})\r\n"
                + "export class " + uppercasefunct.capitalize(composant) + "Component implements OnInit {\r\n"
                + "\r\n"
                + "  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {\r\n"
                + "    iconRegistry.addSvgIconSet(sanitizer.bypassSecurityTrustResourceUrl('assets/avatars.svg'));\r\n"
                + "   }\r\n"
                + "\r\n"
                + "  ngOnInit() {\r\n"
                + "  }\r\n"
                + "\r\n"
                + "}");
        myWriter.close();
    }

    public void updateComponentAddHtml(String nomprojet, String composant, List<Champs> listChamps, IValue iValue, List<Champs> listChampsfile) throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        FileWriter myWriter = new FileWriter(
                this.repo + "/" + nomprojet + "/src/app/" + composant.toLowerCase() + "/components/add-" + composant.toLowerCase() + "/" + "add-" + composant.toLowerCase()
                + ".component.html");
        myWriter.write("<div class=\"login-page\">\r\n"
                + "    <div class=\"login-spacer\"></div>\r\n"
                + "    <div class=\"\">\r\n"
                + "      <h3 class=\"card-heading\">\r\n"
                + "       <strong class=\"foncer\"> {{'" + composant.toLowerCase() + ".register' | translate}}\r\n"
                + "      </strong> </h3>\r\n");

        if (listChampsfile.size() != 0) {
            if (listChampsfile.get(0).getChpType().equals("file")) {
                myWriter.write("<form class=\"\" [formGroup]=\"" + uppercasefunct.capitalize(composant) + "Form\" (ngSubmit)=\"onSubmitFile()\"> \r\n");

            }
        } else {

            myWriter.write("<form class=\"\" [formGroup]=\"" + uppercasefunct.capitalize(composant) + "Form\" (ngSubmit)=\"onSubmit()\"> ");
        }

        for (int k = 0; k < listChamps.size(); k++) {
            List<ValueChamps> valueChamps = iValue.listByChampId(listChamps.get(k).getChpId());
            if (listChamps.get(k).getChpType().equals("autocomplete")) {

                myWriter.write("  <mat-form-field appearance=\"fill\">\r\n"
                        + "             <mat-icon matSuffix>" + listChamps.get(k).getChpIcon() + "</mat-icon>\r\n"
                        + "                  <mat-select  formControlName=\"" + listChamps.get(k).getChpNom() + "\"required>\r\n");
                for (int w = 0; w < valueChamps.size(); w++) {
                    myWriter.write("<mat-option  value=\"" + valueChamps.get(w).getValue() + "\">" + valueChamps.get(w).getLabel() + "</mat-option>\r\n");
                }

                myWriter.write(" </mat-select>\r\n"
                        + "      </mat-form-field>");
            } else if (listChamps.get(k).getChpType().equals("relation")) {

                myWriter.write("  <mat-form-field appearance=\"fill\">\r\n"
                        + "             <mat-icon matSuffix>" + listChamps.get(k).getChpIcon() + "</mat-icon>\r\n"
                        + "                  <mat-select  formControlName=\"" + getMappingObjet(listChamps.get(k).getChpNom()).toString() + "\"required>\r\n"
                        + "<mat-option  *ngFor=\"let item of " + getMappingObjet(listChamps.get(k).getChpNom()).toString() + "\"  [value]=\"item\">{{item." + getMappingChamps(listChamps.get(k).getChpChamps()).toString() + "}}</mat-option>\r\n");

                myWriter.write(" </mat-select>\r\n"
                        + "      </mat-form-field>");
            } else if (listChamps.get(k).getChpType().equals("radio")) {
                myWriter.write("<mat-radio-group  formControlName= \"" + listChamps.get(k).getChpNom() + "\">\n"
                );
                for (int x = 0; x < valueChamps.size(); x++) {
                    myWriter.write("<mat-radio-button  value=\"" + valueChamps.get(x).getValue() + "\">" + valueChamps.get(x).getLabel() + "</mat-radio-button >\r\n");
                }
                myWriter.write(" </mat-radio-group >\r\n");
            } else if (listChamps.get(k).getChpType().equals("checkbox")) {
                myWriter.write("  <mat-form-field appearance=\"fill\">\r\n"
                        + "            <mat-label>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate}}</mat-label>"
                        + "<section  formContolName= \"" + listChamps.get(k).getChpNom() + "\">"
                );
                for (int x = 0; x < valueChamps.size(); x++) {
                    myWriter.write("<mat-checkbox  value=\"" + valueChamps.get(x).getValue() + "\">" + valueChamps.get(x).getLabel() + "</mat-checkbox >\r\n");
                }
                myWriter.write(" </section >\r\n"
                        + "      </mat-form-field>");
            } else if (listChamps.get(k).getChpType().equals("textarea")) {
                myWriter.write(" <mat-form-field appearance=\"fill\">\r\n"
                        + "            <mat-label>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate}}</mat-label>"
                        + "          <textarea matInput  type=\"" + listChamps.get(k).getChpType() + "\"\r\n"
                        + "            formControlName=\"" + listChamps.get(k).getChpNom() + "\" required> </textarea>\r\n"
                        + "        </mat-form-field>\r\n");

            } else if (listChamps.get(k).getChpType().equals("date")) {
                myWriter.write(" <mat-form-field appearance=\"fill\">\r\n"
                        + "<mat-label>{{ '" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate }}</mat-label>\r\n"
                        + "          <input matInput [matDatepicker]=\"picker" + k + "\" \r\n"
                        + "            formControlName=\"" + listChamps.get(k).getChpNom() + "\" required>\r\n"
                        + "<mat-datepicker-toggle matSuffix [for]=\"picker" + k + "\"></mat-datepicker-toggle>\r\n"
                        + "  <mat-datepicker #picker" + k + " startView=\"year\" [startAt]=\"startDate\"></mat-datepicker>\r\n"
                        + "        </mat-form-field>\r\n");

            } else if (listChamps.get(k).getChpType().equals("email")) {
                myWriter.write(" <mat-form-field appearance=\"fill\">\r\n"
                        + "            <mat-label>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate}}</mat-label>\n"
                        + "          <input matInput placeholder=\"{{ '" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate }}\" type=\"" + listChamps.get(k).getChpType() + "\"\r\n"
                        + "            formControlName=\"" + listChamps.get(k).getChpNom() + "\" required pattern=\"[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$\">\r\n"
                        + "        </mat-form-field>\r\n");
            } else if (listChamps.get(k).getChpType().equals("file")) {
                myWriter.write(" <input type=\"file\" formControlName=\"" + listChamps.get(k).getChpNom() + "\"  accept=\"{{accept}}\" (change)=\"uploadImage($event)\" required>\r\n");
            } else {
                myWriter.write(" <mat-form-field appearance=\"fill\">\r\n"
                        + "            <mat-label>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate}}</mat-label>"
                        + "          <input matInput  type=\"" + listChamps.get(k).getChpType() + "\"\r\n"
                        + "            formControlName=\"" + listChamps.get(k).getChpNom() + "\" required >\r\n"
                        + "        </mat-form-field>\r\n");

            }
        }

        myWriter.write(" <div class=\"buttons\">\r\n"
                + "          <div class=\"login\">\r\n"
                + "<button mat-raised-button color=\"warn\" (click)=\"closeDialog()\" type=\"button\">{{'btn_fermer' | translate}}</button>\r\n"
                + "            <button mat-raised-button color=\"primary\" type=\"submit\">{{'btn_valider' | translate}}</button>\r\n"
                + "          </div>\r\n"
                + "        </div>\r\n"
                + "      </form>\r\n"
                + "    </div>\r\n"
                + "  </div>");
        myWriter.close();
    }

    public void updateComponentListHtml(String composant, String nomProjet, List<Champs> listChamps) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/list-" + composant.toLowerCase() + "/" + "list-" + composant.toLowerCase()
                    + ".component.html");
            myWriter.write(" <mat-card class=\"\">\r\n"
                    + "            <mat-card-header class=\"card-header\">\r\n"
                    + "                <span class=\"header\">{{'" + composant.toLowerCase() + ".list' | translate}}</span>\r\n"
                    + "            </mat-card-header>\r\n"
                    + "           <mat-form-field appearance=\"outline\">\r\n"
                    + "        <mat-label>{{'utilisateur.filtre' | translate}}</mat-label>\r\n"
                    + "        <input matInput (keyup)=\"applyFilter($event)\">\r\n"
                    + "      </mat-form-field>\r\n"
                    + "            <button mat-mini-fab color=\"primary\" class=\"add-contact\" (click)=\"openDialogAdd()\"\r\n"
                    + "                matTooltip=\"{{'" + composant.toLowerCase() + ".add' | translate}}\">\r\n"
                    + "                <mat-icon>person_add</mat-icon>\r\n"
                    + "            </button>\r\n"
                    + " <mat-menu #detail=\"matMenu\">\r\n"
                    + "        <form [formGroup]=\"formchamps\">\r\n"
                    + "          <div *ngFor=\"let cd of columnDefinitions\">\r\n"
                    + "            &nbsp; <mat-checkbox [formControlName]=\"cd.def\">{{cd.label}}&nbsp;\r\n"
                    + "            </mat-checkbox>\r\n"
                    + "          </div>\r\n"
                    + "        </form>\r\n"
                    + "      </mat-menu>"
                    + "            <div class=\"\">\r\n"
                    + "<button class=\"float-right\" mat-button [mat-menu-trigger-for]=\"detail\" yPosition=\"above\">\r\n"
                    + "      <mat-icon matTooltip=\"{{'utilisateur.details'| translate}}\" color=\"secondary\">\r\n"
                    + "       filter_alt\r\n"
                    + "      </mat-icon>\r\n"
                    + "    </button>"
                    + "    <table mat-table class=\" mat-elevation-z8\" [dataSource]=\"dataSource\" matSort>\r\n");
            for (int k = 0; k < listChamps.size(); k++) {
                if (listChamps.get(k).getChpType().equals("relation")) {
                    myWriter.write("<ng-container matColumnDef=\"" + getMappingObjet(listChamps.get(k).getChpNom()).toString() + "\">\r\n"
                            + "                        <th mat-header-cell *matHeaderCellDef mat-sort-header>" + getMappingObjet(listChamps.get(k).getChpNom()).toString() + " </th>\r\n"
                            + "                       <td mat-cell *matCellDef=\"let element\"> {{element." + getMappingObjet(listChamps.get(k).getChpNom()).toString() + "." + getMappingChamps(listChamps.get(k).getChpChamps()).toString() + "}} </td>\r\n"
                            + "                    </ng-container>\r\n"
                    );
                } else if (listChamps.get(k).getChpType().equals("file")) {
                    myWriter.write("<ng-container matColumnDef=\"" + listChamps.get(k).getChpNom() + "\">\r\n"
                            + "        <th mat-header-cell *matHeaderCellDef mat-sort-header>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate}}</th>\r\n"
                            + "         <td mat-cell *matCellDef=\"let element\">"
                            + " <mat-icon  color=\"primary\">file_copy\r\n"
                            + "    </mat-icon> </td>\r\n"
                            + "         </ng-container>\r\n");
                } else {
                    myWriter.write("<ng-container matColumnDef=\"" + listChamps.get(k).getChpNom() + "\">\r\n"
                            + "        <th mat-header-cell *matHeaderCellDef mat-sort-header>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate}} </th>\r\n"
                            + "         <td mat-cell *matCellDef=\"let element\"> {{element." + listChamps.get(k).getChpNom() + "}} </td>\r\n"
                            + "         </ng-container>\r\n"
                    );
                }
            }

            myWriter.write(" <ng-container matColumnDef=\"action\">\r\n"
                    + "                <th mat-header-cell *matHeaderCellDef><span style=\"margin-left: 0%;\">\r\n"
                    + "                        {{'register.action' | translate}}</span> </th>\r\n"
                    + "                <td mat-cell *matCellDef=\"let element\" class=\"fab\">\r\n"
                    + "                    <button mat-button [mat-menu-trigger-for]=\"detail\"   yPosition=\"above\">\r\n"
                    + "                        <mat-icon matTooltip=\"{{'utilisateur.details'| translate}}\" color=\"secondary\"\r\n"
                    + "                          >\r\n"
                    + "                            more_vert\r\n"
                    + "                        </mat-icon>\r\n"
                    + "                    </button>\r\n"
                    + "                    <mat-menu #detail=\"matMenu\">\r\n"
                    + "                        <button mat-menu-item (click)=\"openDialogView(element)\">\r\n"
                    + "\r\n"
                    + "                            <mat-icon matTooltip=\"{{'Action.details' | translate}}\" color=\"primary\">visibility\r\n"
                    + "                            </mat-icon>{{'Action.details' | translate}}\r\n"
                    + "                        </button>\r\n"
                    + "                        <button mat-menu-item (click)=\"openDialogUpdate(element)\">\r\n"
                    + "\r\n"
                    + "                            <mat-icon matTooltip=\"{{'Action.edit' | translate}}\" color=\"secondary\">edit</mat-icon>\r\n"
                    + "                            {{'Action.edit' | translate}}\r\n"
                    + "                        </button>\r\n"
                    + "                        <button mat-menu-item (click)=\"openDialogDeleteUser(element)\">\r\n"
                    + "\r\n"
                    + "                            <mat-icon color=\"warn\" matTooltip=\"{{'Action.delete' | translate}}\">delete</mat-icon>\r\n"
                    + "                            {{'Action.delete' | translate}}\r\n"
                    + "                        </button>\r\n");
            for (int z = 0; z < listChamps.size(); z++) {
                if (listChamps.get(z).getChpType().equals("file")) {
                    myWriter.write("<button mat-menu-item (click)=\"downloadImage(element)\">\r\n"
                            + "\r\n"
                            + "                          <mat-icon color=\"warn\" matTooltip=\"{{'Action.dawnload' | translate}}\">dawnload</mat-icon>\r\n"
                            + "                          {{'Action.dawnload' | translate}}\r\n"
                            + "                      </button>\n");
                    break;
                }
            }

            myWriter.write(" </mat-menu>\r\n"
                    + "\r\n"
                    + "                </td>\r\n"
                    + "            </ng-container>\r\n"
                    + "           <tr mat-header-row *matHeaderRowDef=\"getDisplayedColumns()\"></tr>\r\n"
                    + "           <tr mat-row *matRowDef=\"let row; columns: getDisplayedColumns();\"></tr>\r\n"
                    + "        </table>\r\n"
                    + "        <mat-paginator [pageSizeOptions]=\"[5, 10, 20]\" showFirstLastButtons></mat-paginator>\r\n"
                    + "    </div>\r\n"
                    + "</mat-card>\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void updateComponentListCss(String composant, String nomProjet) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/list-" + composant.toLowerCase() + "/" + "list-" + composant.toLowerCase()
                    + ".component.scss");
            myWriter.write("@import \"~bootstrap/scss/bootstrap\";\r\n"
                    + ".position-relative {\r\n"
                    + "	position: relative;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".add-contact {\r\n"
                    + "	position: absolute;\r\n"
                    + "    right: 25px;\r\n"
                    + "    top: 30px;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".t{\r\n"
                    + "  //@extend: 1px solid;\r\n"
                    + "  background-color: rgb(162, 160, 175);\r\n"
                    + " }\r\n"
                    + "\r\n"
                    + "\r\n"
                    + " .petit-card{\r\n"
                    + "   margin: auto;\r\n"
                    + "   border-left: 60px solid;\r\n"
                    + "   border-color: rgb(128, 128, 173);\r\n"
                    + " //  border: 1px solid;\r\n"
                    + " }\r\n"
                    + " \r\n"
                    + "table {\r\n"
                    + "    width: 100%;\r\n"
                    + "   margin-top: 30px;\r\n"
                    + "  }\r\n"
                    + "  .fab{\r\n"
                    + "      width: 12%;\r\n"
                    + "      margin: 0.5%;\r\n"
                    + "  }\r\n"
                    + "  th.mat-sort-header-sorted {\r\n"
                    + "    color: black;\r\n"
                    + "  }\r\n"
                    + "  .mat-form-field {\r\n"
                    + "    font-size: 14px;\r\n"
                    + "    width: 20%;\r\n"
                    + "    margin-top: 30px;\r\n"
                    + "    margin-left: 10px;\r\n"
                    + "\r\n"
                    + "  }\r\n"
                    + " \r\n"
                    + " \r\n"
                    + "  .header{\r\n"
                    + "  margin-top: 10px;\r\n"
                    + "  display: block;\r\n"
                    + "  margin: auto;\r\n"
                    + "  text-align: center;\r\n"
                    + "  }\r\n"
                    + "  .foot{\r\n"
                    + "    background:rgb(90, 90, 158);\r\n"
                    + "    text-align: center;\r\n"
                    + "  }\r\n"
                    + "  .mymargin{\r\n"
                    + "   margin: 7px;\r\n"
                    + "  }\r\n"
                    + "  .container {\r\n"
                    + "    display: grid;\r\n"
                    + "    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));\r\n"
                    + "    grid-gap: 0.5em;\r\n"
                    + "    padding: 0.5em 0.5em;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + "\r\n"
                    + "\r\n"
                    + "h3 {\r\n"
                    + "  font-weight: normal;\r\n"
                    + "  color: #616161;\r\n"
                    + "  font-size: 16px;\r\n"
                    + "  margin: 8px;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".entete{\r\n"
                    + "  width: 100%;\r\n"
                    + "  background: rgb(233, 230, 230);\r\n"
                    + " // margin-top: -5%;\r\n"
                    + "  position: relative;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + "// .number{\r\n"
                    + "\r\n"
                    + "//   text-align: justify;\r\n"
                    + "//   margin: auto;\r\n"
                    + "//  font-family: cursive;\r\n"
                    + "//   font-size:200%;\r\n"
                    + "// }\r\n"
                    + "// .type{\r\n"
                    + "//   margin-right: 4px;\r\n"
                    + "//   text-align: justify;\r\n"
                    + "//   margin: auto; \r\n"
                    + "//   font-family: fantasy;\r\n"
                    + "\r\n"
                    + "// }\r\n"
                    + "\r\n"
                    + "\r\n"
                    + ".card {\r\n"
                    + "  box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.14);\r\n"
                    + "  border: 0;\r\n"
                    + "  margin-bottom: 30px;\r\n"
                    + "  margin-top: 30px;\r\n"
                    + "  border-radius: 6px;\r\n"
                    + "  color: #333333;\r\n"
                    + "  background: #fff;\r\n"
                    + "  width: 100%;\r\n"
                    + "  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.12);\r\n"
                    + "  position: relative;\r\n"
                    + "    display: -webkit-box;\r\n"
                    + "    display: flex;\r\n"
                    + "    -webkit-box-orient: vertical;\r\n"
                    + "    -webkit-box-direction: normal;\r\n"
                    + "    flex-direction: column;\r\n"
                    + "    min-width: 0;\r\n"
                    + "    word-wrap: break-word;\r\n"
                    + "    background-color: #fff;\r\n"
                    + "    background-clip: border-box;\r\n"
                    + "    border: 1px solid #eeeeee;\r\n"
                    + "    border-radius: 0.25rem;\r\n"
                    + "   \r\n"
                    + "\r\n"
                    + "}");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //update html edit

    public void updateComponentEditHtml(String composant, String nomProjet, List<Champs> listChamps, IValue iValue, List<Champs> listChampsfile) throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        FileWriter myWriter = new FileWriter(
                this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/edit-" + composant.toLowerCase() + "/" + "edit-" + composant.toLowerCase()
                + ".component.html");
        myWriter.write("<div class=\"login-page\">\r\n"
                + "    <div class=\"login-spacer\"></div>\r\n"
                + "    <div class=\"\">\r\n"
                + "       <h3 class=\"float-left\">\r\n"
                + "      <strong class=\"foncer\"> {{ '" + composant.toLowerCase() + ".modifier' | translate }} </strong>\r\n"
                + "    </h3>\r\n");
        if (listChampsfile.size() != 0) {
            if (listChampsfile.get(0).getChpType().equals("file")) {
                myWriter.write("<form class=\"\" [formGroup]=\"" + uppercasefunct.capitalize(composant) + "Form\" (ngSubmit)=\"onSubmitFile()\"> \r\n");

            }
        } else {
            myWriter.write("<form class=\"\" [formGroup]=\"" + uppercasefunct.capitalize(composant) + "Form\" (ngSubmit)=\"onSubmit()\"> ");
        }
        for (int k = 0; k < listChamps.size(); k++) {
            List<ValueChamps> valueChamps = iValue.listByChampId(listChamps.get(k).getChpId());
            if (listChamps.get(k).getChpType().equals("autocomplete")) {

                myWriter.write("  <mat-form-field appearance=\"fill\">\r\n"
                        + "             <mat-icon matSuffix>" + listChamps.get(k).getChpIcon() + "</mat-icon>\r\n"
                        + "                  <mat-select placeholder=\"{{ '" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate }}\" formControlName=\"" + listChamps.get(k).getChpNom() + "\"required>\r\n");
                for (int w = 0; w < valueChamps.size(); w++) {
                    myWriter.write("<mat-option  value=\"" + valueChamps.get(w).getValue() + "\">" + valueChamps.get(w).getLabel() + "</mat-option>\r\n");
                }

                myWriter.write(" </mat-select>\r\n"
                        + "      </mat-form-field>");
            } else if (listChamps.get(k).getChpType().equals("relation")) {

                myWriter.write("  <mat-form-field appearance=\"fill\">\r\n"
                        + "             <mat-icon matSuffix>" + listChamps.get(k).getChpIcon() + "</mat-icon>\r\n"
                        + "                  <mat-select placeholder=\"{{ '" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate }}\" formControlName=\"" + getMappingObjet(listChamps.get(k).getChpNom()).toString() + "\"required>\r\n"
                        + "<mat-option  *ngFor=\"let item of " + getMappingObjet(listChamps.get(k).getChpNom().toLowerCase()).toString() + "\"  [value]=\"item\">{{item." + getMappingChamps(listChamps.get(k).getChpChamps()).toString() + "}}</mat-option>\r\n");

                myWriter.write(" </mat-select>\r\n"
                        + "      </mat-form-field>");
            } else if (listChamps.get(k).getChpType().equals("radio")) {

                myWriter.write("  <mat-form-field appearance=\"fill\">\r\n"
                        + "         <mat-icon matSuffix>" + listChamps.get(k).getChpIcon() + "</mat-icon>\r\n"
                        + "<mat-radio-group  formContolName= \"" + listChamps.get(k).getChpNom() + "\">"
                );
                for (int x = 0; x < valueChamps.size(); x++) {
                    myWriter.write("<mat-radio-button  value=\"" + valueChamps.get(x).getValue() + "\">" + valueChamps.get(x).getLabel() + "</mat-radio-button >\r\n");
                }
                myWriter.write(" </mat-radio-group >\r\n"
                        + "      </mat-form-field>");
            } else if (listChamps.get(k).getChpType().equals("textarea")) {
                myWriter.write(" <mat-form-field appearance=\"fill\">\r\n"
                        + "          <mat-icon matSuffix>" + listChamps.get(k).getChpIcon() + "</mat-icon>\r\n"
                        + "          <textarea matInput placeholder=\"{{ '" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate }}\" type=\"" + listChamps.get(k).getChpType() + "\"\r\n"
                        + "            formControlName=\"" + listChamps.get(k).getChpNom() + "\" required> </textarea>\r\n"
                        + "        </mat-form-field>\r\n");

            } else if (listChamps.get(k).getChpType().equals("date")) {
                myWriter.write(" <mat-form-field appearance=\"fill\">\r\n"
                        + "<mat-label>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate}}</mat-label>\r\n"
                        + "          <input matInput [matDatepicker]=\"picker\" \r\n"
                        + "            formControlName=\"" + listChamps.get(k).getChpNom() + "\" required>\r\n"
                        + "<mat-datepicker-toggle matSuffix [for]=\"picker\"></mat-datepicker-toggle>\r\n"
                        + "  <mat-datepicker #picker startView=\"year\" [startAt]=\"startDate\"></mat-datepicker>\r\n"
                        + "        </mat-form-field>\r\n");

            } else if (listChamps.get(k).getChpType().equals("number")) {
                myWriter.write(" <mat-form-field>\r\n"
                        + "          <mat-icon matSuffix>" + listChamps.get(k).getChpIcon() + "</mat-icon>\r\n"
                        + "          <input matInput placeholder=\"{{ '" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate }}\" type=\"" + listChamps.get(k).getChpType() + "\"\r\n"
                        + "            formControlName=\"" + listChamps.get(k).getChpNom() + "\" required>\r\n"
                        + "        </mat-form-field>\r\n");
            } else if (listChamps.get(k).getChpType().equals("file")) {
                myWriter.write(" <input type=\"file\" formControlName=\"file\"  accept=\"{{accept}}\" (change)=\"uploadImage($event)\" required>\r\n");
            } else {
                myWriter.write(" <mat-form-field>\r\n"
                        + "          <mat-icon matSuffix>" + listChamps.get(k).getChpIcon() + "</mat-icon>\r\n"
                        + "          <input matInput placeholder=\"{{ '" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom() + "' | translate }}\" type=\"" + listChamps.get(k).getChpType() + "\"\r\n"
                        + "            formControlName=\"" + listChamps.get(k).getChpNom() + "\" required>\r\n"
                        + "        </mat-form-field>\r\n");
            }

        }
        myWriter.write(" <div class=\"buttons\">\r\n"
                + "          <div class=\"login\">\r\n"
                + "            <button mat-raised-button color=\"primary\" type=\"submit\">{{'btn_valider' | translate}}</button>\r\n"
                + "            <button mat-raised-button color=\"warn\" (click)=\"closeDialog()\" type=\"button\"\r\n"
                + "             >{{'btn_fermer' | translate}}</button>\r\n"
                + "          </div>\r\n"
                + "        </div>\r\n"
                + "      </form>\r\n"
                + "    </div>\r\n"
                + "  </div>");
        myWriter.close();
    }

    public void updateComponentEditTs(String composant, String nomProjet, List<Champs> listChamps, List<Champs> listChampsfile) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/edit-" + composant.toLowerCase() + "/" + "edit-" + composant.toLowerCase()
                    + ".component.ts");
            myWriter.write("import { " + uppercasefunct.capitalize(composant) + "Service } from '../../service/" + composant.toLowerCase() + ".service';\r\n"
                    + "import { " + uppercasefunct.capitalize(composant) + " } from '../../model/" + composant.toLowerCase() + "';\r\n"
                    + "import { Component, OnInit,Inject,Input } from '@angular/core';\r\n"
                    + "import { Router } from '@angular/router';\r\n"
                    + "import { FormBuilder, Validators } from '@angular/forms';\r\n"
                    + "import { MatDialogRef, MatSnackBar,MAT_DIALOG_DATA } from '@angular/material';"
                    + "\r\n"
                    + "import { NotificationService } from '../../../shared/services/notification.service';\r\n"
                    + "import { TranslateService } from '@ngx-translate/core';\n"
                    + "@Component({\r\n"
                    + "  selector: 'app-edit-" + composant.toLowerCase() + "',\r\n"
                    + "  templateUrl: './edit-" + composant.toLowerCase() + ".component.html',\r\n"
                    + "  styleUrls: ['./edit-" + composant.toLowerCase() + ".component.scss']\r\n"
                    + "})\r\n"
                    + "export class Edit" + uppercasefunct.capitalize(composant) + "Component implements OnInit {\r\n"
                    + "result:any\n"
                    + " @Input() accept = '.';\n"
                    + "isFileValid: boolean;\r\n"
                    + "  images;\r\n"
                    + "  href:any\r\n"
                    + "  base64Data: any;\r\n"
                    + "  based\r\n"
                    + "  isUpload: boolean \n"
                    + " public " + composant.toLowerCase() + ": Array<File>;"
                    + "\r\n");
            myWriter.write("" + uppercasefunct.capitalize(composant) + "Form = this.formbuild.group({\r\n"
                    + "id :['', Validators.required],\r\n");
            for (int i = 0; i < listChamps.size(); i++) {
                if (listChamps.get(i).getChpType().equals("relation")) {
                    myWriter.write(getMappingObjet(listChamps.get(i).getChpNom()).toString() + " :['', Validators.required],\r\n");
                } else {
                    myWriter.write(listChamps.get(i).getChpNom() + " :['', Validators.required],\r\n");
                }
            }
            myWriter.write(" });");
            myWriter.write("  constructor(private " + composant.toLowerCase() + "Service: " + uppercasefunct.capitalize(composant) + "Service,\r\n"
                    + "    private router: Router,"
                    + " private formbuild: FormBuilder, \r\n"
                    + "    private _snackBar: MatSnackBar,\r\n"
                    + "private notification: NotificationService,\r\n"
                    + "private translate:TranslateService,\n"
                    + "    public dialogRef: MatDialogRef<Edit" + uppercasefunct.capitalize(composant) + "Component>,@Inject(MAT_DIALOG_DATA) public donnee: any) { }\r\n"
                    + "\r\n"
                    + "  ngOnInit() {\r\n"
                    + "this.initView();\r\n"
                    + "  }\r\n"
                    + "initView() {   \r\n"
                    + "    this." + uppercasefunct.capitalize(composant) + "Form.setValue({ \r\n"
                    + "     id: this.donnee.id,\r\n");
            for (int j = 0; j < listChamps.size(); j++) {
                if (listChamps.get(j).getChpType().equals("relation")) {

                    myWriter.write(getMappingObjet(listChamps.get(j).getChpNom()).toString() + ":this.donnee." + getMappingObjet(listChamps.get(j).getChpNom()).toString() + ",\r\n");
                } else {
                    myWriter.write(listChamps.get(j).getChpNom() + ":this.donnee." + listChamps.get(j).getChpNom() + ",\r\n");
                }

            }

            myWriter.write(" });}\n");
            if (listChampsfile.size() != 0) {
                if (listChampsfile.get(0).getChpType().equals("file")) {
                    myWriter.write(" onSubmitFile() {\r\n"
                            + "    if (this." + uppercasefunct.capitalize(composant) + "Form.value) {\r\n"
                            + "      this." + composant.toLowerCase() + "Service.add" + uppercasefunct.capitalize(composant) + "(this." + composant.toLowerCase() + "[0],this." + uppercasefunct.capitalize(composant) + "Form.value).subscribe(data => {\r\n"
                            + "        if (data.statut) {\r\n"
                            + "          let ReportSaveSuccess;\r\n"
                            + "          this.translate.get('" + composant.toLowerCase() + ".confirmEnr').subscribe((res: string) => {\r\n"
                            + "            ReportSaveSuccess = res;\r\n"
                            + "          });\r\n"
                            + "          this.translate.get(ReportSaveSuccess).subscribe((res: string) => {\r\n"
                            + "            this.notification.success(res);\r\n"
                            + "          });\r\n"
                            + "\r\n"
                            + "         this." + uppercasefunct.capitalize(composant) + "Form.reset();\r\n"
                            + "          this.closeDialog();\r\n"
                            + "        }\r\n"
                            + "      }, error => {\r\n"
                            + "        let ReportSaveError;\r\n"
                            + "        this.translate.get('" + composant.toLowerCase() + ".erreurEnr').subscribe((res: string) => {\r\n"
                            + "          ReportSaveError = res;\r\n"
                            + "        });\r\n"
                            + "        this.translate.get(ReportSaveError).subscribe((res: string) => {\r\n"
                            + "          this.notification.error(res);\r\n"
                            + "        });\r\n"
                            + "      });\r\n"
                            + "    } else {\r\n"
                            + "      let errorChamps;\r\n"
                            + "      let form;\r\n"
                            + "      this.translate.get('control.error').subscribe((res: string) => {\r\n"
                            + "        form = res;\r\n"
                            + "      });\r\n"
                            + "\r\n"
                            + "      this.translate.get('control.required').subscribe((res: string) => {\r\n"
                            + "        errorChamps = res;\r\n"
                            + "      });\r\n"
                            + "      this.translate.get(errorChamps).subscribe((res: string) => {\r\n"
                            + "        this.notification.error(res);\r\n"
                            + "      });\r\n"
                            + " \r\n"
                            + "    }\r\n"
                            + "  }\n");

                    myWriter.write("  uploadImage(event: any) {\r\n"
                            + "    if (event.target.files[0]) {\r\n"
                            + "       const extension = event.target.files[0].name.split('.')[1].toLowerCase();\r\n"
                            + "       if ( \"png\" === extension ||\"jpeg\"===extension || \"jpg\"===extension || \"gif\"===extension ) {\r\n"
                            + "         this.isFileValid = true;\r\n"
                            + "       }\r\n"
                            + "      this." + composant.toLowerCase() + " = event.target.files;\r\n"
                            + "      if (this." + composant.toLowerCase() + "[0].size >3000000){\r\n"
                            + "        this.translate.get(\"Verifier la taille du document!!\").subscribe((res: string) => {\r\n"
                            + "          this.notification.warn(res);\r\n"
                            + "        });\r\n"
                            + "        return;\r\n"
                            + "\r\n"
                            + "      }\r\n"
                            + "      this.isUpload = true;\r\n"
                            + "    }\r\n"
                            + "  }\r\n");

                } else {
                    myWriter.write("\r\n"
                            + "   onSubmit() {\r\n"
                            + "if(" + conditionValueSubmitFalse("this." + uppercasefunct.capitalize(composant) + "Form", listChampsfile) + "){\r\n"
                            + "this.translate.get('error.champsrequired').subscribe((res: string)=>{\r\n"
                            + "this.notification.warn(res);\r\n"
                            + "return;"
                            + "});"
                            + "    this." + composant.toLowerCase() + "Service.create" + uppercasefunct.capitalize(composant) + "(this." + uppercasefunct.capitalize(composant) + "Form.value).subscribe(data => {\r\n"
                            + "  \r\n"
                            + "  this.result=data \n\r"
                            + "      if (this.result.statut) {\r\n"
                            + "         this.notification.info(this.result.description);\r\n"
                            + "\r\n"
                            + "        this." + uppercasefunct.capitalize(composant) + "Form.reset();\r\n"
                            + "        this.closeDialog();\r\n"
                            + "      }\r\n"
                            + "    }, error => {\r\n"
                            + "       this.notification.error('" + uppercasefunct.capitalize(composant) + " invalide');\r\n"
                            + "    });\r\n"
                            + "  }\r\n"
                            + "\r\n"
                            + " }\r\n");
                }
            }

            myWriter.write("  closeDialog() {\r\n"
                    + "    this.dialogRef.close();\r\n"
                    + "  }\r\n"
                    + "}");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void updateComponentEditCss(String composant, String nomProjet) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/edit-"
                    + composant.toLowerCase() + "/" + "edit-" + composant.toLowerCase() + ".component.scss");
            myWriter.write(".image{\r\n" + "  background: url(\"/assets/images/gainde/ia.jpg\");\r\n" + "}\r\n" + "\r\n"
                    + "#test {\r\n" + "  background-image: url();\r\n" + "  background-size: cover;\r\n"
                    + "  /* background: url(http://www.petite-annonce-gratuite.com/layout_images/carte-france.png) no-repeat;\r\n"
                    + "  width: 100%;\r\n" + "  height: 390px;*/\r\n" + "}\r\n" + "\r\n" + "mat-grid-tile {\r\n"
                    + "  background: rgb(238, 239, 240);\r\n" + "}\r\n" + "\r\n"
                    + "@media screen and (min-width: 100%) and (max-width: 100%) {\r\n" + "  #test {\r\n"
                    + "      width: 100%;\r\n" + "      background-size: cover;\r\n" + "  }\r\n" + "\r\n" + "}\r\n"
                    + "body{\r\n" + "  background-image: url(\"/assets/images/gainde/ia1.jpg\");\r\n"
                    + "  background-size: cover;\r\n" + "\r\n" + "}\r\n" + ".right{\r\n" + "  margin-left: 20%;\r\n"
                    + "}\r\n" + ".top{\r\n" + "  margin-top: 11%;\r\n" + "}\r\n" + ".text{\r\n"
                    + "  font-size: 11px;\r\n" + "}\r\n" + "\r\n" + ".center{\r\n" + "  margin-bottom: 2%;\r\n"
                    + "  display: block;\r\n" + "  margin:auto;\r\n" + "}\r\n" + "\r\n" + ".login-page{\r\n"
                    + "  height: 90vh;\r\n" + "//  width: 100%;\r\n" + "  margin-top: -10%;\r\n"
                    + " overflow-wrap: scroll;" + "\r\n" + "}\r\n" + "\r\n" + ".login-spacer{\r\n"
                    + "  height: 12vh;\r\n" + "}\r\n" + "\r\n" + ".login-container{\r\n" + "  margin: auto;\r\n"
                    + "  width: 300px;\r\n" + "  padding: 1vh;\r\n" + "  background-color: #fff;\r\n" + "}\r\n" + "\r\n"
                    + ".login-form{\r\n" + "  display: flex;\r\n" + "  flex-direction: column;\r\n" + "}\r\n" + "\r\n"
                    + "#login-fab {\r\n" + "border-radius: 50%;\r\n" + "  height: 56px;\r\n" + "  margin: auto;\r\n"
                    + "  min-width: 56px;\r\n" + "  width: 56px;\r\n" + "  overflow: hidden;\r\n"
                    + "  background: #1e88e5;\r\n"
                    + "  box-shadow: 0 1px 1.5px 0 rgba(0,0,0,.12), 0 1px 1px 0 rgba(0,0,0,.24);\r\n"
                    + "  margin-top: -35px;\r\n" + "  text-align: center;\r\n" + "  left: 0;\r\n" + "  right: 0;\r\n"
                    + "}\r\n" + "\r\n" + "#lock-icon{\r\n" + "  line-height: 56px;\r\n" + "  color: #fff;\r\n" + "}\r\n"
                    + ".card-heading{\r\n" + "  text-align: center;\r\n" + "  color: rgba(0, 0, 0, 0.31);\r\n" + "}\r\n"
                    + "\r\n" + ".buttons .login{\r\n" + "  padding-top: 2vh;\r\n" + "  padding-bottom: 2vh;\r\n"
                    + "}\r\n" + "\r\n" + ".buttons .login > button{\r\n" + "  width: 40%;\r\n" + "}\r\n" + "button{\r\n"
                    + "  margin: 5%;\r\n" + "}\r\n" + ".buttons .register{\r\n" + "  display: flex;\r\n"
                    + "  padding-top: 4vh;\r\n" + "  padding-bottom: 2vh;\r\n" + "  justify-content: space-between;\r\n"
                    + "}\r\n" + ".app-header{\r\n" + "  color: #fff;\r\n" + "      background-color: #1e88e5;\r\n"
                    + "      text-align: center;\r\n" + "      margin-top: -3px;\r\n" + "      padding: 2px;\r\n"
                    + "  -webkit-box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n"
                    + "  -moz-box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n"
                    + "  box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n" + "  }\r\n" + "");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void updateComponentViewTs(String composant, String nomProjet, List<Champs> listChamps) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/view-"
                    + composant.toLowerCase() + "/" + "view-" + composant.toLowerCase() + ".component.ts");
            myWriter.write("import { " + uppercasefunct.capitalize(composant) + "Service } from '../../service/"
                    + composant.toLowerCase() + ".service';\r\n" + "import { " + uppercasefunct.capitalize(composant)
                    + " } from '../../model/" + composant.toLowerCase() + "';\r\n"
                    + "import { Component, OnInit,Inject } from '@angular/core';\r\n"
                    + "import { Router } from '@angular/router';\r\n"
                    + "import { FormBuilder, Validators } from '@angular/forms';\r\n"
                    + "import { MatDialogRef, MatSnackBar,MAT_DIALOG_DATA } from '@angular/material';" + "\r\n"
                    + "@Component({\r\n" + "  selector: 'app-view-" + composant.toLowerCase() + "',\r\n"
                    + "  templateUrl: './view-" + composant.toLowerCase() + ".component.html',\r\n"
                    + "  styleUrls: ['./view-" + composant.toLowerCase() + ".component.scss']\r\n" + "})\r\n"
                    + "export class View" + uppercasefunct.capitalize(composant) + "Component implements OnInit {\r\n"
                    + "result:any;" + "\r\n");

            myWriter.write("  constructor(private " + composant.toLowerCase() + "Service: "
                    + uppercasefunct.capitalize(composant) + "Service,\r\n" + "    private router: Router,"
                    + " private formbuild: FormBuilder, \r\n" + "    private _snackBar: MatSnackBar,\r\n"
                    + "    public dialogRef: MatDialogRef<View" + uppercasefunct.capitalize(composant)
                    + "Component>,@Inject(MAT_DIALOG_DATA) public donnee: any) { }\r\n" + "\r\n" + "  ngOnInit() {\r\n"
                    + "this.detail();\r\n" + "  }\r\n" + "detail() {   \r\n" + "this.result = this.donnee;"
                    + "  }" + "\r\n" + "  closeDialog() {\r\n" + "    this.dialogRef.close();\r\n" + "  }\r\n" + "}");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void updateComponentViewCss(String composant, String nomProjet) {
        String rep = this.repo;
        try {

            StringProcess uppercasefunct = new StringProcess();
            FileWriter myWriter = new FileWriter(
                    this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/view-"
                    + composant.toLowerCase() + "/" + "view-" + composant.toLowerCase() + ".component.scss");
            myWriter.write(".image{\r\n" + "    background: url(\"/assets/images/gainde/ia.jpg\");\r\n" + "}\r\n"
                    + "\r\n" + "#test {\r\n" + "    background-image: url();\r\n" + "    background-size: cover;\r\n"
                    + "    /* background: url(http://www.petite-annonce-gratuite.com/layout_images/carte-france.png) no-repeat; \r\n"
                    + "    width: 100%;\r\n" + "    height: 390px;*/\r\n" + "}\r\n" + "\r\n" + "mat-grid-tile {\r\n"
                    + "    background: rgb(238, 239, 240);\r\n" + "  }\r\n" + "\r\n"
                    + "@media screen and (min-width: 100%) and (max-width: 100%) {\r\n" + "    #test {\r\n"
                    + "        width: 100%;\r\n" + "        background-size: cover;\r\n" + "    }\r\n" + "    \r\n"
                    + "}\r\n" + "body{\r\n" + "    background-image: url(\"/assets/images/gainde/ia1.jpg\");\r\n"
                    + "    background-size: cover;\r\n" + "\r\n" + "}\r\n" + ".right{\r\n" + "    margin-left: 20%;\r\n"
                    + "}\r\n" + ".top{\r\n" + "    margin-top: 11%;\r\n" + "}\r\n" + ".text{\r\n"
                    + "    font-size: 11px;\r\n" + "}\r\n" + "\r\n" + ".center{\r\n" + "    margin-bottom: 2%;\r\n"
                    + "    display: block;\r\n" + "    margin:auto;\r\n" + "}\r\n" + "\r\n" + ".login-page{\r\n"
                    + "   // height: 90vh;\r\n" + "  //  width: 100%;\r\n" + "    margin-top: -10%;\r\n" + "\r\n"
                    + "}\r\n" + "\r\n" + ".login-spacer{\r\n" + "    height: 12vh;\r\n" + "}\r\n" + "\r\n"
                    + ".login-container{\r\n" + "    margin: auto;\r\n" + "    width: 300px;\r\n"
                    + "    padding: 1vh;\r\n" + "    background-color: #fff;\r\n" + "}\r\n" + "\r\n"
                    + ".login-form{\r\n" + "    display: flex;\r\n" + "    flex-direction: column;\r\n" + "}\r\n"
                    + "\r\n" + "#login-fab {\r\n" + "	border-radius: 50%;\r\n" + "    height: 56px;\r\n"
                    + "    margin: auto;\r\n" + "    min-width: 56px;\r\n" + "    width: 56px;\r\n"
                    + "    overflow: hidden;\r\n" + "    background: #1e88e5;\r\n"
                    + "    box-shadow: 0 1px 1.5px 0 rgba(0,0,0,.12), 0 1px 1px 0 rgba(0,0,0,.24);\r\n"
                    + "    margin-top: -35px;\r\n" + "    text-align: center;\r\n" + "    left: 0;\r\n"
                    + "    right: 0;\r\n" + "}\r\n" + "\r\n" + "#lock-icon{\r\n" + "    line-height: 56px;\r\n"
                    + "    color: #fff;\r\n" + "}\r\n" + ".card-heading{\r\n" + "    text-align: center;\r\n"
                    + "    color: rgba(0, 0, 0, 0.31);\r\n" + "}\r\n" + "\r\n" + ".buttons .login{\r\n"
                    + "    padding-top: 2vh;\r\n" + "    padding-bottom: 2vh;\r\n" + "}\r\n" + "\r\n"
                    + ".buttons .login > button{\r\n" + "    width: 40%;\r\n" + "}\r\n" + "button{\r\n"
                    + "    margin: 5%;\r\n" + "}\r\n" + ".buttons .register{\r\n" + "    display: flex;\r\n"
                    + "    padding-top: 4vh;\r\n" + "    padding-bottom: 2vh;\r\n"
                    + "    justify-content: space-between;\r\n" + "}\r\n" + ".app-header{\r\n" + "    color: #fff;\r\n"
                    + "        background-color: #1e88e5;\r\n" + "        text-align: center;\r\n"
                    + "        margin-top: -3px;\r\n" + "        padding: 2px;\r\n"
                    + "    -webkit-box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n"
                    + "    -moz-box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n"
                    + "    box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n" + "    }\r\n" + "");

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void updateComponentViewHtml(String composant, String nomProjet, List<Champs> listChamps)
            throws IOException {
        StringProcess uppercasefunct = new StringProcess();
        FileWriter myWriter = new FileWriter(
                this.repo + "/" + nomProjet + "/src/app/" + composant.toLowerCase() + "/components/view-"
                + composant.toLowerCase() + "/" + "view-" + composant.toLowerCase() + ".component.html");
        myWriter.write(" <div class=\"scroll\">\n" + " <mat-card-header>\r\n"
                + "    <mat-card-subtitle>Detail</mat-card-subtitle>\r\n" + "\r\n" + "  </mat-card-header>\r\n "
                + "<mat-card-content>\r\n" + "    <div class=\"row\">\r\n"
                + "      <div class=\"col-sm-6 col-lg-6 col-md-6\">");
        for (int k = 0; k < listChamps.size(); k++) {
            if (listChamps.get(k).getChpType().equals("relation")) {
                myWriter.write("<mat-label>" + getMappingObjet(listChamps.get(k).getChpNom()).toString() + ": {{result."
                        + getMappingObjet(listChamps.get(k).getChpNom()).toString() + "."
                        + getMappingChamps(listChamps.get(k).getChpChamps()).toString() + "}}</mat-label><br>\r\n"
                        + "        <hr>");
            } else if (listChamps.get(k).getChpType().equals("file")) {
                myWriter.write("<mat-label><strong>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom()
                        + "' | translate}}</strong>:<img src=\"data:image/png;base64,{{result."
                        + listChamps.get(k).getChpNom() + "}}\" width=\"50\"></mat-label><br>\r\n" + "        <hr>");
            } else {
                myWriter.write("<mat-label><strong>{{'" + composant.toLowerCase() + "." + listChamps.get(k).getChpNom()
                        + "' | translate}}</strong>: {{result." + listChamps.get(k).getChpNom()
                        + "}}</mat-label><br>\r\n" + "        <hr>");
            }

        }
        myWriter.write(" </div>\r\n" + "    </div>\r\n" + "  </mat-card-content>\r\n" + "  <mat-card-actions>\r\n"
                + "    <button mat-button color=\"primary\" (click)=\"closeDialog()\">{{'btn_fermer' | translate}}</button>\r\n"
                + "\r\n" + "  </mat-card-actions>\n" + "</div>");
        myWriter.close();
    }

    // update service
    public void updateService(String taskName, String nomProjet, List<Champs> listChamps) {
        // String rep = this.repo ;
        StringProcess uppercasefunct = new StringProcess();
        try {

            FileWriter myWriter = new FileWriter(this.repo + "/" + nomProjet + "/src/app/" + taskName.toLowerCase()
                    + "/service/" + taskName.toLowerCase() + ".service.ts");
            myWriter.write("import { Injectable } from '@angular/core';\r\n"
                    + "import { HttpClient,HttpHeaders} from '@angular/common/http';\r\n"
                    + "import { environment } from 'src/environments/environment';\r\n" + "import { "
                    + uppercasefunct.capitalize(taskName) + " } from '../model/" + taskName.toLowerCase() + "';"
                    + "@Injectable({\r\n" + "  providedIn: 'root'\r\n" + "})\r\n" + "export class "
                    + uppercasefunct.capitalize(taskName) + "Service {\r\n" + "api = environment.apii;" + "\r\n"
                    + "  constructor(private http:HttpClient) {" + "  }\r\n" + "  \r\n" + "  create"
                    + uppercasefunct.capitalize(taskName) + "(data){\r\n" + "       return this.http.post(this.api+\""
                    + taskName.toLowerCase() + "/create\",data)\r\n" + "  }\r\n" + "  get"
                    + uppercasefunct.capitalize(taskName) + "All(){\r\n" + "        return this.http.get(this.api+\""
                    + taskName.toLowerCase() + "/list\")\r\n" + "  } \r\n" + "  delete"
                    + uppercasefunct.capitalize(taskName) + "(data){\r\n" + "    return this.http.post(this.api+\""
                    + taskName.toLowerCase() + "/delete\",data)\r\n" + "}"
                    + "\n");
            int c = 0;
            for (int i = 0; i < listChamps.size(); i++) {
                if (listChamps.get(i).getChpType().equals("relation")) {
                    myWriter.write("  get"
                            + uppercasefunct.capitalize(getMappingObjet(listChamps.get(i).getChpNom()).toString())
                            + "(){\r\n" + "        return this.http.get(this.api+\""
                            + getMappingObjet(listChamps.get(i).getChpNom()).toString() + "/list\")\r\n" + "}\r\n");
                }
                if (listChamps.get(i).getChpType().equals("file") && c == 0) {
                    myWriter.write(" \r\n" + "  add" + uppercasefunct.capitalize(taskName) + "(file:any, "
                            + taskName.toLowerCase() + ":" + uppercasefunct.capitalize(taskName) + "){\r\n"
                            + "    const formData = new FormData();\r\n" + "    formData.append('"
                            + taskName.toLowerCase() + "', file);\r\n" + "    formData.append('"
                            + taskName.toLowerCase() + "', JSON.stringify(" + taskName.toLowerCase() + "));\r\n"
                            + "    return this.http.post<any>(this.api + '" + taskName.toLowerCase()
                            + "/createfile', formData);\r\n" + "  }\r\n" + " download(id) {\r\n"
                            + "    let headers = new HttpHeaders();\r\n"
                            + "    //headers = headers.append('Accept', 'image/png');\r\n"
                            + "    return this.http.get(this.api + '" + taskName.toLowerCase()
                            + "/downloadFile/'+id,\r\n" + "    {\r\n" + "      headers: headers,\r\n"
                            + "      observe: 'response',\r\n" + "      responseType:'arraybuffer'});\r\n" + "  }");
                    c++;
                }

            }
            myWriter.write("}\r\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // update model
    public void updateModel(String taskName, String nomProjet, List<Champs> listChamps) {
        StringProcess uppercasefunct = new StringProcess();
        String rep = this.repo;
        try {

            FileWriter myWriter = new FileWriter(this.repo + "/" + nomProjet + "/src/app/" + taskName.toLowerCase()
                    + "/model/" + taskName.toLowerCase() + ".ts");
            myWriter.write("export class " + uppercasefunct.capitalize(taskName) + " {");
            for (int k = 0; k < listChamps.size(); k++) {
                if (listChamps.get(k).getChpType().equals("relation")) {
                    myWriter.write(getMappingObjet(listChamps.get(k).getChpNom()).toString() + ":any;");
                } else {
                    myWriter.write(listChamps.get(k).getChpNom() + ":any;");
                }

            }
            myWriter.write("}");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // fichier de configuration des donnees statiques
    public void initfichierstatique() throws IOException {
        File packentity = new File(this.repo + "/configapp");
        packentity.mkdirs();
        File controllerfiles = new File(packentity, "configdatastatique.sql");
        controllerfiles.createNewFile();
        FileWriter myWriter = new FileWriter(this.repo + "/configapp/configdatastatique.sql");
        myWriter.write(
                "INSERT INTO `tp_profil` (`pro_id`, `pro_description`, `pro_libelle`) VALUES(1, 'Admin applicatif', 'administrateur fontionnel');\r\n");
        myWriter.write(
                "INSERT INTO `td_utilisateur` (`uti_id`, `uti_adresse`, `uti_app_id`, `uti_date_creation`, `uti_date_modification`, `uti_email`, `uti_nom`, `uti_password`, `uti_prenom`, `uti_telephone`, `uti_username`, `uti_actived`, `uti_premier_connect`, `uti_pro_id`, `uti_couleur`, `uti_lng_id`, `uti_thm_id`) VALUES(1, 'admin',NULL, NULL, NULL, 'admin@gainde2000', '', '$2a$10$q/kuau7WLiaYidFNF3QtEO5o860/RKJPayVyNV5qv6RNMvNNvc7ZK', 'admin', '777777777', 'admin', 1, 0, 1, NULL, NULL, NULL);\r\n");
        myWriter.write(
                "INSERT INTO `tp_menu` (`men_id`, `men_icone`, `men_icone_color`, `men_id_parent`, `men_nom`, `men_path`) VALUES ('1', 'supervisor_account', 'primary', NULL, 'utilisateur', '/utilisateur');\r\n");
        myWriter.write(
                "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('1', 'create_profil', 'Créer un profil', '1');\r\n"
                + "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('2', 'edit_profil', 'Modifier un profil', '1');\r\n"
                + "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('3', 'delete_profil', 'Supprimer un profil', '1');\r\n"
                + "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('4', 'create_user', 'Créer un utilisateur', '1');\r\n"
                + "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('5', 'edit_user', 'Modifier un utilisateur', '1');\r\n"
                + "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('6', 'delete_user', 'Supprimer un utilisateur', '1');\r\n"
                + "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('7', 'enable_user', 'Activer/desactiver un utilisateur', '1');\r\n"
                + "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('8', 'set_acces', 'Définir les droits d\\'acces', '1');\r\n"
                + "INSERT INTO `tp_action` (`act_id`, `act_code`, `act_nom`, `act_men_id`) VALUES ('9', 'edit_acces', 'Modifier les droits d\\'acces', '1');\r\n");
        myWriter.write("INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (1,1, 1);\r\n"
                + "INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (2,1, 2);\r\n"
                + "INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (3,1, 3);\r\n"
                + "INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (4,1, 4);\r\n"
                + "INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (5,1, 5);\r\n"
                + "INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (6,1, 6);\r\n"
                + "INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (7,1, 7);\r\n"
                + "INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (8,1, 8);\r\n"
                + "INSERT INTO `tr_privilege` (`pri_id`,`pri_pro_id`, `pri_act_id`) VALUES (9,1, 9);");
        myWriter.close();
        System.out.println("Ficher de configuration des donnees statique crees");
    }

    // configuration application
    public void initialiserApp(Application app, List<Theme> listeTheme, List<Workflow> listeWorkflow,
            List<FormulaireV2> listeFormulaire, List<Widget> listeWidget, List<Langue> listeLangue,
            List<PwdCriteria> listePwdCritaire, List<Rapport> listeRapport, List<Parametre> listeParametre,
            List<WorkflowSecteur> listWorkflowSecteur, List<ChampsRapport> listChampsRapport,
            List<Module> listModule, List<Task> listTask,List<TaskStatus> listTaskStatus,
            List<Transition> listTransition,List<RegleGestion> regleGestion,List<TimerTask> timerTask) throws IOException {

        File packentity = new File(this.repo + "/configapp");
        packentity.mkdirs();
        File controllerfiles = new File(packentity, "configdatadynamique.sql");
        controllerfiles.createNewFile();

        // List<Theme> listeTheme = iThemeService.getListTheme();
        try {

            FileWriter myWriter = new FileWriter(this.repo + "/configapp/configdatadynamique.sql");
            // recuperation de L'application
            myWriter.write(
                    "INSERT INTO `td_application` (`app_id`, `app_adresse`, `app_date_creation`, `app_email`, `app_ninea`, `app_nom`, `app_nom_entreprise`, `app_secteur`, `app_telephone_fixe`, `app_telephone_mobile`, `parametre`, `app_etape_creation`) VALUES\r\n"
                    + "	(" + app.getAppId() + ", '" + app.getAppAdresse() + "', NULL, '" + app.getAppEmail()
                    + "', '" + app.getAppNinea() + "', '" + app.getAppNomEntreprise() + "', '"
                    + app.getAppSecteur() + "', '" + app.getAppTelephoneFixe() + "', '"
                    + app.getAppTelephoneMobile() + "', NULL, NULL, NULL);\r\n" + "\r\n");

            // liste des widgets
            for (int a = 0; a < listeWidget.size(); a++) {
                myWriter.write(
                        "INSERT INTO `tp_widget` (`wdg_id`, `wdg_code`, `wdg_description`, `wdg_nom`, `wdg_ordre`, `wdg_longueur`, `wdg_type`) VALUES("
                        + listeWidget.get(a).getWdgId() + ", '" + listeWidget.get(a).getWdgCode() + "', '"
                        + listeWidget.get(a).getWdgDescription() + "','" + listeWidget.get(a).getWdgNom()
                        + "','" + listeWidget.get(a).getWdgOrdre() + "','" + listeWidget.get(a).getWdgLongueur()
                        + "','" + listeWidget.get(a).getWdgType() + "');\r\n");

            }
            // liste des langues
            for (int b = 0; b < listeLangue.size(); b++) {
                myWriter.write(
                        "INSERT INTO `tp_langue` (`lng_id`, `lng_code`, `lng_disposition_text`, `lng_langue`)VALUES("
                        + listeLangue.get(b).getLngId() + ", '" + listeLangue.get(b).getLngCode() + "', '"
                        + listeLangue.get(b).getLngDispositionText() + "','" + listeLangue.get(b).getLngLangue()
                        + "');\r\n");

            }

            // parametre par defaut
            /*
			 * for (int e = 0; e < listeParametre.size(); e++) { myWriter.
			 * write("INSERT INTO `tp_parametre` (`param_id`, `param_uti_username`, `param_lng_id`, `param_thm_id`, `param_img_id`) VALUES("
			 * +listeParametre.get(e).getParamId()+", 'admin', null,'"+listeParametre.get(e)
			 * .getParam_lng_id().getLngId()+"','"+listeParametre.get(e).getParam_img_id().
			 * getImgId()+"');\r\n");
			 * 
			 * }
             */
            // liste des themes
            for (int k = 0; k < listeTheme.size(); k++) {
                myWriter.write(
                        "INSERT INTO `tp_theme` (`thm_id`, `thm_accent`, `thm_primary`, `thm_is_dark`, `thm_is_default`, `thm_name`) VALUES("
                        + listeTheme.get(k).getThmId() + ", '" + listeTheme.get(k).getThmAccent() + "', '"
                        + listeTheme.get(k).getThmPrimary() + "', " + listeTheme.get(k).getThmisDark() + ", "
                        + listeTheme.get(k).getThmisDefault() + ", '" + listeTheme.get(k).getThmName()
                        + "');\r\n");

            }

            // liste des formulaire
            for (int j = 0; j < listeFormulaire.size(); j++) {

                myWriter.write(
                        "INSERT INTO `tp_formulaire` (`frm_id`, `frm_app_id`, `frm_description`, `frm_nom`, `frm_status`, `frm_valider`) VALUES("
                        + listeFormulaire.get(j).getFrmId() + ", '" + listeFormulaire.get(j).getFrmAppId()
                        + "', '" + listeFormulaire.get(j).getFrmDescription() + "', '"
                        + listeFormulaire.get(j).getFrmNom() + "', " + listeFormulaire.get(j).getFrmStatus()
                        + ", '" + listeFormulaire.get(j).getFrmValider() + "');\r\n");

            }
            // liste des workflows
            for (int i = 0; i < listeWorkflow.size(); i++) {

                myWriter.write(
                        "INSERT INTO `tp_workflow` (`wkf_id`, `description`, `group_id`, `name`, `version`, `wkf_artifact_id`, `wkf_conteneur`, `wkf_etat`, `wkf_process_id`, `wkf_app_id`, `wkf_secteur`, `wkf_calltoaction`, `wkf_labelwdgt`) VALUES("
                        + listeWorkflow.get(i).getWkfId() + ", '" + listeWorkflow.get(i).getDescription()
                        + "', '" + listeWorkflow.get(i).getGroupId() + "', '" + listeWorkflow.get(i).getName()
                        + "', '" + listeWorkflow.get(i).getVersion() + "'," + "'"
                        + listeWorkflow.get(i).getWkfArtifactId() + "','"
                        + listeWorkflow.get(i).getWkfConteneur() + "','" + listeWorkflow.get(i).getWkfEtat()
                        + "','" + listeWorkflow.get(i).getWkfProcess_id() + "','"
                        + listeWorkflow.get(i).getWkfAppId().getAppId() + "','"
                        + listeWorkflow.get(i).getWkfSecteur() + "','"
                        + listeWorkflow.get(i).getWkfCalltoaction() + "','"
                        + listeWorkflow.get(i).getWkfLabelwdgt() + "');\r\n");

            }
            // critaire mot de passe
            for (int c = 0; c < listePwdCritaire.size(); c++) {

                myWriter.write(
                        "INSERT INTO `tp_pwd_criteria` (`pwd_id`, `pwd_car_min`, `pwd_dig_min`, `pwd_dure`, `pwd_maj_min`, `pwd_spc_min`)  VALUES("
                        + listePwdCritaire.get(c).getPwdId() + ", '" + listePwdCritaire.get(c).getPwdCarMin()
                        + "', '" + listePwdCritaire.get(c).getPwdDigMin() + "', '"
                        + listePwdCritaire.get(c).getPwdDure() + "', '" + listePwdCritaire.get(c).getPwdMajMin()
                        + "', '" + listePwdCritaire.get(c).getPwdSpcMin() + "');\r\n");

            }
            // Liste fichier
            for (int d = 0; d < listeRapport.size(); d++) {

                myWriter.write(
                        "INSERT INTO `tp_rapport` (`rpt_id`, `rpt_description`, `rpt_jrxml_file`, `rpt_nom`, `rpt_status`,`rpt_valider`,`rpt_app_id`) VALUES("
                        + listeRapport.get(d).getRptId() + ", '" + listeRapport.get(d).getRptDescription()
                        + "', '" + listeRapport.get(d).getRptJrxmlFile().toString() + "','"
                        + listeRapport.get(d).getRptNom() + "'," + listeRapport.get(d).isRptStatus() + ", '"
                        + listeRapport.get(d).getRptValider() + "','"
                        + listeRapport.get(d).getRptAppId().getAppId() + "');\r\n");

            }
            // liste champs rapports
            for (int f = 0; f < listChampsRapport.size(); f++) {

                myWriter.write(
                        "INSERT INTO `td_champs_rapport` (`crt_id`, `crt_class`, `crt_icon`, `crt_label`, `crt_nom`, `crt_obligatoire`, `crt_placeholder`, `crt_regex`, `crt_rpt_id`, `crt_taille`, `crt_type`) VALUES ("
                        + listChampsRapport.get(f).getCrtId() + ", '" + listChampsRapport.get(f).getCrtClass()
                        + "', '" + listChampsRapport.get(f).getCrtIcon() + "', '"
                        + listChampsRapport.get(f).getCrtLabel() + "', '" + listChampsRapport.get(f).getCrtNom()
                        + "', " + listChampsRapport.get(f).getCrtObligatoire() + ", '"
                        + listChampsRapport.get(f).getCrtPlaceholder() + "', '"
                        + listChampsRapport.get(f).getCrtRegex() + "', '"
                        + listChampsRapport.get(f).getCrtRptId() + "', "
                        + listChampsRapport.get(f).getCrtTaille() + ", '"
                        + listChampsRapport.get(f).getCrtType() + "');\r\n");

            }
            // liste workflow secteur activite
            for (int e = 0; e < listWorkflowSecteur.size(); e++) {

                myWriter.write("INSERT INTO `tp_workflow_secteur` (`wsid`, `wsecteur`) VALUES ("
                        + listWorkflowSecteur.get(e).getWsId() + ", '" + listWorkflowSecteur.get(e).getWsSecteur()
                        + "');\r\n");

            }

            // liste module et fonctionnalite
            for (int m = 0; m < listModule.size(); m++) {
                myWriter.write(
                        "INSERT INTO `tp_module` (`mod_id`, `mod_code`, `mod_nom`, `mod_description`) VALUES ("
                        + listModule.get(m).getModId() + ", '" + listModule.get(m).getModCode() + "','" + listModule.get(m).getModNom() + "',"
                        + " '" + listModule.get(m).getModDescription()
                        + "');\r\n");

            }
            
            //liste des task
             for (int t = 0; t < listTask.size(); t++) {
                myWriter.write(
                        "INSERT INTO `tp_task` (`tsk_id`, `tsk_description`, `tsk_form_name`, `tsk_is_first`,"
                        + "`tsk_name`, `tsk_status_id`, `tsk_wkf_id`, `tsk_uti_poowner`, `tsk_form_name_suiv`) VALUES("
                        + listTask.get(t).getTskId() + ", '" + listTask.get(t).getTskDescription() + "', '"
                        + listTask.get(t).getTskFormName() + "','" + listTask.get(t).getTskIsFirst()
                        + "','" + listTask.get(t).getTskName() + "','" + listTask.get(t).getTskStatusId()
                        + "','" + listTask.get(t).getTskWkfId().getWkfId() + "','" + listTask.get(t).getPoOwner().getProId()+ "','" + listTask.get(t).getTskFormNameSuiv() + "');\r\n");

            }
             //liste des task status
            for (int tt = 0; tt < listTaskStatus.size(); tt++) {
                myWriter.write(
                        "INSERT INTO `tp_task_status` (`status_id`, `status_name`) VALUES("
                        + listTaskStatus.get(tt).getStsId() + ", '" + listTaskStatus.get(tt).getStsName() + "');\r\n");

            } 
             //liste des transition de task
             for (int trs = 0; trs < listTransition.size(); trs++) {
                myWriter.write(
                        "INSERT INTO `tr_transition` (`trn_id`, `trn_action`, `trn_tsk_suiv`, `trn_tsk_actuel`, `trn_wkf_id` ) VALUES("
                        + listTransition.get(trs).getTrnId() + ", '" + listTransition.get(trs).getTrnAction()
                        + "','" + listTransition.get(trs).getTrnTskSuiv()+ "','" + listTransition.get(trs).getTrnTskActuel() + "','" + listTransition.get(trs).getTrnWkfId().getWkfId() + "');\r\n");

            } 
             
             //liste des parametrage des regles de gestions de task
             for (int rg = 0; rg < regleGestion.size(); rg++) {
                myWriter.write(
                        "INSERT INTO `tp_regle_gestion` (`rg_id`, `rg_condition`, `rg_donnee_condition`, `rg_titre`, `rg_var_form`,"
                                + "`rg_tsk_id`,`rg_wrfl_id` ) VALUES("
                        + regleGestion.get(rg).getRgId() + ", '" + regleGestion.get(rg).getRgCondition()
                        + "','" + regleGestion.get(rg).getRgDonneeCondition()+ "','" + regleGestion.get(rg).getRgTitre() + "','"
                        + regleGestion.get(rg).getRgVarFomrId()+ "', '" + regleGestion.get(rg).getRgwrflId()+ "', '" + regleGestion.get(rg).getRgTskId() + "');\r\n");

            } 
             
             for (int tmt = 0; tmt < timerTask.size(); tmt++) {
                myWriter.write(
                        "INSERT INTO `tp_task_timer` (`tm_id`, `tm_task_id`, `tm_tasksuiv_id`, `tm_timerdata`, `tm_timertype`,"
                                + "`tm_titre`,`tm_workflowid`) VALUES("
                        + timerTask.get(tmt).getTmId()+ ", '" + timerTask.get(tmt).getTmTaskId()
                        + "','" + timerTask.get(tmt).getTmTaskSuivId()+ "','" + timerTask.get(tmt).getTmTimerData() + "','"
                        + timerTask.get(tmt).getTmTimerType()+ "', '" + timerTask.get(tmt).getTmTitre()+ "', '" + timerTask.get(tmt).getTmWorkflowId() + "');\r\n");

            } 
             

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
