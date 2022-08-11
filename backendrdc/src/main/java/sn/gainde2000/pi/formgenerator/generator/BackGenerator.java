package sn.gainde2000.pi.formgenerator.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import sn.gainde2000.pi.core.tools.StringProcess;
import sn.gainde2000.pi.formgenerator.entity.ChampsV2;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;

public class BackGenerator {

//	public String fichierConfig = "/Users/macbookpro/Documents/gainde2000/project_industrialisation/industrialisation/backend/Industrialisation-backend/opt/industrialisation/fileconfig";
//	public String repo = "/Users/macbookpro/Documents/gainde2000/project_industrialisation/industrialisation/backend/Industrialisation-backend/opt/industrialisation/codesource";
//	
	public String fichierConfig = "/opt/industrialisation/fileconfig";
	public String repo = "/opt/industrialisation/codesource";
	
	
	public String packagename = "sn.gainde2000.pi.core";
	public String importKey = "import";
	public String packageKey = "package";
	public String dossierPackage = "backendrdc/src/main/java/sn/gainde2000/pi/core/";

	public static String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}

		return str.substring(0, 1).toUpperCase() + str.substring(1);
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
	public void generationFileModelBack(String composant, List<ChampsV2> listChamps, boolean isWorkflow)
			throws IOException {
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
				+ "@Basic(optional = false)\n" + "@NotNull\n" + "@Column(name = \"id\")\n" + "private Long id;\n"
				+ ((isWorkflow)
						? (" private Long status; \n" + " private Long poOwner ;\n " + " private Long owner ;\n "
								+ "private Long idLink; \n")
						: "");
		/* generation declaration des attributs */
		int i = 0;
		for (i = 0; i < listChamps.size(); i++) {
			if (!listChamps.get(i).getChpNom().equalsIgnoreCase("id")) {
				if (listChamps.get(i).getChpType().equals("text") || listChamps.get(i).getChpType().equals("textarea")
						|| listChamps.get(i).getChpType().equals("phone")
						|| listChamps.get(i).getChpType().equals("email")
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

		if (isWorkflow) {
			filejavagettersetter = filejavagettersetter + "public Long getStatus() {\r\n" + "		return status;\r\n"
					+ "	}\r\n" + "	public void setStatus(Long status) {\r\n" + "		this.status = status;\r\n"
					+ "	}   \r\n" + "\n";
			filejavagettersetter = filejavagettersetter + "public Long getPoOwner() {\r\n" + "		return poOwner;\r\n"
					+ "	}\r\n" + "	public void setPoOwner(Long poOwner) {\r\n" + "		this.poOwner = poOwner;\r\n"
					+ "	}   \r\n" + "\n";
			filejavagettersetter = filejavagettersetter + "public Long getOwner() {\r\n" + "		return owner;\r\n"
					+ "	}\r\n" + "	public void setOwner(Long owner) {\r\n" + "		this.owner = owner;\r\n"
					+ "	}   \r\n" + "\n";
			filejavagettersetter = filejavagettersetter + "public Long getIdLink() {\r\n" + "		return idLink;\r\n"
					+ "	}\r\n" + "	public void setIdLink(Long idLink) {\r\n" + "		this.idLink = idLink;\r\n"
					+ "	}   \r\n" + "\n";
		}
		filejava = filejava + filejavagettersetter + "\n" + "}";

		FileWriter myWriterentity = new FileWriter(packentity + "/" + capitalize(composant) + ".java");
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
	public void generationFileRepositoryBack(String nomrepo, boolean isWorkflow,boolean isPrimaire, List<FormulaireV2> forms) throws IOException {
		StringProcess uppercasefunct = new StringProcess();
		File packrepo = new File(this.repo + "/" + this.dossierPackage + "repository");
		packrepo.mkdirs();
		File repofiles = new File(packrepo, "" + uppercasefunct.capitalize(nomrepo) + "Repository.java");
		repofiles.createNewFile();
		String repositoryfile = this.packageKey + " " + this.packagename + ".repository;\n" + "\n" + this.importKey
				+ " " + this.packagename + ".entity." + uppercasefunct.capitalize(nomrepo) + ";\n"
				+ "import org.springframework.data.jpa.repository.JpaRepository;\n"
				+ "import org.springframework.stereotype.Repository;\n" + "\n"
				+ "import org.springframework.data.jpa.repository.Query;\n"
				+ "import org.springframework.data.repository.query.Param;\n" + "\n" + "@Repository\n"
				+ "public interface " + uppercasefunct.capitalize(nomrepo) + "Repository extends JpaRepository<"
				+ uppercasefunct.capitalize(nomrepo) + ", Long> {\n";

		if (isWorkflow) {
			repositoryfile += "	 @Query(\"SELECT s from " + uppercasefunct.capitalize(nomrepo)
					+ " s WHERE s.status IN (SELECT t.tskStatusId from Task t WHERE t.poOwner.proId=:poOwner )\")\r\n"
					+ "	    Iterable<" + uppercasefunct.capitalize(nomrepo) + "> list"
					+ uppercasefunct.capitalize(nomrepo) + "(@Param(\"poOwner\") Long poOwner);\r\n" + "	 \r\n"
					+ "	 @Query(\"SELECT s from " + uppercasefunct.capitalize(nomrepo)
					+ " s WHERE s.owner =:owner \")\r\n" + "	    Iterable<" + uppercasefunct.capitalize(nomrepo)
					+ "> list" + uppercasefunct.capitalize(nomrepo) + "ById(@Param(\"owner\") Long owner);\n"

					+ " @Query(\"SELECT s from " + uppercasefunct.capitalize(nomrepo) + " s WHERE s.id =:id \")\r\n"
					+ "	   " + uppercasefunct.capitalize(nomrepo) + " getOne" + uppercasefunct.capitalize(nomrepo)
					+ "(@Param(\"id\") Long id);" + "\n";
		}
		
		if(isWorkflow && isPrimaire) {
			if(forms!=null && forms.size()>0) {
				for(int a = 0 ; a < forms.size(); a++) {
					repositoryfile += "\n@Query(\"SELECT s from "+capitalize(nomrepo)+" s WHERE s.id IN (SELECT t.idLink from "+capitalize(forms.get(a).getFrmNom())+" t where t.poOwner=:poOwner)\")\n" + 
							"Iterable<"+capitalize(nomrepo)+"> list"+capitalize(nomrepo)+"Traites"+capitalize(forms.get(a).getFrmNom())+"(@Param(\"poOwner\") Long poOwner);\n";
				}
			}
		}
		repositoryfile += "\n" + "}";

		FileWriter myWriterrepo = new FileWriter(packrepo + "/" + capitalize(nomrepo) + "Repository.java");
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
	public void generationFileServiceBack(String nomserviceimpl, boolean isWorkflow,boolean isPrimaire, List<FormulaireV2> forms) throws IOException {
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
				+ " " + nomserviceimpl.toLowerCase() + ");\n";
		if (isWorkflow) {
			servicefileimpl += "Iterable<" + uppercasefunct.capitalize(nomserviceimpl) + "> list"
					+ uppercasefunct.capitalize(nomserviceimpl) + "(Long poOwner);\r\n" + "Iterable<"
					+ uppercasefunct.capitalize(nomserviceimpl) + "> list" + uppercasefunct.capitalize(nomserviceimpl)
					+ "ById(Long owner);\n" + "" + uppercasefunct.capitalize(nomserviceimpl) + " getOne"
					+ uppercasefunct.capitalize(nomserviceimpl) + "(Long id);\n";

		}
		
		if(isWorkflow && isPrimaire) {
			if(forms!=null && forms.size()>0) {
				for(int a = 0 ; a < forms.size(); a++)
				servicefileimpl += "Iterable<"+capitalize(nomserviceimpl)+"> list"+capitalize(nomserviceimpl)+"Traites"+capitalize(forms.get(a).getFrmNom())+"(Long poOwner);\n";
			}
		}

		servicefileimpl += "}";

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
				+ "Repository.findById(id);\r\n" + "	}"
				+ ((isWorkflow)
						? ("@Override\r\n" + "	public " + uppercasefunct.capitalize(nomserviceimpl) + " getOne"
								+ uppercasefunct.capitalize(nomserviceimpl) + "(Long id) {\r\n" + "		return "
								+ nomserviceimpl.toLowerCase() + "Repository.getOne"
								+ uppercasefunct.capitalize(nomserviceimpl) + "(id);\r\n" + "	}" + "\n"
								+ "@Override\n" + "public Iterable<" + uppercasefunct.capitalize(nomserviceimpl)
								+ "> list" + uppercasefunct.capitalize(nomserviceimpl) + "(Long poOwner) {\n"
								+ "    return " + nomserviceimpl.toLowerCase() + "Repository.list"
								+ uppercasefunct.capitalize(nomserviceimpl) + "(poOwner);\n" + "}\n" + "@Override\n"
								+ "public Iterable<" + uppercasefunct.capitalize(nomserviceimpl) + "> list"
								+ uppercasefunct.capitalize(nomserviceimpl) + "ById(Long owner) {\n" + "    return "
								+ nomserviceimpl.toLowerCase() + "Repository.list"
								+ uppercasefunct.capitalize(nomserviceimpl) + "ById(owner);\n" + "}")
						: "");
				
		if(isWorkflow && isPrimaire) {
			if(forms!=null && forms.size()>0) {
				for(int a = 0 ; a < forms.size(); a++)
				servicefile += "\n@Override\n" + 
						"    public Iterable<"+capitalize(nomserviceimpl)+"> list"+capitalize(nomserviceimpl)+"Traites"+capitalize(forms.get(a).getFrmNom())+"(Long poOwner) {\n" + 
						"        return this."+nomserviceimpl+"Repository.list"+capitalize(nomserviceimpl)+"Traites"+forms.get(a).getFrmNom()+"(poOwner);\n" + 
						"    }\n";
			}
		}

				servicefile+="}";

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
	public String functionCreateFormWithFile(String composant, List<ChampsV2> listChamps,
			List<ChampsV2> listChampsfile) {

		StringProcess uppercasefunct = new StringProcess();

		String function = "@PostMapping(value=\"" + composant
				+ "/create\",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)\n\r";

		function += "public ServeurResponse createFiles(HttpServletRequest request";

		for (ChampsV2 champs : listChampsfile) {

			function += " , MultipartFile " + champs.getChpNom();

		}

		function += "){\n\r";

		function += "ServeurResponse response = new ServeurResponse();\n" + uppercasefunct.capitalize(composant) + " "
				+ composant.toLowerCase() + ";\n";

		function += "try{\n" + composant.toLowerCase() + " = new ObjectMapper().readValue(\n"
				+ "request.getParameter(\"" + composant.toLowerCase() + "\"), new TypeReference<"
				+ uppercasefunct.capitalize(composant) + ">(){}\n" + ");"
				+ "}\ncatch(Exception ex){\n ex.printStackTrace();}\n";

		function += "try {\n";

		for (ChampsV2 champs : listChampsfile) {

			function += composant.toLowerCase() + ".set" + uppercasefunct.capitalize(champs.getChpNom()) + "("
					+ champs.getChpNom() + ".getBytes());\n";

		}

		function += " " + composant.toLowerCase() + "Service.save(" + composant.toLowerCase() + ");\n"
				+ "            response.setStatut(true);\r\n"
				+ "            response.setDescription(\"Enregistrement réussi\");\r\n"
				+ "            response.setData(" + composant.toLowerCase() + ");\r\n" + "} catch (IOException e1) {\n"
				+ " e1.printStackTrace();\n" + "response.setStatut(false);\n" + " }\n" + "";

		function += " return response;}\n\r";

		for (ChampsV2 champs : listChampsfile) {
			
			function += " @GetMapping(\"" + composant.toLowerCase() + "/downloadFile/" + champs.getChpNom()
					+ "/{id}\")\r\n" + "	public ResponseEntity<ByteArrayResource> downloadfile"+capitalize(champs.getChpNom())+"(@PathVariable String "
					+ champs.getChpNom() + ",@PathVariable Long id,  HttpServletResponse response){\r\n" + "	 \r\n"
					+ "		Optional<" + uppercasefunct.capitalize(composant) + "> data = " + composant.toLowerCase()
					+ "Service.findById(id);\r\n" + "             File file = new File(data.get().get"
					+ uppercasefunct.capitalize(champs.getChpNom()) + "().toLowerCase());\r\n"
					+ "             System.out.println(file.getName());\r\n" + "		return ResponseEntity.ok()\r\n"
					+ "				.contentType(MediaType.APPLICATION_PDF)\r\n"
					+ "				.header(HttpHeaders.CONTENT_DISPOSITION,\"attachment:filename=\"+file.getName())\r\n"
					+ "				.body(new ByteArrayResource(data.get().get"
					+ uppercasefunct.capitalize(champs.getChpNom()) + "()));\r\n" + "             \r\n"
					+ "            \r\n" + "	}\n\r";
		}

		return function;

	}

	public void generationFileControllerBack(String nomcontroller, List<ChampsV2> listChamps,
			List<ChampsV2> listChampsfile, boolean isWorkflow,boolean isPrimary,List<FormulaireV2> forms) throws IOException {
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
				+ ((isWorkflow) ? ("@GetMapping(\"" + nomcontroller.toLowerCase() + "/list/{owner}\")\n"
						+ "    public ServeurResponse getAll" + nomcontroller + "(@PathVariable Long owner) {\n"
						+ "        Iterable<" + uppercasefunct.capitalize(nomcontroller) + "> "
						+ nomcontroller.toLowerCase() + " = " + nomcontroller.toLowerCase() + "Service.list"
						+ uppercasefunct.capitalize(nomcontroller) + "ById(owner);\n"
						+ "        ServeurResponse response = new ServeurResponse();\n" + "        if ("
						+ nomcontroller.toLowerCase() + " == null) {\n" + "            response.setStatut(false);\n"
						+ "            response.setData(null);\n"
						+ "            response.setDescription(\"Aucune élèment trouvé.\");\n" + "\n"
						+ "        } else {\n" + "            \n" + "            response.setStatut(true);\n"
						+ "            response.setData(" + nomcontroller.toLowerCase() + ");\n"
						+ "           response.setDescription(\"Données récupérées.\");\n" + "        }\n"
						+ "        return response;\n" + "   }\n" + "" + "\r\n" + "@GetMapping(\""
						+ nomcontroller.toLowerCase() + "/status/{id}/{status}\")\r\n"
						+ "public ServeurResponse updateStatus" + uppercasefunct.capitalize(nomcontroller)
						+ "(@PathVariable Long id,@PathVariable Long status) {\r\n" + "    "
						+ uppercasefunct.capitalize(nomcontroller) + " " + nomcontroller.toLowerCase() + " = "
						+ nomcontroller.toLowerCase() + "Service.getOne" + uppercasefunct.capitalize(nomcontroller)
						+ "(id);\r\n" + "    " + nomcontroller.toLowerCase() + ".setStatus(status);\r\n" + "    "
						+ nomcontroller.toLowerCase() + "=" + nomcontroller.toLowerCase() + "Service.save("
						+ nomcontroller.toLowerCase() + ");\r\n"
						+ "    ServeurResponse response = new ServeurResponse();\r\n" + "    if ("
						+ nomcontroller.toLowerCase() + " == null) {\r\n" + "        response.setStatut(false);\r\n"
						+ "        response.setData(null);\r\n"
						+ "        response.setDescription(\"Aucune élèment trouvé.\");\r\n" + "\r\n"
						+ "    } else {\r\n" + "        \r\n" + "        response.setStatut(true);\r\n"
						+ "        response.setData(" + nomcontroller.toLowerCase() + ");\r\n"
						+ "       response.setDescription(\"Données récupérées.\");\r\n" + "    }\r\n"
						+ "    return response;\r\n" + "}" + "@GetMapping(\"" + nomcontroller.toLowerCase()
						+ "/task/{poowner}\")\r\n"
						+ "public ServeurResponse getAllTask(@PathVariable Long poowner) {\r\n" + "    Iterable<"
						+ uppercasefunct.capitalize(nomcontroller) + "> " + nomcontroller.toLowerCase() + " = "
						+ nomcontroller.toLowerCase() + "Service.list" + uppercasefunct.capitalize(nomcontroller)
						+ "(poowner);\r\n" + "    ServeurResponse response = new ServeurResponse();\r\n" + "    if ("
						+ nomcontroller.toLowerCase() + "== null) {\r\n" + "        response.setStatut(false);\r\n"
						+ "        response.setData(null);\r\n"
						+ "        response.setDescription(\"Aucune élèment trouvé.\");\r\n" + "\r\n"
						+ "    } else {\r\n" + "        \r\n" + "        response.setStatut(true);\r\n"
						+ "        response.setData(" + nomcontroller.toLowerCase() + ");\r\n"
						+ "       response.setDescription(\"Données récupérées.\");\r\n" + "    }\r\n"
						+ "    return response;\r\n" + "}") : "")
				+ "@GetMapping(\"" + nomcontroller.toLowerCase() + "/list\")\n" + "    public ServeurResponse getAll"
				+ nomcontroller + "() {\n" + "        Iterable<" + uppercasefunct.capitalize(nomcontroller) + "> "
				+ nomcontroller.toLowerCase() + " = " + nomcontroller.toLowerCase() + "Service.findAll();\n"
				+ "        ServeurResponse response = new ServeurResponse();\n" + " " + "  if ("
				+ nomcontroller.toLowerCase() + " == null) {\n" + "          " + "  response.setStatut(false);\n"
				+ "            response.setData(null);\n"
				+ "            response.setDescription(\"Aucune élèment trouvé.\");\n" + "\n" + " } " + "else {\n"
				+ "      response.setStatut(true);\n" + " " + "     response.setData(" + nomcontroller.toLowerCase()
				+ ");\n" + "         response.setDescription(\"Données récupérées.\");\n" + "       " + " }\n"
				+ "        return response;\n" + "}\n"
//				+ ((listChampsfile.size() > 0) ? (" @PostMapping(\"" + nomcontroller.toLowerCase() + "/create\")\n"
//						+ "public ServeurResponse create(@RequestBody " + uppercasefunct.capitalize(nomcontroller) + " "
//						+ nomcontroller.toLowerCase() + ") {\n" + "\n"
//						+ "ServeurResponse response = new ServeurResponse();\n" + "\n" + ""
//						+ nomcontroller.toLowerCase() + "Service.save(" + nomcontroller.toLowerCase() + ");\n"
//						+ "response.setStatut(true);\n" + "response.setDescription(\"Enregistrement réussi\");\n"
//						+ "response.setData(" + nomcontroller.toLowerCase() + ");\n" + "\n" + "return response;\n"
//						+ "}\n" + "@PostMapping(\"" + nomcontroller.toLowerCase() + "/update\")\n"
//						+ "     public ServeurResponse update(@RequestBody " + uppercasefunct.capitalize(nomcontroller)
//						+ " " + nomcontroller.toLowerCase() + ") {\n"
//						+ "          ServeurResponse response = new ServeurResponse();\n" + "\n" + "          "
//						+ nomcontroller.toLowerCase() + "Service.save(" + nomcontroller.toLowerCase() + ");\n"
//						+ "          response.setStatut(true);\n"
//						+ "               response.setDescription(\"Données mis à jour\");\n"
//						+ "          return response;\n" + "     }\n") : "")
				+ " @PostMapping(\"" + nomcontroller.toLowerCase() + "/delete\")\n"
				+ "     public ServeurResponse delete(@RequestBody " + uppercasefunct.capitalize(nomcontroller) + " "
				+ nomcontroller.toLowerCase() + ") {\n"
				+ "          ServeurResponse response = new ServeurResponse();\n" + "          "
				+ nomcontroller.toLowerCase() + "Service.delete(" + nomcontroller.toLowerCase() + ");\n"
				+ "          response.setStatut(true);\n"
				+ "          response.setDescription(\"Données supprimé avec succès\");\n"
				+ "          return response;\n" + "}\n";
		if (listChampsfile.size() == 0) {

				controllerfile = controllerfile + " @PostMapping(\"" + nomcontroller.toLowerCase() + "/create\",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)\n"
						+ "   public ServeurResponse create(HttpServletRequest request) " +  "{\r\n" + "\r\n" 
						+ "        ServeurResponse response = new ServeurResponse();\r\n" + "\r\n"
						+ "        " + uppercasefunct.capitalize(nomcontroller) + " " + nomcontroller.toLowerCase()
						+ ";\r\n" + "        try {\r\n" + "            " + nomcontroller.toLowerCase()
						+ " = new ObjectMapper().readValue(\r\n" + "request.getParameter(\""
						+ nomcontroller.toLowerCase() + "\"), new TypeReference<"
						+ uppercasefunct.capitalize(nomcontroller) + ">() {\r\n" + "            }\r\n"
						+ "            );\r\n" + "\r\n" + "            " 
						+ nomcontroller.toLowerCase() + "Service.save(" + nomcontroller.toLowerCase() + ");\n"
						+ "            response.setStatut(true);\r\n"
						+ "            response.setDescription(\"Enregistrement réussi\");\r\n"
						+ "            response.setData(" + nomcontroller.toLowerCase() + ");\r\n"
						+ "        } catch (JsonParseException e) {\r\n" + " e.printStackTrace();\r\n"
						+ "        } catch (JsonMappingException e) {\r\n" + "e.printStackTrace();\r\n"
						+ "        } catch (IOException e) {\r\n" + " e.printStackTrace();\r\n"
						+ "        } catch (Exception e) {\r\n" + " e.printStackTrace();\r\n"
						+ "        }\r\n return response;\r\n" + "  }\r\n";

				
			

		}
		if (listChampsfile.size() > 1) {
			controllerfile = controllerfile + functionCreateFormWithFile(nomcontroller, listChamps, listChampsfile);
		}

		if(isWorkflow) {
			if(isPrimary) {
				if(forms!=null && forms.size()>0) {
					controllerfile+= "@GetMapping(\""+nomcontroller+"/task/traite/{poowner}/{profil}\")\n" + 
							"    public ServeurResponse getAllTaskTraite(@PathVariable Long poowner, @PathVariable String profil) {\n" + 
							"        Iterable<"+capitalize(nomcontroller)+"> "+nomcontroller+" = null;\n" + 
							"        switch (profil) {\n"; 
					
					for(int a = 0 ; a <forms.size(); a++) {
						controllerfile+= "case \"t"+(a+1)+"\":\n"
								+ nomcontroller +" = "+nomcontroller+"Service.list"+capitalize(nomcontroller)+"Traites"+capitalize(forms.get(a).getFrmNom())+"(poowner);\n"
										+ "break;\n";
					}
					
					
					controllerfile+= 
							"        }\n"; 
					
					controllerfile += 
							"        ServeurResponse response = new ServeurResponse();\n" + 
							"        if (demandepermis == null) {\n" + 
							"            response.setStatut(false);\n" + 
							"            response.setData(null);\n" + 
							"            response.setDescription(\"Aucune élèment trouvé.\");\n" + 
							"\n" + 
							"        } else {\n" + 
							"\n" + 
							"            response.setStatut(true);\n" + 
							"            response.setData(demandepermis);\n" + 
							"            response.setDescription(\"Données récupérées.\");\n" + 
							"        }\n" + 
							"        return response;\n" + 
							"    }";
				}
			}
			
		}
		controllerfile = controllerfile + "}";

		FileWriter myWritecontroller = new FileWriter(
				packcontroller + "/" + capitalize(nomcontroller) + "Controller.java");
		myWritecontroller.write("\n" + controllerfile);
		myWritecontroller.close();

	}
	/// fin back

	public boolean generateAllBack(String composant, List<ChampsV2> listChamps, List<ChampsV2> listChampsfile,
			boolean isWorkflow,boolean isPrimaire, List<FormulaireV2> forms) {
		boolean process = true;

		try {

			this.generationFileRepositoryBack(composant, isWorkflow,isPrimaire,forms);
			this.generationFileServiceBack(composant, isWorkflow,isPrimaire,forms);
			this.generationFileControllerBack(composant, listChamps, listChampsfile, isWorkflow,isPrimaire,forms);

			// fusionner les 2 listes pour le model.
			listChamps.addAll(listChampsfile);
			this.generationFileModelBack(composant, listChamps, isWorkflow);
		} catch (IOException e) {
			e.printStackTrace();
			process = false;
		}

		return process;
	}
}
