package sn.gainde2000.pi.core.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import sn.gainde2000.pi.core.entity.Champs;
import sn.gainde2000.pi.core.entity.JbpmFormInfos;
import sn.gainde2000.pi.core.tools.StringProcess;

public class CrudFormulaireWorkflow {
	 public String fichierConfig ="/opt/industrialisation/fileconfig";
	  public String repo="/opt/industrialisation/codesource";
	  public String packagename="sn.gainde2000.pi.core";
	   public String importKey="import";
	   public String packageKey="package";
	   public String dossierPackage="backendrdc/src/main/java/sn/gainde2000/pi/core/";
	public CrudFormulaireWorkflow() {
		 String SE = System.getProperty("os.name").toLowerCase();
		 if (SE.indexOf("nux") >= 0) {
			 	this.repo="/opt/industrialisation/codesource";
			 	this.fichierConfig ="/opt/industrialisation/fileconfig";
	        } else {
	        	this.repo=System.getProperty("user.dir")+"/opt/industrialisation/codesource";
	        	this.fichierConfig =System.getProperty("user.dir")+"/opt/industrialisation/fileconfig";
	        }
	}
	

	public boolean createComposant(String nomProjet,String name) {
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/component.sh "+name.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
				exitValue=process.waitFor();
		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/component.bat "+name.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
					exitValue=process.waitFor();
		        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	public boolean createModule(String nomProjet,String name) {
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/module.sh "+name.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
				exitValue=process.waitFor();
		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/module.bat "+name.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
					exitValue=process.waitFor();
		        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	
	
	//formulaire ajout
	public boolean createComposant(String nomProjet,String name,String composant) {
		StringProcess uppercasefunct = new StringProcess();
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/component.sh "+name.toLowerCase()+"/components/"+composant.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
				exitValue=process.waitFor();
		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/component.bat "+name.toLowerCase()+"/components/"+composant.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
					exitValue=process.waitFor();
		        }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	public boolean createComposantAdd(String nomProjet,String name,String composant) {
		StringProcess uppercasefunct = new StringProcess();
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/component.sh "+name.toLowerCase()+"/components/add"+uppercasefunct.capitalize(composant.toLowerCase())+" "+this.repo+"/"+nomProjet).inheritIO().start();
				exitValue=process.waitFor();
		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/component.bat "+name.toLowerCase()+"/components/add"+uppercasefunct.capitalize(composant.toLowerCase())+" "+this.repo+"/"+nomProjet).inheritIO().start();
					exitValue=process.waitFor();
		        }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	public boolean createComposantView(String nomProjet,String name,String composant) {
		StringProcess uppercasefunct = new StringProcess();
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/component.sh "+name.toLowerCase()+"/components/view"+uppercasefunct.capitalize(composant.toLowerCase())+" "+this.repo+"/"+nomProjet).inheritIO().start();
				exitValue=process.waitFor();
		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/component.bat "+name.toLowerCase()+"/components/view"+uppercasefunct.capitalize(composant.toLowerCase())+" "+this.repo+"/"+nomProjet).inheritIO().start();
					exitValue=process.waitFor();
		        }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	public boolean deleteComposant(String nomProjet,String name,String workflow) {
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/supprimer.sh src/app/"+workflow.toLowerCase()+"/components/"+name.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
				exitValue=process.waitFor();
		        } else {
		       
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/supprimer.bat src/app/"+workflow.toLowerCase()+"/components/"+name.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
					exitValue=process.waitFor();
		        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	
	public boolean createService(String name, String nomProjet,String workflow) throws IOException {
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/createservice.sh "+name.toLowerCase()+" "+this.repo+"/"+nomProjet+"/src/app/"+workflow)
						.inheritIO().start();
				exitValue=process.waitFor();

		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/createservice.bat "+name.toLowerCase()+" "+this.repo+"/"+nomProjet+"/src/app/"+workflow)
							.inheritIO().start();
					exitValue=process.waitFor();
		        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	public boolean createModel(String name, String nomProjet,String workflow) throws IOException {

		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/createmodel.sh "+name.toLowerCase()+" "+this.repo+"/"+nomProjet+"/src/app/"+workflow)
						.inheritIO().start();
				exitValue=process.waitFor();
		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/createmodel.bat "+name.toLowerCase()+" "+this.repo+"/"+nomProjet+"/src/app/"+workflow)
							.inheritIO().start();
					exitValue=process.waitFor();
		        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	public boolean createRouting(String nomProjet,String name) {
		StringProcess uppercasefunct = new StringProcess();
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/createrouting.sh "+name.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
				exitValue=process.waitFor();

		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/createrouting.bat "+name.toLowerCase()+" "+this.repo+"/"+nomProjet).inheritIO().start();
					exitValue=process.waitFor();
		        }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	public boolean createComposantList(String nomProjet,String name) {
		StringProcess uppercasefunct = new StringProcess();
		int exitValue=1;
		String SE = System.getProperty("os.name").toLowerCase();
		try {
			if (SE.indexOf("nux") >= 0) {
				Process process = new ProcessBuilder("/bin/bash", "-c",this.fichierConfig+"/component.sh "+name.toLowerCase()+"/components/"+uppercasefunct.capitalize(name.toLowerCase())+" "+this.repo+"/"+nomProjet).inheritIO().start();
				exitValue=process.waitFor();

		        } else {
		        	Process process = new ProcessBuilder("cmd", "/C", this.fichierConfig+"/component.bat "+name.toLowerCase()+"/components/"+uppercasefunct.capitalize(name.toLowerCase())+" "+this.repo+"/"+nomProjet).inheritIO().start();
					exitValue=process.waitFor();
		        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return exitValue==0;
	}
	 public void updateComponentAddCss(String composant,String nomProjet,String workflow) {
	    	String rep = this.repo ; 
			  try {
			 
				  StringProcess uppercasefunct = new StringProcess();
			  FileWriter myWriter = new FileWriter(
			 this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/components/add-"+composant.toLowerCase()+"/"+"add-"+composant.toLowerCase()+
					  ".component.scss");
			  myWriter.write(".image{\r\n" + 
			  		"  background: url(\"/assets/images/gainde/ia.jpg\");\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		"#test {\r\n" + 
			  		"  background-image: url();\r\n" + 
			  		"  background-size: cover;\r\n" + 
			  		"  /* background: url(http://www.petite-annonce-gratuite.com/layout_images/carte-france.png) no-repeat;\r\n" + 
			  		"  width: 100%;\r\n" + 
			  		"  height: 390px;*/\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		"mat-grid-tile {\r\n" + 
			  		"  background: rgb(238, 239, 240);\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		"@media screen and (min-width: 100%) and (max-width: 100%) {\r\n" + 
			  		"  #test {\r\n" + 
			  		"      width: 100%;\r\n" + 
			  		"      background-size: cover;\r\n" + 
			  		"  }\r\n" + 
			  		"\r\n" + 
			  		"}\r\n" + 
			  		"body{\r\n" + 
			  		"  background-image: url(\"/assets/images/gainde/ia1.jpg\");\r\n" + 
			  		"  background-size: cover;\r\n" + 
			  		"\r\n" + 
			  		"}\r\n" + 
			  		".right{\r\n" + 
			  		"  margin-left: 20%;\r\n" + 
			  		"}\r\n" + 
			  		".top{\r\n" + 
			  		"  margin-top: 11%;\r\n" + 
			  		"}\r\n" + 
			  		".text{\r\n" + 
			  		"  font-size: 11px;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".center{\r\n" + 
			  		"  margin-bottom: 2%;\r\n" + 
			  		"  display: block;\r\n" + 
			  		"  margin:auto;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".login-page{\r\n" + 
			  		" height: 90vh;\r\n" + 
			  		" //width: 100%;\r\n" + 
			  		"  margin-top: -10%;\r\n"
			  		+ " overflow-wrap: scroll;" + 
			  		"\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".login-spacer{\r\n" + 
			  		"  height: 12vh;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".login-container{\r\n" + 
			  		"  margin: auto;\r\n" + 
			  		"  width: 300px;\r\n" + 
			  		"  padding: 1vh;\r\n" + 
			  		"  background-color: #fff;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".login-form{\r\n" + 
			  		"  display: flex;\r\n" + 
			  		"  flex-direction: column;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		"#login-fab {\r\n" + 
			  		"border-radius: 50%;\r\n" + 
			  		"  height: 56px;\r\n" + 
			  		"  margin: auto;\r\n" + 
			  		"  min-width: 56px;\r\n" + 
			  		"  width: 56px;\r\n" + 
			  		"  overflow: hidden;\r\n" + 
			  		"  background: #1e88e5;\r\n" + 
			  		"  box-shadow: 0 1px 1.5px 0 rgba(0,0,0,.12), 0 1px 1px 0 rgba(0,0,0,.24);\r\n" + 
			  		"  margin-top: -35px;\r\n" + 
			  		"  text-align: center;\r\n" + 
			  		"  left: 0;\r\n" + 
			  		"  right: 0;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		"#lock-icon{\r\n" + 
			  		"  line-height: 56px;\r\n" + 
			  		"  color: #fff;\r\n" + 
			  		"}\r\n" + 
			  		".card-heading{\r\n" + 
			  		"  text-align: center;\r\n" + 
			  		"  color: rgba(0, 0, 0, 0.31);\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".buttons .login{\r\n" + 
			  		"  padding-top: 2vh;\r\n" + 
			  		"  padding-bottom: 2vh;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".buttons .login > button{\r\n" + 
			  		"  width: 40%;\r\n" + 
			  		"}\r\n" + 
			  		"button{\r\n" + 
			  		"  margin: 5%;\r\n" + 
			  		"}\r\n" + 
			  		".buttons .register{\r\n" + 
			  		"  display: flex;\r\n" + 
			  		"  padding-top: 4vh;\r\n" + 
			  		"  padding-bottom: 2vh;\r\n" + 
			  		"  justify-content: space-between;\r\n" + 
			  		"}\r\n" + 
			  		".app-header{\r\n" + 
			  		"  color: #fff;\r\n" + 
			  		"      background-color: #1e88e5;\r\n" + 
			  		"      text-align: center;\r\n" + 
			  		"      margin-top: -3px;\r\n" + 
			  		"      padding: 2px;\r\n" + 
			  		"  -webkit-box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n" + 
			  		"  -moz-box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n" + 
			  		"  box-shadow: 2px 4px 58px -20px rgba(0,0,0,1);\r\n" + 
			  		"  }\r\n" + 
			  		"");
			  myWriter.close();
			  System.out.println("Successfully wrote to the file."); }
			  catch (IOException e) {
				  System.out.println("An error occurred.");
				  e.printStackTrace(); 
				}
	    }
	 public void updateComponentListCss(String composant,String nomProjet,String workflow) {
	    	String rep = this.repo ; 
			  try {
			 
				  StringProcess uppercasefunct = new StringProcess();
			  FileWriter myWriter = new FileWriter(
			 this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/components/"+composant.toLowerCase()+"/"+composant.toLowerCase()+
					  ".component.scss");
			  myWriter.write("@import \"~bootstrap/scss/bootstrap\";\r\n" + 
			  		".position-relative {\r\n" + 
			  		"	position: relative;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".add-contact {\r\n" + 
			  		"	position: absolute;\r\n" + 
			  		"    right: 25px;\r\n" + 
			  		"    top: 30px;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".t{\r\n" + 
			  		"  //@extend: 1px solid;\r\n" + 
			  		"  background-color: rgb(162, 160, 175);\r\n" + 
			  		" }\r\n" + 
			  		"\r\n" + 
			  		"\r\n" + 
			  		" .petit-card{\r\n" + 
			  		"   margin: auto;\r\n" + 
			  		"   border-left: 60px solid;\r\n" + 
			  		"   border-color: rgb(128, 128, 173);\r\n" + 
			  		" //  border: 1px solid;\r\n" + 
			  		" }\r\n" + 
			  		" \r\n" + 
			  		"table {\r\n" + 
			  		"    width: 100%;\r\n" + 
			  		"   margin-top: 30px;\r\n" + 
			  		"  }\r\n" + 
			  		"  .fab{\r\n" + 
			  		"      width: 12%;\r\n" + 
			  		"      margin: 0.5%;\r\n" + 
			  		"  }\r\n" + 
			  		"  th.mat-sort-header-sorted {\r\n" + 
			  		"    color: black;\r\n" + 
			  		"  }\r\n" + 
			  		"  .mat-form-field {\r\n" + 
			  		"    font-size: 14px;\r\n" + 
			  		"    width: 20%;\r\n" + 
			  		"    margin-top: 30px;\r\n" + 
			  		"    margin-left: 10px;\r\n" + 
			  		"\r\n" + 
			  		"  }\r\n" + 
			  		" \r\n" + 
			  		" \r\n" + 
			  		"  .header{\r\n" + 
			  		"  margin-top: 10px;\r\n" + 
			  		"  display: block;\r\n" + 
			  		"  margin: auto;\r\n" + 
			  		"  text-align: center;\r\n" + 
			  		"  }\r\n" + 
			  		"  .foot{\r\n" + 
			  		"    background:rgb(90, 90, 158);\r\n" + 
			  		"    text-align: center;\r\n" + 
			  		"  }\r\n" + 
			  		"  .mymargin{\r\n" + 
			  		"   margin: 7px;\r\n" + 
			  		"  }\r\n" + 
			  		"  .container {\r\n" + 
			  		"    display: grid;\r\n" + 
			  		"    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));\r\n" + 
			  		"    grid-gap: 0.5em;\r\n" + 
			  		"    padding: 0.5em 0.5em;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		"\r\n" + 
			  		"\r\n" + 
			  		"h3 {\r\n" + 
			  		"  font-weight: normal;\r\n" + 
			  		"  color: #616161;\r\n" + 
			  		"  font-size: 16px;\r\n" + 
			  		"  margin: 8px;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		".entete{\r\n" + 
			  		"  width: 100%;\r\n" + 
			  		"  background: rgb(233, 230, 230);\r\n" + 
			  		" // margin-top: -5%;\r\n" + 
			  		"  position: relative;\r\n" + 
			  		"}\r\n" + 
			  		"\r\n" + 
			  		"// .number{\r\n" + 
			  		"\r\n" + 
			  		"//   text-align: justify;\r\n" + 
			  		"//   margin: auto;\r\n" + 
			  		"//  font-family: cursive;\r\n" + 
			  		"//   font-size:200%;\r\n" + 
			  		"// }\r\n" + 
			  		"// .type{\r\n" + 
			  		"//   margin-right: 4px;\r\n" + 
			  		"//   text-align: justify;\r\n" + 
			  		"//   margin: auto; \r\n" + 
			  		"//   font-family: fantasy;\r\n" + 
			  		"\r\n" + 
			  		"// }\r\n" + 
			  		"\r\n" + 
			  		"\r\n" + 
			  		".card {\r\n" + 
			  		"  box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.14);\r\n" + 
			  		"  border: 0;\r\n" + 
			  		"  margin-bottom: 30px;\r\n" + 
			  		"  margin-top: 30px;\r\n" + 
			  		"  border-radius: 6px;\r\n" + 
			  		"  color: #333333;\r\n" + 
			  		"  background: #fff;\r\n" + 
			  		"  width: 100%;\r\n" + 
			  		"  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.12);\r\n" + 
			  		"  position: relative;\r\n" + 
			  		"    display: -webkit-box;\r\n" + 
			  		"    display: flex;\r\n" + 
			  		"    -webkit-box-orient: vertical;\r\n" + 
			  		"    -webkit-box-direction: normal;\r\n" + 
			  		"    flex-direction: column;\r\n" + 
			  		"    min-width: 0;\r\n" + 
			  		"    word-wrap: break-word;\r\n" + 
			  		"    background-color: #fff;\r\n" + 
			  		"    background-clip: border-box;\r\n" + 
			  		"    border: 1px solid #eeeeee;\r\n" + 
			  		"    border-radius: 0.25rem;\r\n" + 
			  		"   \r\n" + 
			  		"\r\n" + 
			  		"}");
			  myWriter.close();
			  System.out.println("Successfully wrote to the file."); }
			  catch (IOException e) {
				  System.out.println("An error occurred.");
				  e.printStackTrace(); 
				}
	    }
	 public void updateComponent(String composant,String nomProjet,JsonArray ja,String workflow) {
	    	String rep = this.repo ; 
			  try {
			 
				  StringProcess uppercasefunct = new StringProcess();
			  FileWriter myWriter = new FileWriter(
			 this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/components/add-"+composant.toLowerCase()+"/"+"add-"+composant.toLowerCase()+
					  ".component.ts");
			  myWriter.write("import { "+uppercasefunct.capitalize(composant)+"Service } from '../../service/"+composant.toLowerCase()+".service';\r\n" + 
			  		"import { "+uppercasefunct.capitalize(composant)+" } from '../../model/"+composant.toLowerCase()+"';\r\n" + 
			  		"import { Component, OnInit,Input,Inject } from '@angular/core';\r\n" + 
			  		"import { Router } from '@angular/router';\r\n" + 
			  		"import { FormBuilder, Validators } from '@angular/forms';\r\n" + 
			  		"import { MatDialogRef, MatSnackBar,MAT_DIALOG_DATA } from '@angular/material';\r\n"
			  		+ "import { TranslateService } from '@ngx-translate/core';"+
			  		"\r\n"
			  		+ "import { NotificationService } from '../../../shared/services/notification.service';\r\n" + 
			  		"@Component({\r\n" + 
			  		"  selector: 'app-add-"+composant.toLowerCase()+"',\r\n" + 
			  		"  templateUrl: './add-"+composant.toLowerCase()+".component.html',\r\n" + 
			  		"  styleUrls: ['./add-"+composant.toLowerCase()+".component.scss']\r\n" + 
			  		"})\r\n"
			  		+  
			  		"export class Add"+uppercasefunct.capitalize(composant)+"Component implements OnInit {\r\n" + 
			  		"result:any;\n"
			  		+ "  public "+composant.toLowerCase()+": Array<File>;\n"
			  		+ " @Input() accept = '.';"
			  		+ "resp:any;\n"
			  		+ " isFileValid: boolean;\r\n" + 
			  		"  images;\r\n" + 
			  		"  href:any\r\n" + 
			  		"  base64Data: any;\r\n" + 
			  		"  based\r\n" + 
			  		"  isUpload: boolean;\n"
			  		+ "isfile:boolean;\n"
			  		+ "extention:any;\n");
		
		   		
	
			  myWriter.write(""+uppercasefunct.capitalize(composant)+"Form = this.formbuild.group({");
			  for(int i=0; i<ja.size(); i++) {
		   			JsonObject json = (JsonObject) ja.get(i);
				 
					  myWriter.write(json.get("name").getAsString().toLowerCase()+" :['', Validators.required],\r\n");
			    } 
			  myWriter.write("poOwner :['', Validators.required],\n"+
					      "owner :['', Validators.required],\n"
			  		      + " idLink:['',Validators.required]});");
		
			  myWriter.write("  constructor(private "+composant.toLowerCase()+"Service: "+uppercasefunct.capitalize(composant)+"Service,\r\n" + 
			  		"    private router: Router,"
			  		+ " private formbuild: FormBuilder, \r\n" + 
			  		"    private _snackBar: MatSnackBar,\r\n"
			  		+ "	private translate:TranslateService,\r\n"
			  		+ "private notification: NotificationService,\r\n"
			  		+ "@Inject(MAT_DIALOG_DATA) public donner: any" + 
			  		"    ,public dialogRef: MatDialogRef<Add"+uppercasefunct.capitalize(composant)+"Component >) {"
			  				+ "this.isfile=false }\r\n" + 
			  		"\r\n" + 
			  		"  ngOnInit() {\r\n");
			 
			 	myWriter.write("  }\r\n" + 
				  		"\r\n");
			 	 for(int i=0; i<ja.size(); i++) {
			   			JsonObject json = (JsonObject) ja.get(i);
		  		
			  	if (json.get("code").getAsString().equals("Document")){
			  
			  		myWriter.write(" onSubmitFile() {\r\n" + 
			  				"    if (this."+uppercasefunct.capitalize(composant)+"Form.value) {\r\n"
			  						+ "if(this.donner){"
			  						+ " this."+uppercasefunct.capitalize(composant)+"Form.value.idLink=this.donner.id\n"
			  						+ "}else{\n"
			  						+ "this."+uppercasefunct.capitalize(composant)+"Form.value.idLink=null}\n"
			  						+ "this."+uppercasefunct.capitalize(composant)+"Form.value.poOwner=localStorage.getItem(\"profile\") \r\n" 
			  						+ "this."+uppercasefunct.capitalize(composant)+"Form.value.owner=localStorage.getItem(\"id\") \r\n" + 
			  				"      this."+composant.toLowerCase()+"Service.add"+uppercasefunct.capitalize(composant)+"(this."+composant.toLowerCase()+"[0],this."+uppercasefunct.capitalize(composant)+"Form.value).subscribe(data => {\r\n" + 
			  				"        if (data.statut) {\r\n"
			  				+ "	 this."+composant.toLowerCase()+"Service.updateTask"+uppercasefunct.capitalize(composant)+"(this.donner.data.id,this.donner.status).subscribe(data=>{\r\n" + 
			  				"\r\n" + 
			  				"        })\n" + 
			  				"          let ReportSaveSuccess;\r\n" + 
			  				"          this.translate.get('"+composant.toLowerCase()+".confirmEnr').subscribe((res: string) => {\r\n" + 
			  				"            ReportSaveSuccess = res;\r\n" + 
			  				"          });\r\n" + 
			  				"          this.translate.get(ReportSaveSuccess).subscribe((res: string) => {\r\n" + 
			  				"            this.notification.success(res);\r\n" + 
			  				"          });\r\n" + 
			  				"\r\n" + 
			  				"         this."+uppercasefunct.capitalize(composant)+"Form.reset();\r\n" + 
			  				"          this.closeDialog();\r\n" + 
			  				"        }\r\n" + 
			  				"      }, error => {\r\n" + 
			  				"        let ReportSaveError;\r\n" + 
			  				"        this.translate.get('"+composant.toLowerCase()+".erreurEnr').subscribe((res: string) => {\r\n" + 
			  				"          ReportSaveError = res;\r\n" + 
			  				"        });\r\n" + 
			  				"        this.translate.get(ReportSaveError).subscribe((res: string) => {\r\n" + 
			  				"          this.notification.error(res);\r\n" + 
			  				"        });\r\n" + 
			  				"      });\r\n" + 
			  				"    } else {\r\n" + 
			  				"      let errorChamps;\r\n" + 
			  				"      let form;\r\n" + 
			  				"      this.translate.get('control.error').subscribe((res: string) => {\r\n" + 
			  				"        form = res;\r\n" + 
			  				"      });\r\n" + 
			  				"\r\n" + 
			  				"      this.translate.get('control.required').subscribe((res: string) => {\r\n" + 
			  				"        errorChamps = res;\r\n" + 
			  				"      });\r\n" + 
			  				"      this.translate.get(errorChamps).subscribe((res: string) => {\r\n" + 
			  				"        this.notification.error(res);\r\n" + 
			  				"      });\r\n" + 
			  				" \r\n" + 
			  				"    }\r\n" + 
			  				"  }\n");
			  		
			  		}
		  		}
			  	
			  	myWriter.write("onSubmit() {\r\n" 
			  			+ "this."+uppercasefunct.capitalize(composant)+"Form.value.poOwner=localStorage.getItem(\"profile\") \r\n" 
			  			+ "this."+uppercasefunct.capitalize(composant)+"Form.value.owner=localStorage.getItem(\"id\") \r\n" + 
			  			"if(this.donner){"
  						+ " this."+uppercasefunct.capitalize(composant)+"Form.value.idLink=this.donner.id\n"
  						+ "}else{"
  						+ "this."+uppercasefunct.capitalize(composant)+"Form.value.idLink=null}\n"+
				  		"    this."+composant.toLowerCase()+"Service.create"+uppercasefunct.capitalize(composant)+"(this."+uppercasefunct.capitalize(composant)+"Form.value).subscribe(data => {\r\n" + 
				  		"  \r\n"
				  		+ "  this.result=data \n\r" + 
				  		"      if (this.result.statut) {\r\n"
				  		+ " this."+composant.toLowerCase()+"Service.updateTask"+uppercasefunct.capitalize(composant)+"(this.donner.data.id,this.donner.status).subscribe(data=>{\r\n" + 
				  				"\r\n" + 
				  		"        })\r\n" + 
				  		"        this.notification.info(this.result.description);\r\n" + 
				  		"        this."+uppercasefunct.capitalize(composant)+"Form.reset();\r\n" + 
				  		"        this.closeDialog();\r\n" + 
				  		"      }\r\n" + 
				  		"    }, error => {\r\n" + 
				  		"     this.notification.error('"+uppercasefunct.capitalize(composant)+" invalide');\r\n" + 
				  		"    });\r\n" + 
				  		"  }\r\n" + 
				  		"\r\n" + 
				  		"\r\n" + 
				  		"  closeDialog() {\r\n" + 
				  		"    this.dialogRef.close();\r\n" + 
				  		"  }\r\n");
				 for(int i=0; i<ja.size(); i++) {
			   			JsonObject json = (JsonObject) ja.get(i);
			   			
			
				  if (json.get("code").getAsString().equals("Document")){
						 myWriter.write("  uploadImage(event: any) {\r\n"
						 		+ 	"this.isfile=true \n" + 
						 		"    if (event.target.files[0]) {\r\n" + 
						 		"       this.extention= event.target.files[0].name.split('.')[1].toLowerCase();\r\n" + 
						 		"       \r\n" + 
						 		"      this."+composant.toLowerCase()+" = event.target.files;\r\n" + 
						 		"      if (this."+composant.toLowerCase()+"[0].size >3000000){\r\n" + 
						 		"        this.translate.get(\"Verifier la taille du document!!\").subscribe((res: string) => {\r\n" + 
						 		"          this.notification.warn(res);\r\n" + 
						 		"        });\r\n" + 
						 		"        return;\r\n" + 
						 		"\r\n" + 
						 		"      }\r\n" + 
						 		"      this.isUpload = true;\r\n" + 
						 		"    }\r\n" + 
						 		"  }\r\n");
						 
						 
	                   }
				  
				  

			    } 
			  
			  
			  myWriter.write("}");
			  myWriter.close();
			  System.out.println("Successfully wrote to the file."); }
			  catch (IOException e) {
				  System.out.println("An error occurred.");
				  e.printStackTrace(); 
				}
	    }
	 public void updateComponentAddHtml(String nomprojet,String composant,JsonArray ja,String workflow) throws IOException {
		 StringProcess uppercasefunct = new StringProcess();
		 FileWriter myWriter = new FileWriter(
	   					this.repo+"/"+nomprojet+"/src/app/"+workflow.toLowerCase()+"/components/add-"+composant.toLowerCase()+"/"+"add-"+composant.toLowerCase()+
	   							".component.html");
	   			myWriter.write("<div class=\"login-page\">\r\n" + 
	   					"    <div class=\"login-spacer\"></div>\r\n" + 
	   					"    <div class=\"\">\r\n" + 
	   					"      <h3 class=\"card-heading\">\r\n" + 
	   					"       <strong class=\"foncer\"> {{'"+composant.toLowerCase()+".register' | translate}}\r\n" + 
	   					"      </strong> </h3>\r\n");
	   		  for(int i=0; i<ja.size(); i++) {
		   			JsonObject json = (JsonObject) ja.get(i);
		   			if(json.get("code").getAsString().equals("Document"))  {
	   			myWriter.write( "<form class=\"\" [formGroup]=\""+uppercasefunct.capitalize(composant)+"Form\" (ngSubmit)=\"onSubmitFile()\"> \r\n");
		   		}
		   		else {
		   			myWriter.write( "<form class=\"\" [formGroup]=\""+uppercasefunct.capitalize(composant)+"Form\" (ngSubmit)=\"onSubmit()\"> \r\n");
		   			}
		   		break;
	   		  }
	   		  for(int i=0; i<ja.size(); i++) {
	   			JsonObject json = (JsonObject) ja.get(i);

	   			     if(json.get("code").getAsString().equals("TextBox"))  {
	   			    	myWriter.write(" <mat-form-field appearance=\"fill\">\r\n" + 
		   						"        <mat-label>{{'"+composant.toLowerCase()+"."+json.get("name").getAsString().toLowerCase()+"' | translate}}</mat-label>\n" + 
		   						"          <input matInput  type=\"text\"\r\n" + 
		   						"            formControlName=\""+json.get("name").getAsString().toLowerCase()+"\" required >\r\n" + 
		   						"        </mat-form-field>\r\n");
	   			    }else if(json.get("code").getAsString().equals("CheckBox")) {
	   			    	myWriter.write("   <mat-label>{{'"+composant.toLowerCase()+"."+json.get("name").getAsString().toLowerCase()+"' | translate}}</mat-label>\n" + 
		   						"          <input type=\"checkbox\"\r\n" + 
		   						"            formControlName=\""+json.get("name").getAsString().toLowerCase()+"\" required  >\r\n");
	   			    }
	   			    else if(json.get("code").getAsString().equals("DatePicker")) {
	   			    	myWriter.write("<mat-form-field  appearance=\"fill\">\r\n" + 
	   			    			"        <mat-label>{{'"+composant.toLowerCase()+"."+json.get("name").getAsString().toLowerCase()+"' | translate}}</mat-label>\n" + 
	   			    			"        <input matInput [matDatepicker]=\"picker\" formControlName=\""+json.get("name").getAsString().toLowerCase()+"\" required>\r\n" + 
	   			    			"        <mat-datepicker-toggle matSuffix [for]=\"picker\"></mat-datepicker-toggle>\r\n" + 
	   			    			"        <mat-datepicker #picker></mat-datepicker>\r\n" + 
	   			    			"      </mat-form-field>");
	   			    }
	   			    else if(json.get("code").getAsString().equals("TextArea")) {
	   			    	myWriter.write(" <textarea id=\""+json.get("name").getAsString()+"\" formControlName=\""+json.get("name").getAsString().toLowerCase()+"\" rows=\"3\" cols=\"90\" required></textarea>");
	   			    }
	   			    else if(json.get("code").getAsString().equals("RadioGroup")){
	   			    	myWriter.write(" <mat-form-field appearance=\"fill\">\r\n" + 
	   			    			"        <mat-label>{{'"+composant.toLowerCase()+"."+json.get("name").getAsString().toLowerCase()+"' | translate}}</mat-label>\n" + 
		   						"          <input matInput type=\"radio\"\r\n" + 
		   						"            formControlName=\""+json.get("name").getAsString().toLowerCase()+"\" required >\r\n" + 
		   						"        </mat-form-field>\r\n");
	   			    }
	   			     
	   			    else if(json.get("code").getAsString().equals("Document")){
	   			    	myWriter.write("        <mat-label>{{'"+composant.toLowerCase()+"."+json.get("name").getAsString().toLowerCase()+"' | translate}}</mat-label>\n"
	   			    			+ " <input type=\"file\" formControlName=\"file\"  accept=\"{{accept}}\" (change)=\"uploadImage($event)\" required>");
	   			    }
	   			    else if(json.get("code").getAsString().equals("ListBox")) {
	   			    	JsonArray option= json.getAsJsonArray("options");
	   			    	
		   			    	myWriter.write("  <mat-form-field appearance=\"fill\">\r\n" + 
		   			    			"        <mat-label>{{'"+composant.toLowerCase()+"."+json.get("name").getAsString().toLowerCase()+"' | translate}}</mat-label>\n" + 
		   			    			"                  <mat-select  formControlName=\""+json.get("name").getAsString().toLowerCase()+"\"required>\r\n");
		   			     for(int j=0; j<option.size(); j++) {
		   		   			JsonObject op = (JsonObject) option.get(j);
		   		   		System.out.print(op.get("value").getAsString());
		   			    		myWriter.write("<mat-option  value=\""+op.get("value").getAsString()+"\">{{'"+composant.toLowerCase()+"."+op.get("value").getAsString().toLowerCase()+"'|translate}}</mat-option>\r\n");
		   			    	}
		   			    			
		   			    	myWriter.write(" </mat-select>\r\n" + 
		   			    			"      </mat-form-field>");
	   			    
	   		  }
	   		  }
	   			myWriter.write(" <div class=\"buttons\">\r\n" + 
	   					"          <div class=\"login\">\r\n" + 
	   								"<button mat-raised-button color=\"warn\" (click)=\"closeDialog()\" type=\"button\">{{'btn_fermer' | translate}}</button>\r\n"+
	   					"            <button mat-raised-button color=\"primary\" type=\"submit\">{{'btn_valider' | translate}}</button>\r\n" + 
	   					"          </div>\r\n" + 
	   					"        </div>\r\n" + 
	   					"      </form>\r\n" + 
	   					"    </div>\r\n" + 
	   					"  </div>");
	   			myWriter.close();
	   		  
	 }
	 public void updateComponentViewTs(String composant, String nomProjet, JsonArray ja,String  workflow) {
			String rep = this.repo;
			try {

				StringProcess uppercasefunct = new StringProcess();
				FileWriter myWriter = new FileWriter(
						this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/components/view-"+composant.toLowerCase()+"/"+"view-"+composant.toLowerCase()+
						  ".component.ts");
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
						+ "this.detail();\r\n" + "  }\r\n" + "detail() {   \r\n" + "this.result = this.donnee;" +

						"  }" + "\r\n" + "  closeDialog() {\r\n" + "    this.dialogRef.close();\r\n" + "  }\r\n" + "}");
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}

		public void updateComponentViewCss(String composant, String nomProjet,String workflow) {
			String rep = this.repo;
			try {

				StringProcess uppercasefunct = new StringProcess();
				FileWriter myWriter = new FileWriter(
						this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/components/view-"+composant.toLowerCase()+"/"+"view-"+composant.toLowerCase()+
						  ".component.scss");
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

		public void updateComponentViewHtml(String composant, String nomProjet, JsonArray ja,String  workflow)
				throws IOException {
			StringProcess uppercasefunct = new StringProcess();
			FileWriter myWriter = new FileWriter(
					this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/components/view-"+composant.toLowerCase()+"/"+"view-"+composant.toLowerCase()+
					  ".component.html");
			myWriter.write(" <div class=\"scroll\">\n" + " <mat-card-header>\r\n"
					+ "    <mat-card-subtitle>Detail</mat-card-subtitle>\r\n" + "\r\n" + "  </mat-card-header>\r\n "
					+ "<mat-card-content>\r\n" + "    <div class=\"row\">\r\n"
					+ "      <div class=\"col-sm-6 col-lg-6 col-md-6\">");
			 for(int i=0; i<ja.size(); i++) {
		   			JsonObject json = (JsonObject) ja.get(i);
				
					myWriter.write("<mat-label><strong>{{'" + composant.toLowerCase() + "." + json.get("name").getAsString().toLowerCase()
							+ "' | translate}}</strong>: {{result." + json.get("name").getAsString().toLowerCase()
							+ "}}</mat-label><br>\r\n" + "        <hr>");
				

			}
			myWriter.write(" </div>\r\n" + "    </div>\r\n" + "  </mat-card-content>\r\n" + "  <mat-card-actions>\r\n"
					+ "    <button mat-button color=\"primary\" (click)=\"closeDialog()\">{{'btn_fermer' | translate}}</button>\r\n"
					+ "\r\n" + "  </mat-card-actions>\n" + "</div>");
			myWriter.close();
		}
	 
	 
	 public void updateComponentListHtml(String composant,String nomProjet,JsonArray ja,JbpmFormInfos formInfos, String  workflow,List<JbpmFormInfos> listForm) {
	    	String rep = this.repo ; 
			  try {
				  
				  StringProcess uppercasefunct = new StringProcess();
			  FileWriter myWriter = new FileWriter(
			 this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/components/"+composant.toLowerCase()+"/"+composant.toLowerCase()+
					  ".component.html");
			  myWriter.write(" <mat-card class=\"\">\r\n" + 
			  		"            <mat-card-header class=\"card-header\">\r\n" + 
			  		"                <span class=\"header\">{{ '"+composant.toLowerCase()+".list' | translate }}</span>\r\n" + 
			  		"            </mat-card-header>\r\n" + 
			  		"           <mat-form-field appearance=\"outline\">\r\n" + 
			  		"        <mat-label>{{'utilisateur.filtre' | translate}}</mat-label>\r\n" + 
			  		"        <input matInput (keyup)=\"applyFilter($event)\">\r\n" + 
			  		"      </mat-form-field>\r\n");
			  			if(formInfos.getJfrmPrimaire()) {
			  myWriter.write(" <button *ngIf=\""+formInfos.getJfrmFormulaire().toLowerCase()+"\" mat-mini-fab color=\"primary\" class=\"add-contact\" (click)=\"openDialogAdd()\"\r\n" + 
			  		"                matTooltip=\"{{'"+composant.toLowerCase()+".add' | translate}}\">\r\n" + 
			  		"                <mat-icon>person_add</mat-icon>\r\n" + 
			  		"            </button>\r\n");
			  			}
			  	myWriter.write( " <mat-menu #detail=\"matMenu\">\r\n" + 
			  		"        <form [formGroup]=\"formchamps\">\r\n" + 
			  		"          <div *ngFor=\"let cd of columnDefinitions\">\r\n" + 
			  		"            &nbsp; <mat-checkbox [formControlName]=\"cd.def\">{{cd.label}}&nbsp;\r\n" + 
			  		"            </mat-checkbox>\r\n" + 
			  		"          </div>\r\n" +
			  		"        </form>\r\n" + 
			  		"      </mat-menu>\n");
			  	myWriter.write(" <div class=\"\">\r\n" 
			  			+ "<button class=\"float-right\" mat-button [mat-menu-trigger-for]=\"detail\" yPosition=\"above\">\r\n" + 
				  		"      <mat-icon matTooltip=\"{{'utilisateur.details'| translate}}\" color=\"secondary\">\r\n" + 
				  		"       filter_alt\r\n" + 
				  		"      </mat-icon>\r\n" + 
				  		"    </button>\n" + 
			  		"                <table mat-table class=\" mat-elevation-z8\" [dataSource]=\"dataSource\" matSort>\r\n" );
			    for(int i=0; i<ja.size(); i++) {
		   			JsonObject json = (JsonObject) ja.get(i);
		  			            
		  			             if(json.get("code").getAsString().equals("Document")) {
		  			            	 myWriter.write("<ng-container matColumnDef=\""+json.get("name").getAsString().toLowerCase()+"\">\r\n" + 
			  			            		  "        <th mat-header-cell *matHeaderCellDef mat-sort-header>{{'"+composant.toLowerCase()+"."+json.get("name").getAsString().toLowerCase()+"' | translate}}</th>\r\n" + 
			  			            		  "         <td mat-cell *matCellDef=\"let element\">"
			  			            		  + " <mat-icon  color=\"primary\">file_copy\r\n" + 
			  			            		  "    </mat-icon> </td>\r\n" + 
			  			            		  "         </ng-container>\r\n"); 
		  			             }
		  			             else {
		  			              myWriter.write("<ng-container matColumnDef=\""+json.get("name").getAsString().toLowerCase()+"\">\r\n" + 
		  			            		  "        <th mat-header-cell *matHeaderCellDef mat-sort-header>{{'"+composant.toLowerCase()+"."+json.get("name").getAsString().toLowerCase()+"' | translate}} </th>\r\n" + 
		  			            		  "         <td mat-cell *matCellDef=\"let element\"> {{element."+json.get("name").getAsString().toLowerCase()+"}} </td>\r\n" + 
		  			            		  "         </ng-container>\r\n" 
		  			            		  	);
		  			          }
		  			          }
	
						myWriter.write(" <ng-container matColumnDef=\"action\">\r\n" + 
								"                <th mat-header-cell *matHeaderCellDef><span style=\"margin-left: 0%;\">\r\n" + 
								"                        {{'register.action' | translate}}</span> </th>\r\n" + 
								"                <td mat-cell *matCellDef=\"let element\" class=\"fab\">\r\n" + 
								"                    <button mat-button [mat-menu-trigger-for]=\"detail\"   yPosition=\"above\">\r\n" + 
								"                        <mat-icon matTooltip=\"{{'utilisateur.details'| translate}}\" color=\"secondary\"\r\n" + 
								"                          >\r\n" + 
								"                            more_vert\r\n" + 
								"                        </mat-icon>\r\n" + 
								"                    </button>\r\n");
					
						myWriter.write(" <mat-menu #detail=\"matMenu\">\r\n"); 
						   for(int i=0; i<listForm.size(); i++) {
							    if(!listForm.get(i).getJfrmPrimaire())
							   myWriter.write("<button *ngIf=\""+listForm.get(i).getJfrmFormulaire().toLowerCase()+"\" mat-menu-item (click)=\"openDialog"+uppercasefunct.capitalize(listForm.get(i).getJfrmFormulaire())+"(element)\">\r\n" + 
								"\r\n" + 
								"                            <mat-icon matTooltip=\"{{'Action."+listForm.get(i).getJfrmFormulaire().toLowerCase()+"' | translate}}\" color=\"primary\">settings\r\n" + 
								"                            </mat-icon>{{'Action."+listForm.get(i).getJfrmFormulaire().toLowerCase()+"' | translate}}\r\n" + 
								"                        </button>\r\n");
						   }
						
						
						 for(int j=0; j<ja.size(); j++) {
					   			JsonObject json = (JsonObject) ja.get(j);
							if(json.get("code").getAsString().equals("Document")) {
						myWriter.write( "<button mat-menu-item (click)=\"downloadImage(element)\">\r\n" + 
								"\r\n" + 
								"                          <mat-icon color=\"warn\" matTooltip=\"{{'Action.dawnload' | translate}}\">dawnload</mat-icon>\r\n" + 
								"                          {{'Action.dawnload' | translate}}\r\n" + 
								"                      </button>\n");
						     break;
							}
						}
					 myWriter.write("</mat-menu>\r\n");	
						myWriter.write(
								"                </td>\r\n" + 
								"            </ng-container>\r\n" + 
								"           <tr mat-header-row *matHeaderRowDef=\"getDisplayedColumns()\"></tr>\r\n" + 
								"           <tr mat-row *matRowDef=\"let row; columns: getDisplayedColumns();\"></tr>\r\n" + 
								"        </table>\r\n" + 
								"        <mat-paginator [pageSizeOptions]=\"[5, 10, 20]\" showFirstLastButtons></mat-paginator>\r\n" + 
								"    </div>\r\n" + 
								"</mat-card>\n");
			  myWriter.close();
			  System.out.println("Successfully wrote to the file."); }
			  catch (IOException e) {
				  System.out.println("An error occurred.");
				  e.printStackTrace(); 
				}
	    }
	 
	 
	 public void updateComponentList(String composant,String nomProjet, JsonArray ja,JbpmFormInfos formInfos,String workflow,List<JbpmFormInfos> listform) {
	    	String rep = this.repo ; 
			  try {
			 
				  StringProcess uppercasefunct = new StringProcess();
			  FileWriter myWriter = new FileWriter(
			 this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/components/"+composant.toLowerCase()+"/"+composant.toLowerCase()+
					  ".component.ts");
			  myWriter.write("import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';\r\n" + 
			  		"import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';\r\n" + 
			  		"import { ActivatedRoute, Router } from '@angular/router';\r\n" + 
			  		"import { MatPaginator, MatTableDataSource, MatSort, MatDialog, MatSnackBar } from '@angular/material';\r\n");
			  for(int i=0; i<listform.size(); i++) {	
				  myWriter.write("import { Add"+uppercasefunct.capitalize(listform.get(i).getJfrmFormulaire())+"Component } from '../add-"+listform.get(i).getJfrmFormulaire().toLowerCase()+"/add-"+listform.get(i).getJfrmFormulaire().toLowerCase()+".component';\r\n"); 
			  }
			  	  myWriter.write("import { View"+uppercasefunct.capitalize(composant)+"Component } from '../view-"+composant.toLowerCase()+"/view-"+composant.toLowerCase()+".component';\r\n" + 
			  	    "import { "+uppercasefunct.capitalize(composant)+"Service } from '../../service/"+composant.toLowerCase()+".service';\r\n" + 
			  		"import { "+uppercasefunct.capitalize(composant)+"} from '../../model/"+composant.toLowerCase()+"';\r\n" + 
			  		"import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';\r\n" + 
			  		"import { TranslateService } from '@ngx-translate/core';\r\n"
			  		+ "import { NotificationService } from '../../../shared/services/notification.service';\r\n"
			  		+ "import * as fileSaver from 'file-saver';" + 
			  		"\r\n"
			  		+ "import { merge, Observable } from 'rxjs';" + 
			  		"\r\n" + 
			  		"@Component({\r\n" + 
			  		"	selector: '"+composant+"',\r\n" + 
			  		"	templateUrl: './"+composant+".component.html',\r\n" + 
			  		"	styleUrls: ['./"+composant+".component.scss']\r\n" + 
			  		"})\r\n" + 
			  		"export class "+uppercasefunct.capitalize(composant)+"Component implements AfterViewInit {\r\n" + 
			  		"	@ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;\r\n" + 
			  		"	@ViewChild(MatSort) sort: MatSort;\r\n" + 
			  		"	form;\r\n result:any\r\n" + 
			  		"	dataSource: MatTableDataSource<"+uppercasefunct.capitalize(composant)+">;\r\n" + 
			  		"   langue;\r\n"
			  		+ " task:any=[] \r\n"
			  		+ "status:any \n" + 
			  		"	constructor(private "+composant.toLowerCase()+"Service: "+uppercasefunct.capitalize(composant)+"Service, private dialogRef: MatDialog,\r\n" + 
			  		"		private route: ActivatedRoute,\r\n" + 
			  		"		private formbuild: FormBuilder,\r\n" + 
			  		"		private _snackBar: MatSnackBar, \r\n" + 
			  		"		private translate: TranslateService,\r\n"
			  		+"		private notification: NotificationService," + 
			  		"		private router: Router) {\r\n" + 
			  		"	}\r\n" + 
			  		"	displayedColumns: string[] = [");
			  	  
			  for(int i=0; i<ja.size(); i++) {
		   			JsonObject json = (JsonObject) ja.get(i);
			  			 
			  				 myWriter.write("'"+json.get("name").getAsString().toLowerCase()+"',");
			  			   
	 	   			    }
			  		   
			  		 myWriter.write("'action'];\r\n"); 
			  		 
			  		 myWriter.write("\n\r columnDefinitions = [\n\r");
			  		for(int i=0; i<ja.size(); i++) {
			   			JsonObject json = (JsonObject) ja.get(i);
			   			
				  			 myWriter.write("{ def: '"+json.get("name").getAsString().toLowerCase()+"', label: '"+json.get("name").getAsString().toLowerCase()+"', hide: false},");
				  		                 
				  		                
				  		   
	 	   			    }
				  		 
				  		myWriter.write("];\r\n"); 
				  		myWriter.write("formchamps:FormGroup = new FormGroup({\n\r");
				  		for(int i=0; i<ja.size(); i++) {
				   			JsonObject json = (JsonObject) ja.get(i);
				  			 myWriter.write(json.get("name").getAsString().toLowerCase()+": new FormControl(false),\r\n");
	 	   			    }
				  		myWriter.write("});\n\r");

				  		for(int i=0; i<ja.size(); i++) {
				   			JsonObject json = (JsonObject) ja.get(i);
				  			 myWriter.write(json.get("name").getAsString().toLowerCase()+"= this.formchamps.get('"+json.get("name").getAsString().toLowerCase()+"');\r\n");             
				  		                
	 	   			    }
				  		 
				  	
				  		  
				  		  
				  		myWriter.write(" getDisplayedColumns():string[] {\r\n" + 
				  				"    return this.columnDefinitions.filter(cd=>!cd.hide).map(cd=>cd.def);\r\n" + 
				  				"  }\n\r"); 
				  		
				  		myWriter.write("mesChamps(){");
				  		String ok = "";
				  		for(int i=0; i<ja.size(); i++) {
				   			JsonObject json = (JsonObject) ja.get(i);
				  			 myWriter.write("let o"+i+":Observable<boolean> = this."+json.get("name").getAsString().toLowerCase()+".valueChanges;\n\r");
				  		                
				  			 if(i!=0) ok+=",";
				  		     ok+= "o"+i;
				  		   
	 	   			    }
				  		myWriter.write("merge("+ok+").subscribe( v => { \n\r");
				  		for(int i=0; i<ja.size(); i++) {
				   			JsonObject json = (JsonObject) ja.get(i);
				  			 myWriter.write("this.columnDefinitions["+i+"].hide = this."+json.get("name").getAsString().toLowerCase()+".value;\n\r");
				  		                   
	 	   			    }
				  		myWriter.write("});\n\r");
				  		myWriter.write("}");

				  	

			  		 
			  		myWriter.write("\r\n" + 
			  		"	applyFilter(event: Event) {\r\n" + 
			  		"		const filterValue = (event.target as HTMLInputElement).value;\r\n" + 
			  		"		this.dataSource.filter = filterValue.trim().toLowerCase();\r\n" + 
			  		"	}\r\n" + 
			  		"\r\n" + 
			  		"	ngAfterViewInit() {\r\n"  
			  		+ "this.mesChamps()\n;" + 
			  		"		this.list"+uppercasefunct.capitalize(composant)+"();\r\n");
			  		for(int i=0; i<listform.size(); i++) {
			  			myWriter.write(""
			  					+ " this.verify"+uppercasefunct.capitalize(listform.get(i).getJfrmFormulaire())+"();\r\n");
			  			if(listform.get(i).getJfrmPrimaire()) {
			  			myWriter.write("if(!this."+listform.get(i).getJfrmFormulaire().toLowerCase()+"){\r\n" + 
			  					"      this.listTask(localStorage.getItem('profil'))\r\n" + 
			  					"    }\r\n" + 
			  					"");
			  			}
			  		}
			  		myWriter.write("	}\r\n" + 
			  		"	ngOnInit() {\r\n" + 
			  		"		}\r\n" + 
			  		"\r\n" + 
			  		"	list"+uppercasefunct.capitalize(composant)+"() {\r\n" + 
			  		"		this."+composant.toLowerCase()+"Service.get"+uppercasefunct.capitalize(composant)+"All(localStorage.getItem('id')).subscribe(data => {\r\n"
			  				+ "this.form = data" + 
			  				"\r\n"+
			  		"			if (this.form.statut) {\r\n" + 
			  		"				console.log(this.form);\r\n" + 
			  		"				this.dataSource = new MatTableDataSource<"+uppercasefunct.capitalize(composant)+">(this.form.data);\r\n" + 
			  		"				this.dataSource.paginator = this.paginator;\r\n" + 
			  		"				this.dataSource.sort = this.sort;\r\n" + 
			  		"			} else {\r\n" + 
			  		"				window.alert(this.form.description);\r\n" + 
			  		"			}\r\n" + 
			  		"		})\r\n" + 
			  		"	}\r\n" + 
			  		"\r\n" + 
			  		"\r\n");
			  		myWriter.write("listTask(poowner) {\r\n" + 
			  				"		this."+composant.toLowerCase()+"Service.getTask(poowner).subscribe(data => {\r\n" + 
			  				"this.form = data\r\n" + 
			  				"			if (this.form.statut) {\r\n" + 
			  				"				console.log(this.form);\r\n" + 
			  				"				this.dataSource = new MatTableDataSource<"+uppercasefunct.capitalize(composant)+">(this.form.data);\r\n" + 
			  				"				this.dataSource.paginator = this.paginator;\r\n" + 
			  				"				this.dataSource.sort = this.sort;\r\n" + 
			  				"			} else {\r\n" + 
			  				"				window.alert(this.form.description);\r\n" + 
			  				"			}\r\n" + 
			  				"		})\r\n" + 
			  				"	}");
			  		if(formInfos.getJfrmPrimaire()) {
			  		myWriter.write("openDialogAdd(): void {\r\n" + 
			  		"		const dialog = this.dialogRef.open(Add"+uppercasefunct.capitalize(composant)+"Component, {\r\n" + 
			  		"			width: '700px',\r\n"
			  		+ 			"data:this.status,\n"
			  		+ "			disableClose: true" + 
			  		"\r\n" + 
			  		"		}).afterClosed().subscribe(result => {\r\n" + 
			  		"			//location.reload();\r\n" + 
			  		"			this.list"+uppercasefunct.capitalize(composant)+"();\r\n" + 
			  		"		});\r\n" + 
			  		"\r\n" + 
			  		"	}\r\n");
			  		}else {
			  			myWriter.write("executer(): void {\r\n" + 
						  		"		const dialog = this.dialogRef.open(Add"+uppercasefunct.capitalize(composant)+"Component, {\r\n" + 
						  		"			width: '700px',\r\n" + 
						  		"\r\n" + 
						  		"		}).afterClosed().subscribe(result => {\r\n" + 
						  		"			//location.reload();\r\n" + 
						  		"			this.list"+uppercasefunct.capitalize(composant)+"();\r\n" + 
						  		"		});\r\n" + 
						  		"\r\n" + 
						  		"	}\r\n");
			  		}
			  		
			  		
			  		myWriter.write( "openDialogView(username) {\r\n" + 
			  		"		console.log(username);\r\n" + 
			  		"		const dialog1 = this.dialogRef.open(View"+uppercasefunct.capitalize(composant)+"Component, {\r\n" + 
			  		"			width: '700px',\r\n" + 
			  		"			data: username\r\n,"
			  		+ "			disableClose: true\n" + 
			  		"		}).afterClosed().subscribe(result => {\r\n" + 
			  		"			//location.reload();\r\n" + 
			  		"			this.list"+uppercasefunct.capitalize(composant)+"();\r\n" + 
			  		"		});\r\n" + 
			  		"	}\n"
			  		+ "");
			  		for(int i=0; i<ja.size(); i++) {
			   			JsonObject json = (JsonObject) ja.get(i);
			  			   if(json.get("code").getAsString().equals("Document")) {
			  		myWriter.write("downloadImage(data){\r\n" + 
			  		"		this."+composant.toLowerCase()+"Service.download(data.id).subscribe(resp=>{\r\n" + 
			  		"		this.saveFile(resp.body, \"fichier:\"+data.id,data.extention);\r\n" + 
			  		"\r\n" + 
			  		"		});\r\n" + 
			  		"\r\n" + 
			  		"	  }\r\n" + 
			  		"	  saveFile(data: any,  filename?: string, extention?:any) {\r\n" + 
			  		"      let blob = new Blob([data], {type:extention});\r\n" + 
			  		"		fileSaver.saveAs(blob,  filename);\r\n" + 
			  		"	  }");
			  		break;
			  			   }
			  			 }
			  		 for(int i=0; i<listform.size(); i++) {
			  			 myWriter.write("\n\r"
			  			 		+ ""+listform.get(i).getJfrmFormulaire().toLowerCase()+"=false \r\n"
			  			 		+ " verify"+uppercasefunct.capitalize(listform.get(i).getJfrmFormulaire())+"(){\r\n" + 
			  			 		"      this."+composant.toLowerCase()+"Service.getAllTask().subscribe(data=>{\r\n" + 
			  			 		"        this.task=data\r\n" + 
			  			 		"    console.log(this.task.data)\r\n" + 
			  			 		"    for(let i=0; i<this.task.data.length; i++){\r\n" + 
			  			 		"     if( this.task.data[i].poOwner.proId==localStorage.getItem(\"profil\") && this.task.data[i].tskFormName=='"+uppercasefunct.capitalize(listform.get(i).getJfrmFormulaire())+"'){\r\n" + 
			  			 		"     this."+listform.get(i).getJfrmFormulaire().toLowerCase()+"=true\r\n");
			  			 		if(listform.get(i).getJfrmPrimaire()) {
			  			 		 myWriter.write("this.list"+uppercasefunct.capitalize(composant)+"();\r\n");
			  			 		}
			  			 	      myWriter.write(" this."+composant.toLowerCase()+"Service.getStatus(this.task.data[i].tskId).subscribe((data:any)=>{\r\n" + 
			  			 			"      this.status=data.data\r\n" + 
			  			 			"     })\r\n");
			  			 		
			  			 	 myWriter.write(" break\r\n" + 
			  			 		"     }\r\n" + 
			  			 		"   }\r\n" + 
			  			 		"      })\r\n" + 
			  			 		"   }\n");
			  			 if(!listform.get(i).getJfrmPrimaire()) {
			  			myWriter.write( "openDialog"+uppercasefunct.capitalize(listform.get(i).getJfrmFormulaire())+"(data) {\r\n" + 
						  		"		const dialog1 = this.dialogRef.open(Add"+uppercasefunct.capitalize(listform.get(i).getJfrmFormulaire())+"Component, {\r\n" + 
						  		"			width: '700px',\r\n" + 
						  		"			data:{data:data,status:this.status}\r\n" + 
						  		"		}).afterClosed().subscribe(result => {\r\n" + 
						  		"			//location.reload();\r\n" + 
						  		"			this.list"+uppercasefunct.capitalize(composant)+"();\r\n" + 
						  		"		});\r\n" + 
						  		"	}\n"
						  		+ "");
			  		 }
			  		 }
			  		myWriter.write("}");
			  myWriter.close();
			  System.out.println("Successfully wrote to the filegggg."); }
			  catch (IOException e) {
				  System.out.println("An error occurred.");
				  e.printStackTrace(); 
				}
	    }
	 
	 public void updateComposantFormTs(String nomprojet,String composant)throws IOException  {
		 StringProcess uppercasefunct = new StringProcess();
		 FileWriter myWriter = new FileWriter(
					this.repo+"/"+nomprojet+"/src/app/"+composant.toLowerCase()+"/"+composant.toLowerCase()+".component.ts");
		 myWriter.write("import { Component, OnInit } from '@angular/core';\r\n" + 
		 		"import { MatIconRegistry } from '@angular/material';\r\n" + 
		 		"import { DomSanitizer } from '@angular/platform-browser';\r\n" + 
		 		"\r\n" + 
		 		"@Component({\r\n" + 
		 		"  selector: 'app-"+composant.toLowerCase()+"',\r\n" + 
		 		"  template: `\r\n" + 
		 		"  <app-sidenav></app-sidenav>\r\n" + 
		 		"  `,\r\n" + 
		 		"  styles: []\r\n" + 
		 		"})\r\n" + 
		 		"export class "+uppercasefunct.capitalize(composant)+"Component implements OnInit {\r\n" + 
		 		"\r\n" + 
		 		"  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {\r\n" + 
		 		"    iconRegistry.addSvgIconSet(sanitizer.bypassSecurityTrustResourceUrl('assets/avatars.svg'));\r\n" + 
		 		"   }\r\n" + 
		 		"\r\n" + 
		 		"  ngOnInit() {\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"}");
		 myWriter.close();
	 }
		public void updateModel(String taskName,String nomProjet,JsonArray ja,String workflow) {
			StringProcess uppercasefunct = new StringProcess();
			 String rep = this.repo ; 
			  try {
			 
			  
			  FileWriter myWriter = new FileWriter(
			  this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/model/"+taskName.toLowerCase()+
			  ".ts");
			  myWriter.write("export class "+uppercasefunct.capitalize(taskName)+" {\n");
			  for(int i=0; i<ja.size(); i++) {
		   			JsonObject json = (JsonObject) ja.get(i);
					  myWriter.write(json.get("name").getAsString().toLowerCase()+":any;\n");
 	   			}
 	   			myWriter.write("processinstId:any;\n"
 	   					+ "taskInstId:any;\n"
 	   					+ "status:any;\n}");
			  myWriter.close();
			  System.out.println("Successfully wrote to the file."); }
			  catch (IOException e) {
				  System.out.println("An error occurred.");
				  e.printStackTrace(); 
				}
		}
		 public void updateRoutingForm(String nomprojet,String composant,List<JbpmFormInfos> forms) throws IOException {
			 StringProcess uppercasefunct = new StringProcess();
			 FileWriter myWriter = new FileWriter(
						this.repo+"/"+nomprojet+"/src/app/"+composant.toLowerCase()+"/"+composant.toLowerCase()+"-routing.module.ts");
			 myWriter.write("import { NgModule } from '@angular/core';\r\n" + 
			 		"import { Routes, RouterModule } from '@angular/router';\r\n"
			 		+ "import { "+uppercasefunct.capitalize(composant.toLowerCase())+"Component } from './"+composant.toLowerCase()+".component';\r\n"); 
				for (int j = 0; j < forms.size(); j++)
		  		 {
					if(forms.get(j).getJfrmPrimaire()) {
					 myWriter.write("import { "+uppercasefunct.capitalize(forms.get(j).getJfrmFormulaire())+"Component } from './components/"+forms.get(j).getJfrmFormulaire().toLowerCase()+"/"+forms.get(j).getJfrmFormulaire().toLowerCase()+".component';\r\n" ); 
					 break;
					}
		  		 }
				myWriter.write("const routes: Routes = [\r\n" + 
			 		"  {\r\n" + 
			 		"    path: '', component: "+uppercasefunct.capitalize(composant)+"Component,\r\n" + 
			 		"    children: [\r\n");
				for (int i = 0; i < forms.size(); i++)
		  		 {
					if(forms.get(i).getJfrmPrimaire()) {
						myWriter.write("{ path: '', component:"+uppercasefunct.capitalize(forms.get(i).getJfrmFormulaire())+"Component },\r\n");
					}
					
		  		 }
				myWriter.write("    ],\r\n" + 
			 		"\r\n" + 
			 		"  },\r\n" + 
			 		"\r\n" + 
			 		"];\r\n" + 
			 		"\r\n" + 
			 		"@NgModule({\r\n" + 
			 		"  imports: [RouterModule.forChild(routes)],\r\n" + 
			 		"  exports: [RouterModule]\r\n" + 
			 		"})\r\n" + 
			 		"export class "+uppercasefunct.capitalize(composant)+"RoutingModule { }");
				myWriter.close();
		 }
		 
		 
		 public void updateModule(String nomprojet,String composant,List<JbpmFormInfos> forms) throws IOException {
			 StringProcess uppercasefunct = new StringProcess();
			 FileWriter myWriter = new FileWriter(
						this.repo+"/"+nomprojet+"/src/app/"+composant.toLowerCase()+"/"+composant.toLowerCase()+".module.ts");
			 myWriter.write("import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';\r\n" + 
			 		"import { CommonModule } from '@angular/common';\r\n" + 
			 		"\r\n" + 
			 		"import { MaterialModule } from '../shared/material.module';\r\n" + 
			 		"import { FormsModule, ReactiveFormsModule } from '@angular/forms';\r\n" + 
			 		"import { HttpClientModule } from '@angular/common/http';\r\n" + 
			 		"import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';\r\n" + 
			 		"import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';\r\n" + 
			 		"import { DndModule } from 'ngx-drag-drop';\r\n" + 
			 		"import { FlexLayoutModule } from '@angular/flex-layout';\r\n" 
			 		+ "import { "+uppercasefunct.capitalize(composant.toLowerCase())+"Component } from './"+composant.toLowerCase()+".component';\r\n"
			 		+ "import { "+uppercasefunct.capitalize(composant.toLowerCase())+"RoutingModule } from './"+composant.toLowerCase()+"-routing.module';\r\n" ); 
			 		
				for (int j = 0; j < forms.size(); j++)
		  		 {
					if(forms.get(j).getJfrmPrimaire()) {
						myWriter.write("import { "+uppercasefunct.capitalize(forms.get(j).getJfrmFormulaire())+"Component } from './components/"+forms.get(j).getJfrmFormulaire().toLowerCase()+"/"+forms.get(j).getJfrmFormulaire().toLowerCase()+".component';\r\n" );
					}
					 myWriter.write("import {Add"+uppercasefunct.capitalize(forms.get(j).getJfrmFormulaire())+"Component } from './components/add-"+forms.get(j).getJfrmFormulaire().toLowerCase()+"/add-"+forms.get(j).getJfrmFormulaire().toLowerCase()+".component';\r\n" );
					 myWriter.write("import {View"+uppercasefunct.capitalize(forms.get(j).getJfrmFormulaire())+"Component } from './components/view-"+forms.get(j).getJfrmFormulaire().toLowerCase()+"/view-"+forms.get(j).getJfrmFormulaire().toLowerCase()+".component';\r\n" ); 
		  		 }
				
				
				myWriter.write("@NgModule({ \n"
						+ "declarations: ["+uppercasefunct.capitalize(composant)+"Component,");
				for (int i = 0; i < forms.size(); i++)
		  		 {
					if(forms.get(i).getJfrmPrimaire()) {
						myWriter.write(uppercasefunct.capitalize(forms.get(i).getJfrmFormulaire())+"Component,");
					}
					myWriter.write("Add"+uppercasefunct.capitalize(forms.get(i).getJfrmFormulaire())+"Component,View"+uppercasefunct.capitalize(forms.get(i).getJfrmFormulaire())+"Component,");
					
		  		 }
				myWriter.write(" ],\n "
						+ "imports: [\r\n" + 
						"    CommonModule,\r\n" + 
						"    "+uppercasefunct.capitalize(composant)+"RoutingModule,\r\n" + 
						"    MaterialModule,\r\n" + 
						"    FormsModule,\r\n" + 
						"    ReactiveFormsModule,\r\n" + 
						"    FlexLayoutModule,\r\n" + 
						"    HttpClientModule,\r\n" + 
						"    SharedcomponentModule,\r\n" + 
						"    SweetAlert2Module.forRoot(),\r\n" + 
						"    DndModule\r\n" + 
						"  ],schemas: [ CUSTOM_ELEMENTS_SCHEMA ]\r\n" + 
						"})\r\n" + 
						"export class "+uppercasefunct.capitalize(composant)+"Module { }");
				myWriter.close();
		 }
		 
		 public void updateService(String taskName,String nomProjet,JsonArray ja,String workflow) {
			 //String rep = this.repo ;
			 StringProcess uppercasefunct = new StringProcess();
			  try {
			 
			  FileWriter myWriter = new FileWriter(
			  this.repo+"/"+nomProjet+"/src/app/"+workflow.toLowerCase()+"/service/"+taskName.toLowerCase()+
			  ".service.ts");
			  myWriter.write("import { Injectable } from '@angular/core';\r\n" + 
			  		"import { HttpClient,HttpHeaders} from '@angular/common/http';\r\n"
			  		+ "import { environment } from 'src/environments/environment';\r\n"
			  		+ "import { "+uppercasefunct.capitalize(taskName)+" } from '../model/"+taskName.toLowerCase()+"';\n" + 
			  		"@Injectable({\r\n" + 
			  		"  providedIn: 'root'\r\n" + 
			  		"})\r\n"
			  		+ 
			  		"export class "+uppercasefunct.capitalize(taskName)+"Service {\r\n" + 
			  		 "api = environment.apii;" + 
			  		"\r\n" + 
			  		"  constructor(private http:HttpClient) {"+
			  		"  }\r\n" + 
			  		"  \r\n" + 
			  		"  create"+uppercasefunct.capitalize(taskName)+"(data){\r\n" + 
			  		"       return this.http.post(this.api+\""+taskName.toLowerCase()+"/create\",data)\r\n" + 
			  		"  }\r\n" + 
			  		"  get"+uppercasefunct.capitalize(taskName)+"All(owner){\r\n" + 
			  		"        return this.http.get(this.api+\""+taskName.toLowerCase()+"/list/\"+owner)\r\n" + 
			  		"  } \r\n" + 
			  		"  delete"+uppercasefunct.capitalize(taskName)+"(data){\r\n" + 
			  		"    return this.http.post(this.api+\""+taskName.toLowerCase()+"/delete\",data)\r\n" + 
			  		"}\r\n"+
			  		"  getTask(poowner){\r\n" + 
			  		"    return this.http.get(this.api+\""+taskName.toLowerCase()+"/task/\"+poowner)\r\n"
			  				+ " }\n"
			  		+ " getStatus(taskId){\r\n" + 
			  		"  return this.http.get(this.api+\"transition/statusAfterExecution/\"+taskId)\r\n" + 
			  		" }\n"
			  		+ "updateTask"+uppercasefunct.capitalize(taskName)+"(id,status){\r\n" + 
			  		"  return this.http.get(this.api+\""+taskName.toLowerCase()+"/status/\"+id+\"/\"+status)\r\n" + 
			  		"}\n" + 
			  		""+
			  		
			  		"\n");
			  for(int i=0; i<ja.size(); i++) {
		   			JsonObject json = (JsonObject) ja.get(i);
				 
				 if (json.get("code").getAsString().equals("Document")){
					 myWriter.write(" \r\n" + 
					 		"  add"+uppercasefunct.capitalize(taskName)+"(file:any, "+taskName.toLowerCase()+":"+uppercasefunct.capitalize(taskName)+"){\r\n" + 
					 		"    const formData = new FormData();\r\n" + 
					 		"    formData.append('"+taskName.toLowerCase()+"', file);\r\n" +
					 		"    formData.append('"+taskName.toLowerCase()+"', JSON.stringify("+taskName.toLowerCase()+"));\r\n" + 
					 		"    return this.http.post<any>(this.api + '"+taskName.toLowerCase()+"/createfile', formData);\r\n" + 
					 		"  }\r\n"
					 		+ " download(id) {\r\n" + 
					 		"    let headers = new HttpHeaders();\r\n" + 
					 		"    //headers = headers.append('Accept', 'image/png');\r\n" + 
					 		"    return this.http.get(this.api + '"+taskName.toLowerCase()+"/downloadFile/'+id,\r\n" + 
					 		"    {\r\n" + 
					 		"      headers: headers,\r\n" + 
					 		"      observe: 'response',\r\n" + 
					 		"      responseType:'arraybuffer'});\r\n" + 
					 		"  }");
                   }

			    } 
			  myWriter.write("getAllTask(){\r\n" + 
			  		"  return this.http.get(this.api+\"task/list\")\r\n" + 
			  		"}");
			  myWriter.write("}\r\n");
			  myWriter.close();
			  System.out.println("Successfully wrote to the file."); }
			  catch (IOException e) {
				  System.out.println("An error occurred.");
				  e.printStackTrace(); 
				}
		}
		 
		 
	 //backend++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 
	 public void generationFileModelBack(String composant,JsonArray ja) throws IOException {
		 StringProcess uppercasefunct = new StringProcess();
 			File packentity= new File(this.repo+"/"+this.dossierPackage+"entity");
 	            packentity.mkdirs();
 	            File controllerfiles = new File(packentity, "" + uppercasefunct.capitalize(composant) + ".java");
 	            controllerfiles.createNewFile();
 	            String filejava = this.packageKey + " "+ this.packagename+".entity;\n" +
 	                    "\n" +
 	                    "import java.io.Serializable;\n" +
						"import java.math.BigInteger;\n" +
						"import javax.persistence.Basic;\n" +
						"import javax.persistence.Column;\n" +
						"import javax.persistence.Entity;\n" +
						"import javax.persistence.GeneratedValue;\n" +
						"import javax.persistence.GenerationType;\n" +
						"import javax.persistence.Id;\n" +
						"import javax.persistence.Table;\n" +
						"import javax.validation.constraints.NotNull;\n"
						+ "import javax.persistence.JoinColumn;\r\n" + 
						"import javax.persistence.ManyToOne;\n" +
 	                    "import javax.persistence.Temporal;\r\n" + 
 	                    "import javax.persistence.TemporalType;\n"
 	                    +"import sn.gainde2000.pi.core.*;\n"
 	                    + "import java.util.Date;\n" +
 	                    "@Entity\n" +
 	                    "@Table(name = \"" + uppercasefunct.capitalize(composant) + "\")\n" +
						"public class "+uppercasefunct.capitalize(composant)+" implements Serializable {\n"+
 	                    "    private static final long serialVersionUID = 1L;\n";

 	            filejava = filejava+ "@Id\n" +
 	                    "@GeneratedValue(strategy = GenerationType.AUTO)\n" +
 	                    "@Basic(optional = false)\n" +
 	                    "@NotNull\n" +
 	                    "@Column(name = \"id\")\n" +
 	                    "private Long id;\n"+
 	                    " private Long status; \n"+
 	                   " private Long poOwner ;\n "+
 	                  " private Long owner ;\n "
 	                   + "private Long idLink; \n"
 	                    ;
 	            /*generation declaration des attributs*/
 	           for(int i=0; i<ja.size(); i++) {
 		   			JsonObject json = (JsonObject) ja.get(i);
 	                           
 	                         if (json.get("code").getAsString().equals("TextBox")|| 
 	                        		json.get("code").getAsString().equals("TextArea")||
 	                        		json.get("code").getAsString().equals("CheckBox")||
 	                        		json.get("code").getAsString().equals("RadioGroup")||
 	                        		json.get("code").getAsString().equals("ListBox")){
 	                            	filejava = filejava+ "@Column(name = \""+json.get("name").getAsString().toLowerCase()+"\")\n";
 	                                filejava = filejava+ "private String "+json.get("name").getAsString().toLowerCase()+";\n" + "";
 	                            }
 	                            else if (json.get("code").getAsString().equals("Document")) {
 	                            	filejava = filejava+ "@Column(name = \""+json.get("name").getAsString().toLowerCase()+"\", columnDefinition = \"MEDIUMBLOB\")\n";
 	                                filejava = filejava+ "private byte[] "+json.get("name").getAsString().toLowerCase()+";\n" + "";
 	                                filejava = filejava+ "@Column(name = \"extention\")\n";
 	                                filejava = filejava+ "private String extention ;\n" + "";
 	                            }
 	                          
 	                            else if (json.get("code").getAsString().equals("DatePicker")){
 	                            	 filejava = filejava+ "@Column(name = \""+json.get("name").getAsString().toLowerCase()+"\")\n";
 	                                filejava = filejava+ "@Temporal(TemporalType.TIMESTAMP)\n" +
 	                                        "private Date "+json.get("name").getAsString().toLowerCase()+";";
 	                            }
 	                            else{
 	                                filejava = filejava+ " ";
 	                            }

 	                    filejava = filejava+ "\n" + "";

 	                }
 	          
 	            /*generation declaration des getters setters*/
 	            String filejavagettersetter = "";
 	           for(int j=0; j<ja.size(); j++) {
		   			JsonObject json = (JsonObject) ja.get(j);
	                           
 	                
 	                		if (json.get("code").getAsString().equals("TextBox")|| 
 	                        		json.get("code").getAsString().equals("TextArea")||
 	                        		json.get("code").getAsString().equals("CheckBox")||
 	                        		json.get("code").getAsString().equals("RadioGroup")||
 	                        		json.get("code").getAsString().equals("ListBox")){
 	                		filejavagettersetter = filejavagettersetter+ "public String get"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"() {\n" +
 	                        "        return "+json.get("name").getAsString().toLowerCase()+";\n" +
 	                        "    }\n" +
 	                        "    public void set"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"(String "+json.get("name").getAsString().toLowerCase()+") {\n" +
 	                        "        this."+json.get("name").getAsString().toLowerCase()+" = "+json.get("name").getAsString().toLowerCase()+";\n" +
 	                        "    }   \n" + "";
 	                		}
 	                		
 	                	
 	                	 if(json.get("code").getAsString().equals("DatePicker")){
	 	                		filejavagettersetter = filejavagettersetter+ "public Date get"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"() {\n" +
	 	                        "        return "+json.get("name").getAsString().toLowerCase()+";\n" +
	 	                        "    }\n" +
	 	                        "    public void set"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"(Date "+json.get("name").getAsString().toLowerCase()+") {\n" +
	 	                        "        this."+json.get("name").getAsString().toLowerCase()+" = "+json.get("name").getAsString().toLowerCase()+";\n" +
	 	                        "    }   \n" + "";
	 	                		}
 	                		if(json.get("code").getAsString().equals("Document")){
	 	                		filejavagettersetter = filejavagettersetter+ "public byte[] get"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"() {\n" +
	 	                        "        return "+json.get("name").getAsString().toLowerCase()+";\n" +
	 	                        "    }\n" +
	 	                        "    public void set"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"(byte[] "+json.get("name").getAsString().toLowerCase()+") {\n" +
	 	                        "        this."+json.get("name").getAsString().toLowerCase()+" = "+json.get("name").getAsString().toLowerCase()+";\n" +
	 	                        "    }   \n" + "";
	 	                		filejavagettersetter = filejavagettersetter+"public String getExtention() {\r\n" + 
	 	           	         		"		return extention;\r\n" + 
	 	           	         		"	}\r\n" + 
	 	           	         		"	public void setExtention(String extention) {\r\n" + 
	 	           	         		"		this.extention = extention;\r\n" + 
	 	           	         		"	}  \n";
	 	   	 	              
	 	                		}
 	                		
 	                			
 	                    filejavagettersetter = filejavagettersetter+ "\n" + "";

 	            }
 	          filejavagettersetter = filejavagettersetter+ " ";filejavagettersetter = filejavagettersetter+ "public Long getId() {\r\n" + 
	 	            		"		return id;\r\n" + 
	            		"	}\r\n" + 
	            		"	public void setId(Long id) {\r\n" + 
	            		"		this.id = id;\r\n" + 
	            		"	}   \r\n" + 
	            		"\n";
       		
       		filejavagettersetter = filejavagettersetter+ "public Long getStatus() {\r\n" +            
 	 	            		"		return status;\r\n" + 
 	 	            		"	}\r\n" + 
 	 	            		"	public void setStatus(Long status) {\r\n" + 
 	 	            		"		this.status = status;\r\n" + 
 	 	            		"	}   \r\n" + 
 	 	            		"\n";
       		filejavagettersetter = filejavagettersetter+ "public Long getPoOwner() {\r\n" +            
 	 	            		"		return poOwner;\r\n" + 
 	 	            		"	}\r\n" + 
 	 	            		"	public void setPoOwner(Long poOwner) {\r\n" + 
 	 	            		"		this.poOwner = poOwner;\r\n" + 
 	 	            		"	}   \r\n" + 
 	 	            		"\n";
       		filejavagettersetter = filejavagettersetter+ "public Long getOwner() {\r\n" +            
	            		"		return owner;\r\n" + 
	            		"	}\r\n" + 
	            		"	public void setOwner(Long owner) {\r\n" + 
	            		"		this.owner = owner;\r\n" + 
	            		"	}   \r\n" + 
	            		"\n";
       		filejavagettersetter = filejavagettersetter+ "public Long getIdLink() {\r\n" +            
	            		"		return idLink;\r\n" + 
	            		"	}\r\n" + 
	            		"	public void setIdLink(Long idLink) {\r\n" + 
	            		"		this.idLink = idLink;\r\n" + 
	            		"	}   \r\n" + 
	            		"\n";
       
 	            filejava = filejava+filejavagettersetter+ "\n" + "}";
 	            FileWriter myWriterentity = new FileWriter(packentity+"\\" + composant + ".java");
 	            myWriterentity.write("\n" +filejava);
 	            myWriterentity.close();
           
	}
	 
	 //creation du fichier repository
	 public void generationFileRepositoryBack(String nomrepo) throws IOException {
			StringProcess uppercasefunct = new StringProcess();
	            File packrepo = new File(this.repo+"/"+this.dossierPackage+"repository");
	            packrepo.mkdirs();
	            File repofiles = new File(packrepo, ""+uppercasefunct.capitalize(nomrepo) +"Repository.java");
	            repofiles.createNewFile();
	            String repositoryfile =  this.packageKey+ " "+this.packagename+".repository;\n" +
	                    "\n" +
	                    this.importKey+" "+this.packagename+".entity."+uppercasefunct.capitalize(nomrepo)+";\n" +
	                    "import org.springframework.data.jpa.repository.JpaRepository;\n" +
	                    "import org.springframework.stereotype.Repository;\n"
	                    + "import org.springframework.data.jpa.repository.Query;\n" + 
	                    "import org.springframework.data.repository.query.Param;\n" +
	                    "\n" +
	                    "@Repository\n" +
	                    "public interface "+uppercasefunct.capitalize(nomrepo)+"Repository extends JpaRepository<"+uppercasefunct.capitalize(nomrepo)+", Long> {\n"
	                    		+ "	 @Query(\"SELECT s from "+uppercasefunct.capitalize(nomrepo)+" s WHERE s.status IN (SELECT t.tskStatusId from Task t WHERE t.poOwner.proId=:poOwner )\")\r\n" + 
	                    		"	    Iterable<"+uppercasefunct.capitalize(nomrepo)+"> list"+uppercasefunct.capitalize(nomrepo)+"(@Param(\"poOwner\") Long poOwner);\r\n" + 
	                    		"	 \r\n" + 
	                    		"	 @Query(\"SELECT s from "+uppercasefunct.capitalize(nomrepo)+" s WHERE s.owner =:owner \")\r\n" + 
	                    		"	    Iterable<"+uppercasefunct.capitalize(nomrepo)+"> list"+uppercasefunct.capitalize(nomrepo)+"ById(@Param(\"owner\") Long owner);\n"
	                    		
	                    	  + " @Query(\"SELECT s from "+uppercasefunct.capitalize(nomrepo)+" s WHERE s.id =:id \")\r\n" + 
	                    	  "	   "+uppercasefunct.capitalize(nomrepo)+" getOne"+uppercasefunct.capitalize(nomrepo)+"(@Param(\"id\") Long id);" +
	                    "\n" +
	                    "}";

	            FileWriter myWriterrepo = new FileWriter(packrepo+"\\" + nomrepo + "Repository.java");
	            myWriterrepo.write("\n" +repositoryfile);
	            myWriterrepo.close();
		}
	 
	 /*+++++++++++++++++++ Generation des fichiers service+++++++++++++++++++++++++*/
		
		public void generationFileServiceBack(String nomserviceimpl) throws IOException {
			 //String nomserviceimpl = form.getFrmNom();
			StringProcess uppercasefunct = new StringProcess();
	            File packservice = new File(this.repo+"/"+this.dossierPackage+"service");
	            packservice.mkdirs();
	            
	            File serviceimplfiles = new File(packservice, "I"+uppercasefunct.capitalize(nomserviceimpl)+"Service.java");
	            serviceimplfiles.createNewFile();
	            String servicefileimpl = this.packageKey+" "+ this.packagename+".service;\n" +
	                    this.importKey+" "+this.packagename+".entity."+uppercasefunct.capitalize(nomserviceimpl)+";\n" +
	                    "import java.util.List;\n"
	                    + "import java.util.Optional;\n" +
	                    "\n" +
	                    "public interface I"+uppercasefunct.capitalize(nomserviceimpl)+"Service {\n" +
	                    "\n"
	                    + "Optional<"+uppercasefunct.capitalize(nomserviceimpl)+"> findById(Long id);\n" +
	                    "List<"+uppercasefunct.capitalize(nomserviceimpl)+"> findAll();\n" +
	                     uppercasefunct.capitalize(nomserviceimpl) +" save("+uppercasefunct.capitalize(nomserviceimpl)+" "+nomserviceimpl.toLowerCase()+");\n"+
	                     "void delete("+uppercasefunct.capitalize(nomserviceimpl)+" "+nomserviceimpl.toLowerCase()+");\n"
	                     + "Iterable<"+uppercasefunct.capitalize(nomserviceimpl)+"> list"+uppercasefunct.capitalize(nomserviceimpl)+"(Long poOwner);\r\n" + 
	                     "Iterable<"+uppercasefunct.capitalize(nomserviceimpl)+"> list"+uppercasefunct.capitalize(nomserviceimpl)+"ById(Long owner);\n"
	                     + ""+uppercasefunct.capitalize(nomserviceimpl)+" getOne"+uppercasefunct.capitalize(nomserviceimpl)+"(Long id);"+
	                    "}";

	            FileWriter myWriteservicesimpl = new FileWriter(packservice+"/I"+uppercasefunct.capitalize(nomserviceimpl)+"Service.java");
	            myWriteservicesimpl.write("\n" +servicefileimpl);
	            myWriteservicesimpl.close();

	           File packservi =  new File(this.repo+"/"+this.dossierPackage+"service/impl");
			   packservi.mkdirs();
	            File servicefiles = new File(packservi,uppercasefunct.capitalize(nomserviceimpl)+"ServiceImpl.java");
	            servicefiles.createNewFile();
	            String servicefile = this.packageKey+" "+ this.packagename+".service.impl;\n" +
	                    "\n" +
	                    this.importKey+" "+this.packagename+".entity."+uppercasefunct.capitalize(nomserviceimpl)+";\n" +
	                    this.importKey+" "+this.packagename+".service.I"+uppercasefunct.capitalize(nomserviceimpl)+"Service;\n" +
	                    "import org.springframework.beans.factory.annotation.Autowired;\n" +
	                    "import org.springframework.stereotype.Service;\n" +
	                    this.importKey+" "+this.packagename+".repository."+uppercasefunct.capitalize(nomserviceimpl)+"Repository;"+
	                    "\n" +
	                    "import java.util.List;\n"
	                    + "import java.util.Optional;\n" +
	                    "\n" +
	                    "@Service\n" +
	                    "public class "+uppercasefunct.capitalize(nomserviceimpl)+"ServiceImpl implements I"+uppercasefunct.capitalize(nomserviceimpl)+"Service {\n" +
	                    "\n" +
	                    "    @Autowired\n" +
	                    "    private "+uppercasefunct.capitalize(nomserviceimpl)+"Repository "+nomserviceimpl.toLowerCase()+"Repository;\n" +
	                    "\n" +
	                    "    @Override\n" +
	                    "    public List<"+uppercasefunct.capitalize(nomserviceimpl)+"> findAll() {\n" +
	                    "\n" +
	                    "        return "+nomserviceimpl.toLowerCase()+"Repository.findAll();\n" +
	                    "    }\n" +
	                    "    @Override\n" +
	                    "    public "+uppercasefunct.capitalize(nomserviceimpl)+" save("+uppercasefunct.capitalize(nomserviceimpl)+" "+nomserviceimpl.toLowerCase()+") {\n" +
	                    "\n" +
	                    "        return "+nomserviceimpl.toLowerCase()+"Repository.save("+nomserviceimpl.toLowerCase()+");\n" +
	                    "    }\n" +
	                    "    @Override\n" +
	                    "    public void delete("+uppercasefunct.capitalize(nomserviceimpl)+" "+nomserviceimpl.toLowerCase()+") {\n" +
	                    "\n" +
	                    "  "+nomserviceimpl.toLowerCase()+"Repository.delete("+nomserviceimpl.toLowerCase()+");\n" +
	                    "    }\n"
	                    + "	@Override\r\n" + 
	                    "	public Optional<"+uppercasefunct.capitalize(nomserviceimpl)+"> findById(Long id) {\r\n" + 
	                    "		return "+nomserviceimpl.toLowerCase()+"Repository.findById(id);\r\n" + 
	                    "	}\n"
	                    + "@Override\r\n" + 
	                    "	public "+uppercasefunct.capitalize(nomserviceimpl)+" getOne"+uppercasefunct.capitalize(nomserviceimpl)+"(Long id) {\r\n" + 
	                    "		// TODO Auto-generated method stub\r\n" + 
	                    "		return "+nomserviceimpl.toLowerCase()+"Repository.getOne"+uppercasefunct.capitalize(nomserviceimpl)+"(id);\r\n" + 
	                    "	}" +
	                    "\n"
	                    + "@Override\n" + 
	                    "public Iterable<"+uppercasefunct.capitalize(nomserviceimpl)+"> list"+uppercasefunct.capitalize(nomserviceimpl)+"(Long poOwner) {\n" + 
	                    "    return "+nomserviceimpl.toLowerCase()+"Repository.list"+uppercasefunct.capitalize(nomserviceimpl)+"(poOwner);\n" + 
	                    "}\n" + 
	                    "@Override\n" + 
	                    "public Iterable<"+uppercasefunct.capitalize(nomserviceimpl)+"> list"+uppercasefunct.capitalize(nomserviceimpl)+"ById(Long owner) {\n" + 
	                    "    return "+nomserviceimpl.toLowerCase()+"Repository.list"+uppercasefunct.capitalize(nomserviceimpl)+"ById(owner);\n" + 
	                    "}"+
	                    "}";

	            FileWriter myWriteservices = new FileWriter(packservi+"/"+uppercasefunct.capitalize(nomserviceimpl)+"ServiceImpl.java");
	            myWriteservices.write("\n" +servicefile);
	            myWriteservices.close();

		}
		 /*+++++++++++++++++++ Fin generation des fichiers service+++++++++++++++++++++++++*/
  /*++++++++++++++++++++++++generation controlleur++++++++++++++++++++++++++++++++++*/
		public void generationFileControllerBack(String nomcontroller,JsonArray ja) throws IOException {
			StringProcess uppercasefunct = new StringProcess();
	            File packcontroller = new File(this.repo+"/"+this.dossierPackage+"controller");
	            packcontroller.mkdirs();
	            File cntrlfiles = new File(packcontroller, ""+uppercasefunct.capitalize(nomcontroller)+"Controller.java");
	            cntrlfiles.createNewFile();

	            String controllerfile  =  this.packageKey+" "+this.packagename+".controller;\n" +
	                    "\n" +
	                    this.importKey+" "+this.packagename+".entity."+uppercasefunct.capitalize(nomcontroller)+";\n" +
	                    this.importKey+" "+this.packagename+".service.I"+uppercasefunct.capitalize(nomcontroller)+"Service;\n" +
	                    "import java.util.Optional;\n" +
	                    "\n" +
	                    "import org.springframework.beans.factory.annotation.Autowired;\n" +
	                    "import org.springframework.web.bind.annotation.CrossOrigin;\n" +
	                    "import org.springframework.web.bind.annotation.GetMapping;\n" +
	                    "import org.springframework.web.bind.annotation.PathVariable;\n" +
	                    "import org.springframework.web.bind.annotation.PostMapping;\n" +
	                    "import org.springframework.web.bind.annotation.RequestBody;\n" +
	                    "import org.springframework.web.bind.annotation.RestController;\n" +
	                    "import javax.validation.constraints.NotNull;\n"
	                    + "import javax.servlet.http.HttpServletRequest;\n"
	                    + "import org.springframework.web.bind.annotation.RequestParam;\n"
	                    + "import org.springframework.web.multipart.MultipartFile;\n"
	                    + "\r\n" + 
	                    "import com.fasterxml.jackson.core.JsonParseException;\r\n" + 
	                    "import com.fasterxml.jackson.core.type.TypeReference;\r\n" + 
	                    "import com.fasterxml.jackson.databind.JsonMappingException;\r\n" + 
	                    "import com.fasterxml.jackson.databind.ObjectMapper;\r\n" + 
	                    "import java.io.IOException;\n"
	                    + "import javax.ws.rs.core.HttpHeaders;\r\n" + 
	                    "import javax.servlet.http.HttpServletRequest;\r\n" + 
	                    "import javax.servlet.http.HttpServletResponse;\n"
	                    + "import org.springframework.core.io.ByteArrayResource;\r\n" + 
	                    "import org.springframework.http.MediaType;\n"
	                    + "import org.springframework.http.ResponseEntity;\n"
	                    + "import java.io.File;"+
	                    "\n" +
	                    this.importKey+" "+this.packagename+".ServeurResponse.ServeurResponse;" +
	                    "\n" +
	                    "@RestController\n" +
	                    "public class "+uppercasefunct.capitalize(nomcontroller)+"Controller {\n" +
	                    "\n" +
	                    " @Autowired \n" +
	                    "I"+uppercasefunct.capitalize(nomcontroller)+"Service "+nomcontroller.toLowerCase()+"Service;\n" +
	                    "@GetMapping(\""+nomcontroller.toLowerCase()+"/list/{owner}\")\n" +
	                    "    public ServeurResponse getAll"+nomcontroller+"(@PathVariable Long owner) {\n" +
	                    "        Iterable<"+uppercasefunct.capitalize(nomcontroller)+"> "+nomcontroller.toLowerCase()+" = "+nomcontroller.toLowerCase()+"Service.list"+uppercasefunct.capitalize(nomcontroller)+"ById(owner);\n" +
	                    "        ServeurResponse response = new ServeurResponse();\n" +
	                    "        if ("+nomcontroller.toLowerCase()+" == null) {\n" +
	                    "            response.setStatut(false);\n" +
	                    "            response.setData(null);\n" +
	                    "            response.setDescription(\"Aucune élèment trouvé.\");\n" +
	                    "\n" +
	                    "        } else {\n" +
	                    "            \n" +
	                    "            response.setStatut(true);\n" +
	                    "            response.setData("+nomcontroller.toLowerCase()+");\n" +
	                    "           response.setDescription(\"Données récupérées.\");\n" +
	                    "        }\n" +
	                    "        return response;\n" +
	                    "   }\n"
	                    + ""
	                    + "\r\n"
	                    + "@GetMapping(\""+nomcontroller.toLowerCase()+"/status/{id}/{status}\")\r\n" + 
	                    "public ServeurResponse updateStatus"+uppercasefunct.capitalize(nomcontroller)+"(@PathVariable Long id,@PathVariable Long status) {\r\n" + 
	                    "    "+uppercasefunct.capitalize(nomcontroller)+" "+nomcontroller.toLowerCase()+" = "+nomcontroller.toLowerCase()+"Service.getOne"+uppercasefunct.capitalize(nomcontroller)+"(id);\r\n" + 
	                    "    "+nomcontroller.toLowerCase()+".setStatus(status);\r\n" + 
	                    "    "+nomcontroller.toLowerCase()+"="+nomcontroller.toLowerCase()+"Service.save("+nomcontroller.toLowerCase()+");\r\n" + 
	                    "    ServeurResponse response = new ServeurResponse();\r\n" + 
	                    "    if ("+nomcontroller.toLowerCase()+" == null) {\r\n" + 
	                    "        response.setStatut(false);\r\n" + 
	                    "        response.setData(null);\r\n" + 
	                    "        response.setDescription(\"Aucune élèment trouvé.\");\r\n" + 
	                    "\r\n" + 
	                    "    } else {\r\n" + 
	                    "        \r\n" + 
	                    "        response.setStatut(true);\r\n" + 
	                    "        response.setData("+nomcontroller.toLowerCase()+");\r\n" + 
	                    "       response.setDescription(\"Données récupérées.\");\r\n" + 
	                    "    }\r\n" + 
	                    "    return response;\r\n" + 
	                    "}" + 
	                    "@GetMapping(\""+nomcontroller.toLowerCase()+"/task/{poowner}\")\r\n" + 
	                    "public ServeurResponse getAllTask(@PathVariable Long poowner) {\r\n" + 
	                    "    Iterable<"+uppercasefunct.capitalize(nomcontroller)+"> "+nomcontroller.toLowerCase()+" = "+nomcontroller.toLowerCase()+"Service.list"+uppercasefunct.capitalize(nomcontroller)+"(poowner);\r\n" + 
	                    "    ServeurResponse response = new ServeurResponse();\r\n" + 
	                    "    if ("+nomcontroller.toLowerCase()+"== null) {\r\n" + 
	                    "        response.setStatut(false);\r\n" + 
	                    "        response.setData(null);\r\n" + 
	                    "        response.setDescription(\"Aucune élèment trouvé.\");\r\n" + 
	                    "\r\n" + 
	                    "    } else {\r\n" + 
	                    "        \r\n" + 
	                    "        response.setStatut(true);\r\n" + 
	                    "        response.setData("+nomcontroller.toLowerCase()+");\r\n" + 
	                    "       response.setDescription(\"Données récupérées.\");\r\n" + 
	                    "    }\r\n" + 
	                    "    return response;\r\n" + 
	                    "}" +
	                    " @PostMapping(\""+nomcontroller.toLowerCase()+"/create\")\n" +
	                    "public ServeurResponse create(@RequestBody "+uppercasefunct.capitalize(nomcontroller)+" "+nomcontroller.toLowerCase()+") {\n" +
	                    "\n" +
	                    "ServeurResponse response = new ServeurResponse();\n" +
	                    "\n" +
	                    ""+nomcontroller.toLowerCase()+"Service.save("+nomcontroller.toLowerCase()+");\n" +
	                    "response.setStatut(true);\n" +
	                    "response.setDescription(\"Enregistrement réussi\");\n" +
	                    "response.setData("+nomcontroller.toLowerCase()+");\n" +
	                    "\n" +
	                    "return response;\n" +
	                    "}\n" +
	                    "@PostMapping(\""+nomcontroller.toLowerCase()+"/update\")\n" +
	                    "     public ServeurResponse update(@RequestBody "+uppercasefunct.capitalize(nomcontroller)+" "+nomcontroller.toLowerCase()+") {\n" +
	                    "          ServeurResponse response = new ServeurResponse();\n" +
	                    "\n" +
	                    "          "+nomcontroller.toLowerCase()+"Service.save("+nomcontroller.toLowerCase()+");\n" +
	                    "          response.setStatut(true);\n" +
	                    "               response.setDescription(\"Données mis à jour\");\n" +
	                    "          return response;\n" +
	                    "     }\n" +
	                    " @PostMapping(\""+nomcontroller.toLowerCase()+"/delete\")\n" +
	                    "     public ServeurResponse delete(@RequestBody "+uppercasefunct.capitalize(nomcontroller)+" "+nomcontroller.toLowerCase()+") {\n" +
	                    "          ServeurResponse response = new ServeurResponse();\n" +
	                    "          "+nomcontroller.toLowerCase()+"Service.delete("+nomcontroller.toLowerCase()+");\n" +
	                    "          response.setStatut(true);\n" +
	                    "          response.setDescription(\"Données supprimé avec succès\");\n" +
	                    "          return response;\n" +
	                    "     }\n" ;
	            for(int j=0; j<ja.size(); j++) {
		   			JsonObject json = (JsonObject) ja.get(j);
	                	 if(json.get("code").getAsString().equals("Document")) {
	                	 controllerfile= controllerfile+" @PostMapping(\""+nomcontroller.toLowerCase()+"/createfile\")\n" +
	                    "    public ServeurResponse create(HttpServletRequest request, @RequestParam(\""+nomcontroller.toLowerCase()+"\") MultipartFile file) {\r\n" + 
	                    "\r\n" + 
	                    "        ServeurResponse response = new ServeurResponse();\r\n" + 
	                    "\r\n" + 
	                    "        "+uppercasefunct.capitalize(nomcontroller)+" "+nomcontroller.toLowerCase()+";\r\n" + 
	                    "        try {\r\n" + 
	                    "            "+nomcontroller.toLowerCase()+" = new ObjectMapper().readValue(\r\n" + 
	                    "                    request.getParameter(\""+nomcontroller.toLowerCase()+"\"), new TypeReference<"+uppercasefunct.capitalize(nomcontroller)+">() {\r\n" + 
	                    "            }\r\n" + 
	                    "            );\r\n" + 
	                    "\r\n" + 
	                    "            "+nomcontroller.toLowerCase()+".set"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"(file.getBytes());\r\n"
	                    		+ ""+nomcontroller.toLowerCase()+".setExtention(file.getContentType());" +  
	                    "\r\n" + 
	                    ""+nomcontroller.toLowerCase()+"Service.save("+nomcontroller.toLowerCase()+");\n" +
	                    "            response.setStatut(true);\r\n" + 
	                    "            response.setDescription(\"Enregistrement réussi\");\r\n" + 
	                    "            response.setData("+nomcontroller.toLowerCase()+");\r\n" + 
	                    "        } catch (JsonParseException e) {\r\n" + 
	                    "            e.printStackTrace();\r\n" + 
	                    "        } catch (JsonMappingException e) {\r\n" + 
	                    "            e.printStackTrace();\r\n" + 
	                    "        } catch (IOException e) {\r\n" + 
	                    "            e.printStackTrace();\r\n" + 
	                    "        } catch (Exception e) {\r\n" + 
	                    "            e.printStackTrace();\r\n" + 
	                    "        }\r\n" + 
	                    "\r\n" + 
	                    "        return response;\r\n" + 
	                    "  }\r\n"
	                    + " @GetMapping(\""+nomcontroller.toLowerCase()+"/downloadFile/{id}\")\r\n" + 
	                    "	public ResponseEntity<ByteArrayResource> downloadfile(@PathVariable Long id,  HttpServletResponse response){\r\n" + 
	                    "	 \r\n" + 
	                    "		Optional<"+uppercasefunct.capitalize(nomcontroller)+"> data = "+nomcontroller.toLowerCase()+"Service.findById(id);\r\n" + 
	                    "             File file = new File(data.get().get"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"().toLowerCase());\r\n" + 
	                    "             System.out.println(file.getName());\r\n" + 
	                    "		return ResponseEntity.ok()\r\n" + 
	                    "				.contentType(MediaType.APPLICATION_PDF)\r\n" + 
	                    "				.header(HttpHeaders.CONTENT_DISPOSITION,\"attachment:filename=\"+file.getName())\r\n" + 
	                    "				.body(new ByteArrayResource(data.get().get"+uppercasefunct.capitalize(json.get("name").getAsString().toLowerCase())+"()));\r\n" + 
	                    "             \r\n" + 
	                    "            \r\n" + 
	                    "	}";
	                	 }
	                 	}
	                controllerfile=controllerfile+ "}";

	            FileWriter myWritecontroller = new FileWriter(packcontroller+"\\"+nomcontroller+"Controller.java");
	            myWritecontroller.write("\n" +controllerfile);
	            myWritecontroller.close();


	           
		}
}
