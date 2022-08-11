package sn.gainde2000.pi.formgenerator.generator;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import sn.gainde2000.pi.formgenerator.entity.ChampsV2;
import sn.gainde2000.pi.formgenerator.entity.FormulaireV2;
import sn.gainde2000.pi.formgenerator.entity.Step;
import sn.gainde2000.pi.formgenerator.entity.ValueChampsV2;

public class FrontGenerator {
	public String fichierConfig = "/opt/industrialisation/fileconfig";
	public String repo = "/opt/industrialisation/codesource";

	public FrontGenerator() {
		 String SE = System.getProperty("os.name").toLowerCase();
		 if (SE.indexOf("nux") >= 0) {
			 	this.repo="/opt/industrialisation/codesource";
			 	this.fichierConfig ="/opt/industrialisation/fileconfig";
	        } else {
	        	this.repo=System.getProperty("user.dir")+"/opt/industrialisation/codesource";
	        	this.fichierConfig =System.getProperty("user.dir")+"/opt/industrialisation/fileconfig";
	        }
	}
//	public final static String FRONT_SOURCE_PATH = "/Users/macbookpro/Documents/gainde2000/project_industrialisation/industrialisation/backend/Industrialisation-backend/opt/industrialisation/codesource/frontrdc/src/app/";
	public final static String FRONT_SOURCE_PATH = "/opt/industrialisation/codesource/frontrdc/src/app/";

	public final static String COMPONENT_NAME = "#ComponentName";
	public final static String COMPONENT_NAME_LOWERCASE = "#componentName";
	public final static String WORKFLOW_NAME = "#WorkflowName";
	public final static String WORKFLOW_NAME_LOWERCASE = "#workflowName";
	public final static String COMPONENT_IMPORT = "#componentsImport";
	public final static String COMPONENT_DECLARATION = "#componentsDeclaration";
	private final static String MODULE_NAME = "#module";
	
	private final static String MODULE_NAME_CAPITALIZE = "#Module";
	private final static String HTML_TABLE_ALL_COLUMNS = "#columnsTable";
	private final static String HTML_TABLE_COLUMN = "" + "\n<ng-container matColumnDef=\"#fieldName\">\n"
			+ "      <th mat-header-cell *matHeaderCellDef mat-sort-header> {{'#module.#fieldName' | translate}} </th>\n"
			+ "      <td mat-cell *matCellDef=\"let element\"> {{element.#fieldName}} </td>\n" + " </ng-container>\n";

	private final static String EDIT_STEP_NAME = ""
			+ "            <div class=\"mt-3 mb-3\">{{'#module.#stepName' | translate}}</div>\n";

	private final static String EDIT_STEPPER_BUTTON = "<div>\n"
			+ "              <button type=\"button\" mat-stroked-button color=\"primary\" matStepperPrevious>{{'btn.previous' | translate}}</button>\n"
			+ "              <button type=\"button\" mat-stroked-button color=\"primary\" matStepperNext>{{'btn.suivant' | translate}}</button>\n"
			+ "            </div>";

	private final static String EDIT_STEPPER_BUTTON_START = "<div>\n"
			+ "              <button type=\"button\" mat-flat-button color=\"warn\" (click)=\"closeDialog()\" >{{'btn.fermer' | translate}}</button>\n"
			+ "              <button type=\"button\" mat-stroked-button color=\"primary\" matStepperNext>{{'btn.suivant' | translate}}</button>\n"
			+ "            </div>\n";

	private final static String EDIT_STEPPER_BUTTON_END = "<div>\n"
			+ "              <button type=\"button\" mat-stroked-button color=\"primary\" matStepperPrevious>{{'btn.previous' | translate}}</button>\n"
			+ "              <button type=\"submit\" mat-flat-button color=\"primary\" >{{'btn.valider' | translate}}</button>\n"
			+ "            </div>";

	private final static String DETAIL_STEPPER_BUTTON_END = "<div>\n"
			+ "              <button type=\"button\" mat-stroked-button color=\"primary\" matStepperPrevious>{{'btn.previous' | translate}}</button>\n"
			+ "              <button type=\"button\" mat-flat-button color=\"warn\" (click)=\"closeDialog()\" >{{'btn.fermer' | translate}}</button>\n"
			+ "            </div>\n";

	private final static String EDIT_STEPPER = "" + "<mat-step>\n" + EDIT_STEP_NAME + "	#stepChamps\n" + " #stepButtons"
			+ "</mat-step>\n";
	private final static String EDIT_STEPPER_NON_STEPPER = "" + "\n"  + "	#stepChamps\n" 
			+ "\n";

	private final static String EDIT_STEPPER_DETAIL = "" + "<mat-step>\n" + EDIT_STEP_NAME + "" + "<div class=\"row\">"
			+ "	#stepChamps\n" + "</div>" + EDIT_STEPPER_BUTTON + "" + "</mat-step>\n";

	private final static String EDIT_FIELD_INPUT = "" + "<mat-form-field appearance=\"fill\">\n"
			+ "      <mat-label>{{'#module.#fieldName' | translate}}</mat-label>\n"
			+ "      <input matInput type=\"#fieldType\" formControlName=\"#fieldName\" required>\n"
			+ "</mat-form-field>" + "\n";
	private final static String EDIT_FIELD_DATE = "" + "<mat-form-field appearance=\"fill\">\n"
			+ "      <mat-label>{{'#module.#fieldName' | translate}}</mat-label>\n"
			+ "      <input matInput [matDatepicker]=\"#fieldName\" formControlName=\"#fieldName\" required>\n"
			+ "      <mat-datepicker-toggle matSuffix [for]=\"#fieldName\"></mat-datepicker-toggle>\n"
			+ "      <mat-datepicker ##fieldName></mat-datepicker>\n" + "</mat-form-field>" + "\n";

	private final static String EDIT_FIELD_SELECT = "" + "<mat-form-field appearance=\"fill\">\n"
			+ "    <mat-label>{{'#module.#fieldName' | translate}}</mat-label>\n"
			+ "    <mat-select formControlName=\"#fieldName\" required>\n" + "         #selectOptions"
			+ "    </mat-select>\n" + " </mat-form-field>" + "\n";

	
	private final static String EDIT_FIELD_SELECT_MULTIPLE = "" + "<mat-form-field appearance=\"fill\">\n"
			+ "    <mat-label>{{'#module.#fieldName' | translate}}</mat-label>\n"
			+ "    <mat-select formControlName=\"#fieldName\" required multiple>\n" + "         #selectOptions"
			+ "    </mat-select>\n" + " </mat-form-field>" + "\n";
	
	private final static String EDIT_FIELD_SELECT_OPTION = "<mat-option value=\"#optionValue\">{{'#module.#optionLabel'|translate}}</mat-option>\n";

	private final static String EDIT_FIELD_RELATION = "" + "<mat-form-field appearance=\"fill\">\n"
			+ "    <mat-label>{{'#module.#fieldName' | translate}}</mat-label>\n"
			+ "    <mat-select formControlName=\"#fieldName\" required>\n" + "         #selectOptions"
			+ "    </mat-select>\n" + " </mat-form-field>" + "\n";

	private final static String EDIT_FIELD_RELATION_OPTION = "<mat-option *ngFor=\"let op of #options\" [value]=\"op\">{{'op.#optionLabel'|translate}}</mat-option>\n";

	private final static String EDIT_FIELD_RADIO = "" + "<label>{{'#module.#fieldName' | translate}}</label>"
			+ "<mat-radio-group formControlName=\"#fieldName\">\n" + "     #radioOptions" + " </mat-radio-group>" + "\n";
	private final static String EDIT_FIELD_RADIO_OPTION = "<mat-radio-button class=\"\" value=\"#optionValue\">{{'#module.#optionLabel' | translate}}</mat-radio-button>\n";

//	private final static String EDIT_FIELD_CHECKBOX = "<label>{{'#module.#fieldName' | translate}}</label>\n";
//	private final static String EDIT_FIELD_CHECKBOX_OPTION = "" + "<label>\n"
//			+ "   <input type=\"checkbox\" value=\"#optionValue\" formControlName=\"select#fieldName\">\n"
//			+ "   {{'#module.#optionLabel' | translate}}\n" + "</label>\n";

	private final static String EDIT_FIELD_TEXTAREA = "" + "<mat-form-field appearance=\"fill\">\n"
			+ "    <mat-label>{{'#module.#fieldName' | translate}}</mat-label>\n"
			+ "    <textarea matInput formControlName=\"#fieldName\" required>\n" + "    </textarea>\n"
			+ " </mat-form-field>" + "\n";

	private final static String EDIT_INPUT_FILE = ""
			+ "<div class=\"row container-fluid mt-1 mb-2\" (click)=\"addFile#fieldName()\">\n"
			+ "                <div class=\"file-upload\">\n" + "<span class=\"file-name\">\n"
			+ "                    {{'#module.#fieldName' | translate}}\n" + "</span>\n"
			+ "                  <span *ngIf=\"#fieldName\" class=\"sep\"></span>\n" + "\n"
			+ "                  <span class=\"file-name2\">\n" + "{{#fieldName?.name}}\n"
			+ "                  </span>\n" + "\n" + "" + "<span class=\"flex\">\n" + "                  </span>\n"
			+ "                  <span *ngIf=\"#fieldName\"><mat-icon color=\"primary\" >check</mat-icon></span>\n"
			+ "                </div>\n" + "</div>\n";

	private final static String DETAIL_INPUT_FILE = ""
			+ "<div class=\"row container-fluid mt-1 mb-2\" (click)=\"viewFile#fieldName()\">\n"
			+ "         <div class=\"file-upload\">\n" + "<span class=\"file-name\">\n"
			+ "         {{'#module.#fieldName' | translate}}\n" + "</span>\n" + "		</div>\n" 
			+ "</div>\n";

	private final static String EDIT_INPUT_FILE_HIDDEN = ""
			+ "<input type=\"file\" #file#fileId hidden (change)=\"onFileAdded#fieldName()\">\n" ;

	private final static String DETAIL_FIELD = "" + "<div class=\"col-sm-6 container-fluid\">\n"
			+ "       <div class=\"container-fluid fond\"><br>\n"
			+ "          	<span>{{'#module.#fieldName' | translate}}</span><br>\n"
			+ "          	<p class=\"mymargin foncer\">{{donnee?.#fieldName #pipe}}</p>\n" + "        </div>\n"
			+ " </div>\n";
	
	private final static String DETAIL_FIELD_RELATION = "" + "<div class=\"col-sm-6 container-fluid\">\n"
			+ "       <div class=\"container-fluid fond\"><br>\n"
			+ "          	<span>{{'#module.#fieldName' | translate}}</span><br>\n"
			+ "          	<p class=\"mymargin foncer\">{{donnee?.#fieldName?.#champsName #pipe}}</p>\n" + "        </div>\n"
			+ " </div>\n";

	private final static String DETAIL_FIELD_TEXTAREA = "" + "<div class=\"col-sm-12 container-fluid\">\n"
			+ "       <div class=\"container-fluid fond\"><br>\n"
			+ "          	<span>{{'#module.#fieldName' | translate}}</span><br>\n"
			+ "          	<p class=\"mymargin foncer\">{{donnee?.#fieldName #pipe}}</p>\n" + "        </div>\n"
			+ " </div>\n";
	
	
	
	private final static String EDIT_COMPONENT_FILE_CODE = ""
			+ "@ViewChild('file#fieldName') file#fieldName;\n" + 
			"\n" + 
			" #fieldName:File;\n" + 
			"\n" + 
			"  addFile#fieldName(){\n" + 
			"    this.file#fieldName.nativeElement.click();\n" + 
			"  }\n" + 
			"\n" + 
			"  onFileAdded#fieldName(){\n" + 
			"    this.#fieldName = this.file#fieldName.nativeElement.files[0];\n" + 
			"    const extension = this.#fieldName.name.split('.')[1].toLowerCase();\n" + 
			"       if ( \"pdf\" != extension ) {\n" + 
			"        this.translate.get(\"#module.fileExtenxionNotif\").subscribe((res: string) => {\n" + 
			"          this.notification.warn(res);\n" + 
			"        });\n" + 
			"        this.#fieldName =null;\n" + 
			"        return;\n" + 
			"       }\n" + 
			"      if (this.#fieldName.size >3000000){\n" + 
			"        this.translate.get(\"#module.fileSizeNotif\").subscribe((res: string) => {\n" + 
			"          this.notification.warn(res);\n" + 
			"        });\n" + 
			"        this.#fieldName = null;\n" + 
			"        return;\n" + 
			"      }\n" + 
			"  }\n";
	
	private static final String EDIT_COMPONENT_SUBMIT_FUNCTION_FILES =""
			
			+ "if(#filesAddedCondition){\n" + 
			"      this.translate.get(\"#module.selectAllFilesNotif\").subscribe((res: string) => {\n" + 
			"        this.notification.warn(res);\n" + 
			"      });\n" + 
			"      return;\n" + 
			"    }\n"
			+ "if(this.EditForm.invalid){\n" + 
			"      this.translate.get('#module.remplirTousLesChampsNotif').subscribe(data=>{\n" + 
			"        this.notification.warn(data);\n" + 
			"      })\n" + 
			"      return;\n" + 
			"    }\n"
			+ ""+  
			"      this.#moduleService.create#Module(this.EditForm.value #filesArgument).subscribe((data:any) => {\n" + 
			"        if (data.statut) {\n" + 
			"          this.translate.get('#module.confirmEnr').subscribe((res: string) => {\n" + 
			"            this.notification.success(res);\n" + 
			"          });\n" + 
			"\n" + 
			"          this.EditForm.reset();\n" + 
			"          this.dialog.close({status:true});\n" + 
			"        }\n" + 
			"      }, error => {\n" + 
			"        let ReportSaveError;\n" + 
			"        this.translate.get('#module.erreurEnr').subscribe((res: string) => {\n" + 
			"          this.notification.error(res);\n" + 
			"        });\n" + 
			"      });\n";
	
	
	private static final String WORKFLOW_EDIT_COMPONENT_SUBMIT_FUNCTION_FILES =""
			+ "this.EditForm.value.poOwner = localStorage.getItem(\"profile\")\n" + 
			"  this.EditForm.value.owner = localStorage.getItem(\"id\")\n" + 
			"  this.EditForm.value.status = this.status\n"
			+ ""
			+ "if(#filesAddedCondition){\n" + 
			"      this.translate.get(\"#module.selectAllFilesNotif\").subscribe((res: string) => {\n" + 
			"        this.notification.warn(res);\n" + 
			"      });\n" + 
			"      return;\n" + 
			"    }"
			+ "if(this.EditForm.invalid){\n" + 
			"      this.translate.get('#module.remplirTousLesChampsNotif').subscribe(data=>{\n" + 
			"        this.notification.warn(data);\n" + 
			"      })\n" + 
			"      return;\n" + 
			"    }\n"
			+ ""+  
			"      this.#componentNameService.create#ComponentName(this.EditForm.value #filesArgument).subscribe((data:any) => {\n" + 
			"        if (data.statut) {\n" + 
			"          this.translate.get('#module.confirmEnr').subscribe((res: string) => {\n" + 
			"            this.notification.success(res);\n" + 
			"          });\n" + 
			"\n" + 
			"          "
			+ "			#submitUpdateTask\n"		 
			+ "		   this.EditForm.reset();\n" + 
			"          this.dialog.close({status:true});\n" + 
			"        }\n" + 
			"      }, error => {\n" + 
			"        let ReportSaveError;\n" + 
			"        this.translate.get('#module.erreurEnr').subscribe((res: string) => {\n" + 
			"          this.notification.error(res);\n" + 
			"        });\n" + 
			"      });\n";
	
	private static final String WORKFLOW_TASK_UPDATE_FUNCTION = ""
			+ "\nthis.#componentNameService.updateTask#ComponentName(this.donnee.id, this.status).subscribe(data => {\n" + 
			"\n" + 
			"          })";
	private static final String EDIT_COMPONENT_SUBMIT_FUNCTION =""
			
			+ "if(this.EditForm.invalid){\n" + 
			"      this.translate.get('#module.remplirTousLesChampsNotif').subscribe(data=>{\n" + 
			"        this.notification.warn(data);\n" + 
			"      })\n" + 
			"      return;\n" + 
			"    }\n"
			+ ""+  
			"      this.#moduleService.create#Module(this.EditForm.value #filesArgument).subscribe((data:any) => {\n" + 
			"        if (data.statut) {\n" + 
			"          this.translate.get('#module.confirmEnr').subscribe((res: string) => {\n" + 
			"            this.notification.success(res);\n" + 
			"          });\n" + 
			"\n" + 
			"          this.EditForm.reset();\n" + 
			"          this.dialog.close({status:true});\n" + 
			"        }\n" + 
			"      }, error => {\n" + 
			"        let ReportSaveError;\n" + 
			"        this.translate.get('#module.erreurEnr').subscribe((res: string) => {\n" + 
			"          this.notification.error(res);\n" + 
			"        });\n" + 
			"      });\n";
	
	private static final String WORKFLOW_EDIT_COMPONENT_SUBMIT_FUNCTION =""
			+ "this.EditForm.value.poOwner = localStorage.getItem(\"profile\")\n" + 
			"  this.EditForm.value.owner = localStorage.getItem(\"id\")\n" + 
			"  this.EditForm.value.status = this.status\n"
			+ ""
		
			+ "if(this.EditForm.invalid){\n" + 
			"      this.translate.get('#module.remplirTousLesChampsNotif').subscribe(data=>{\n" + 
			"        this.notification.warn(data);\n" + 
			"      })\n" + 
			"      return;\n" + 
			"    }\n"
			+ ""+  
			"      this.#moduleService.create#Module(this.EditForm.value #filesArgument).subscribe((data:any) => {\n" + 
			"        if (data.statut) {\n" + 
			"          this.translate.get('#module.confirmEnr').subscribe((res: string) => {\n" + 
			"            this.notification.success(res);\n" + 
			"          });\n" + 
			"\n" + 
			"          #submitUpdateTask\n"
			+ "			"
			+ "		   this.EditForm.reset();\n" + 
			"          this.dialog.close({status:true});\n" + 
			"        }\n" + 
			"      }, error => {\n" + 
			"        let ReportSaveError;\n" + 
			"        this.translate.get('#module.erreurEnr').subscribe((res: string) => {\n" + 
			"          this.notification.error(res);\n" + 
			"        });\n" + 
			"      });\n";
	
	public static final String WORKFLOW_MAIN_POPUP_IMPORTS = ""
			+ "import { Add#FormNameComponent } from '../add-#formName/add-#formName.component';\n" + 
			"";
	public static final String WORKFLOW_TRAITEMENT_FUNCTIONS = ""
			+ "#formName = false\n" + 
			"	verify#FormName() {\n" + 
			"		this.#moduleService.getAllTask().subscribe(data => {\n" + 
			"			this.task = data\n" + 
			"			console.log(this.task.data)\n" + 
			"			for (let i = 0; i < this.task.data.length; i++) {\n" + 
			"				if (this.task.data[i].poOwner.proId == localStorage.getItem(\"profil\") && this.task.data[i].tskFormName == '#FormName') {\n" + 
			"					this.#formName = true\n" + 
			"					this.#moduleService.getStatus(this.task.data[i].tskId).subscribe((data: any) => {\n" + 
			"						this.status = data.data\n" + 
			"					})\n" +
			"					break\n" + 
			"				}\n" + 
			"			}\n" + 
			"		})\n" + 
			"	}\n"
			+ "\n\n"
			+ "openDialogAdd#FormName(data) {\n" + 
			"		const dialog1 = this.dialogRef.open(Add#FormNameComponent, {\n" + 
			"			width: '700px',\n" + 
			"			data: { data: data, status: this.status }\n" + 
			"		}).afterClosed().subscribe(result => {\n" + 
			"			location.reload();\n" + 
			"			this.list#Module();\n" + 
			"		});\n" + 
			"	}\n"
			+ "\n\n"
			+ "";
	
	
	private static final String WORKFLOW_MAIN_PRINCIPALES_FUNCTIONS = ""
			+ ""
			+ "ngAfterViewInit() {\n" + 
			"		this.list#Module();\n" + 
			"		this.verify#Module();\n" + 
			"		if (!this.#module) {\n" + 
			"			this.listTask(localStorage.getItem('profil'))\n" + 
			"		}\n" + 
			"		\n" + 
			"		#verifyTraitementCall\n" + 
			"	}\n" + 
			"	ngOnInit() {\n"
			+ "		" + 
			"	}"
			+ "\n"
			+ "#module = false\n" + 
			"	verify#Module() {\n" + 
			"		this.#moduleService.getAllTask().subscribe(data => {\n" + 
			"			this.task = data\n" + 
			"			console.log(this.task.data)\n" + 
			"			for (let i = 0; i < this.task.data.length; i++) {\n" + 
			"				if (this.task.data[i].poOwner.proId == localStorage.getItem(\"profil\") && this.task.data[i].tskFormName == '#Module') {\n" + 
			"					this.#module = true\n" + 
			"					this.list#Module();\n" + 
			"					this.#moduleService.getStatus(this.task.data[i].tskId).subscribe((data: any) => {\n" + 
			"						this.status = data.data\n" + 
			"					})\n" + 
			"					break\n" + 
			"				}\n" + 
			"			}\n" + 
			"		})\n" + 
			"	}\n"
			+ "list#Module() {\n" + 
			"		this.#moduleService.get#ModuleAll(localStorage.getItem('id')).subscribe(data => {\n" + 
			"			this.form = data\n" + 
			"			if (this.form.statut) {\n" + 
			"				this.dataSource = new MatTableDataSource<any>(this.form.data);\n" + 
			"				this.dataSource.paginator = this.paginator.toArray()[0];\n" + 
			"				this.dataSource.sort = this.sort;\n" + 
			"			} else {\n" + 
			"				\n" + 
			"			}\n" + 
			"		})\n" + 
			"	}\n"
			+ "listTask(poowner) {\n" + 
			"		this.#moduleService.getTask(poowner).subscribe(data => {\n" + 
			"			this.form = data\n" + 
			"			if (this.form.statut) {\n" + 
			"				this.dataSource2 = new MatTableDataSource<any>(this.form.data);\n" + 
			"				this.dataSource2.paginator = this.paginator.toArray()[0];\n" + 
			"				this.dataSource2.sort = this.sort;\n"
			+ "				this.getTaskTraite();\n" + 
			"			} else {\n" + 
			"			}\n" + 
			"		})\n" + 
			"	}\n"
			+ ""
			+ "getTaskTraite() {\n" + 
			"		let traitant = null;\n"
			+ ""
			+ "		#conditionTaskTraites" + 
			 
			"		this.#moduleService.getTaskTraite(localStorage.getItem('profil'), traitant).subscribe((data: any) => {\n" + 
			"			if (data.statut) {\n" + 
			"				this.dataSourceTraites = new MatTableDataSource<any>(data.data);\n" + 
			"				this.dataSourceTraites.paginator = this.paginator.toArray()[1];\n" + 
			"				this.dataSourceTraites.sort = this.sort;\n" + 
			"			}\n" + 
			"		});\n" + 
			"	}\n"
			+ ""
			+ ""
			+ " openDialogAdd(): void {\n" + 
			"		const dialog = this.dialogRef.open(Add#ModuleComponent, {\n" + 
			"			width: '700px',\n" + 
			"			data: this.status,\n" + 
			"			disableClose: true\n" + 
			"		}).afterClosed().subscribe(result => {\n" + 
			"			location.reload();\n" + 
			"			this.list#Module();\n" + 
			"		});\n" + 
			"\n" + 
			"	}\n" + 
			"	openDialogEdit(data) {\n" + 
			"		const dialog1 = this.dialogRef.open(Add#ModuleComponent, {\n" + 
			"			width: '700px',\n" + 
			"			data: { data: data, status: this.status }\n" + 
			"			, disableClose: true\n" + 
			"		}).afterClosed().subscribe(result => {\n" + 
			"		});\n" + 
			"	}"
			+ "\n"
			+ "openDialogDetail#Module(data) {\n" + 
			"		const dialog1 = this.dialogRef.open(View#ModuleComponent, {\n" + 
			"			width: '700px',\n" + 
			"			data: { data: data, status: this.status }\n" + 
			"		}).afterClosed().subscribe(result => {\n" + 
			"		});\n" + 
			"	}"
			+ "";
	
	

	
	
	
	
	/**
	 * replace in file key word with the true values
	 * 
	 * @param sb
	 * @param find
	 * @param replace
	 * @return new value content
	 */
	public static StringBuffer replaceAll(StringBuffer sb, String find, String replace) {
		return new StringBuffer(Pattern.compile(find).matcher(sb).replaceAll(replace));
	}

	public static String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}

		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static String getFieldNameRelation(String column) {
		String[] ch = column.split("_");
		String newCh = "";
		int count = 0;
		for(String s : ch) {
			if(count==0)
				newCh+=s;
			else {
				newCh+=capitalize(s);
			}
			
			count++;
		}
		return newCh;
	}

	/**
	 * 
	 * @param SOURCE_TEMPLATE
	 * @param VARNAME
	 * @param VARNAME_LOWERCASE
	 * @param nomModule
	 * @param SOURCE_PATH
	 * @param fileName
	 * @return
	 */
	public static boolean fileContentFromTemplate(String SOURCE_TEMPLATE, String VARNAME, String VARNAME_LOWERCASE,
			String nomModule, String SOURCE_PATH, String fileName) {
		boolean process = false;
		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource(SOURCE_TEMPLATE).getFile());
		StringBuffer contentBuffer = new StringBuffer();

		contentBuffer = FileUtils.getFileContent(file);

		// remplacing $vars in file
		if (contentBuffer != null) {
			if(!VARNAME.isEmpty() && ! VARNAME_LOWERCASE.isEmpty()) {
			contentBuffer = replaceAll(contentBuffer, VARNAME, capitalize(nomModule));
			contentBuffer = replaceAll(contentBuffer, VARNAME_LOWERCASE, nomModule);
			}
			process = FileUtils.saveFile(SOURCE_PATH, fileName, contentBuffer);

		}
		return process;
	}

	/**
	 * creation de module
	 * 
	 * @param nomModule
	 * @return status
	 */
	public static boolean createModule(String nomModule, List<FormulaireV2> forms) {
		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource("templates/angular/module").getFile());

		StringBuffer contentBuffer = new StringBuffer();
		
		StringBuffer imports = new StringBuffer();
		StringBuffer components = new StringBuffer();
		
		if(forms != null && forms.size()>0) {
		imports.append("import { Add"+capitalize(nomModule)+"Component } from './components/add-"+nomModule+"/add-"+nomModule+".component';\n" + 
				"");
		imports.append("import { View"+capitalize(nomModule)+"Component } from './components/view-"+nomModule+"/view-"+nomModule+".component';\n" + 
				"");
		components.append("Add"+capitalize(nomModule)+"Component,\n");
		components.append("View"+capitalize(nomModule)+"Component\n");

		for(FormulaireV2 f: forms) {
			imports.append("import { Add"+capitalize(f.getFrmNom())+"Component } from './components/add-"+f.getFrmNom()+"/add-"+f.getFrmNom()+".component';\n" + 
					"");
			components.append(",Add"+capitalize(f.getFrmNom())+"Component\n");

		}
		}
		
		contentBuffer = FileUtils.getFileContent(file);
		contentBuffer =replaceAll(contentBuffer, "#workflowName", nomModule);
		contentBuffer =replaceAll(contentBuffer, "#WorkflowName", capitalize(nomModule));
		contentBuffer =replaceAll(contentBuffer, "#componentsImport", imports.toString());
		contentBuffer =replaceAll(contentBuffer, "#componentsDeclaration", components.toString());

		return FileUtils.saveFile(FRONT_SOURCE_PATH + "/" + nomModule, nomModule + ".module.ts", contentBuffer);
	
		
//		return fileContentFromTemplate("templates/angular/module", WORKFLOW_NAME, WORKFLOW_NAME_LOWERCASE, nomModule,
//				FRONT_SOURCE_PATH + "/" + nomModule, nomModule + ".module.ts");
	}

	/**
	 * create module component
	 * 
	 * @param nomModule
	 * @return status
	 */
	public static boolean createModuleComponent(String nomModule) {
		return fileContentFromTemplate("templates/angular/moduleComponent", WORKFLOW_NAME, WORKFLOW_NAME_LOWERCASE,
				nomModule, FRONT_SOURCE_PATH + "/" + nomModule, nomModule + ".component.ts");
	}

	public static boolean createModuleRouting(String nomModule) {

		return fileContentFromTemplate("templates/angular/routingmodule", WORKFLOW_NAME, WORKFLOW_NAME_LOWERCASE,
				nomModule, FRONT_SOURCE_PATH + "/" + nomModule, nomModule + "-routing.module.ts");
	}
	
	public static boolean createService(String nomModule, FormulaireV2 formV2) {
		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource("templates/angular/service").getFile());

		StringBuffer contentBuffer = new StringBuffer();
		
		StringBuffer filesArguments = new StringBuffer();
		StringBuffer fileFormDataAppend = new StringBuffer();
		StringBuffer selectMultipleJoin = new StringBuffer();
		
		for(Step step : formV2.getSteps()) {
			for(ChampsV2 champsV2: step.getChamps()) {
				if(champsV2.getChpType().equalsIgnoreCase("file")) {
					filesArguments.append(","+champsV2.getChpNom()+":any");
					fileFormDataAppend.append("\nformData.append('"+champsV2.getChpNom()+"',"+champsV2.getChpNom()+")");
				}
				if(champsV2.getChpType().equalsIgnoreCase("checkbox")) {
					selectMultipleJoin.append("data."+champsV2.getChpNom()+".join();\n");
				}
			}
		}
		
		contentBuffer = FileUtils.getFileContent(file);
		contentBuffer =replaceAll(contentBuffer, "#componentName", nomModule);
		contentBuffer =replaceAll(contentBuffer, "#ComponentName", capitalize(nomModule));
		return FileUtils.saveFile(FRONT_SOURCE_PATH + "/" + nomModule+"/service", nomModule + ".service.ts", contentBuffer);
		
	}
	
	public static boolean createServiceWorkflow(String primaryForm,String nomModule,FormulaireV2 formV2) {
		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource("templates/angular/workflow/service").getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);
		StringBuffer filesArguments = new StringBuffer();
		StringBuffer fileFormDataAppend = new StringBuffer();
		StringBuffer selectMultipleJoin = new StringBuffer();
		
		for(Step step : formV2.getSteps()) {
			for(ChampsV2 champsV2: step.getChamps()) {
				if(champsV2.getChpType().equalsIgnoreCase("file")) {
					filesArguments.append(","+champsV2.getChpNom()+":any");
					fileFormDataAppend.append("\nformData.append('"+champsV2.getChpNom()+"',"+champsV2.getChpNom()+")");
				}
				if(champsV2.getChpType().equalsIgnoreCase("checkbox")) {
					selectMultipleJoin.append("data."+champsV2.getChpNom()+".join();\n");
				}
			}
		}
		contentBuffer = replaceAll(contentBuffer,"#componentName", nomModule);
		contentBuffer = replaceAll(contentBuffer,"#ComponentName", capitalize(nomModule));
		contentBuffer = replaceAll(contentBuffer,"#filesarguments",filesArguments.toString());
		contentBuffer = replaceAll(contentBuffer,"#joinSelectMultiple",selectMultipleJoin.toString());
		contentBuffer = replaceAll(contentBuffer,"#appendFileOnFormData",fileFormDataAppend.toString());

		return FileUtils.saveFile(FRONT_SOURCE_PATH + "/" + primaryForm+"/service", nomModule + ".service.ts", contentBuffer);
		
	}
	
	
	public static boolean createMainComponentHtml(String nomModule, FormulaireV2 formV2) {
		boolean process = false;
		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource("templates/angular/mainComponentHtml").getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);
		StringBuffer columnBuffer = new StringBuffer();

		if (contentBuffer != null) {
			for (Step step : formV2.getSteps()) {
				if (step.getChamps().size() > 0) {
					for (ChampsV2 champs : step.getChamps()) {
						String column = HTML_TABLE_COLUMN.replaceAll("#fieldName", champs.getChpNom());
						columnBuffer.append(column);
					}
				}
			}
			contentBuffer = replaceAll(contentBuffer, HTML_TABLE_ALL_COLUMNS, columnBuffer.toString());
			contentBuffer = replaceAll(contentBuffer, MODULE_NAME, nomModule);

			process = FileUtils.saveFile(FRONT_SOURCE_PATH + "/" + nomModule + "/components/main-content",
					"main-content.component.html", contentBuffer);
		}

		return process;
	}
	
	public static boolean createMainComponentHtmlWorkflow(String nomModule, FormulaireV2 formV2, List<FormulaireV2> forms) {
		boolean process = false;
		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource("templates/angular/workflow/mainComponentHtml").getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);
		StringBuffer columnBuffer = new StringBuffer();
		StringBuffer actionButtons = new StringBuffer();
		
		if (contentBuffer != null) {
			for (Step step : formV2.getSteps()) {
				if (step.getChamps().size() > 0) {
					for (ChampsV2 champs : step.getChamps()) {
						String column = HTML_TABLE_COLUMN.replaceAll("#fieldName", champs.getChpNom());
						columnBuffer.append(column);
					}
				}
			}
			
			for(FormulaireV2 f: forms) {
				actionButtons.append("<button  mat-menu-item\n" + 
						"                    (click)=\"openDialogAdd"+capitalize(f.getFrmNom())+"(element)\">\n" + 
						"\n" + 
						"                    <mat-icon\n" + 
						"                    matTooltip=\"{{'Action.edit' | translate}}\"\n" + 
						"                    color=\"primary\">edit\n" + 
						"                    </mat-icon>{{'Action.edit' | translate}}\n" + 
						"             </button>\n");
			}
			
			contentBuffer = replaceAll(contentBuffer, HTML_TABLE_ALL_COLUMNS, columnBuffer.toString());
			contentBuffer = replaceAll(contentBuffer, MODULE_NAME, nomModule);
			contentBuffer = replaceAll(contentBuffer, MODULE_NAME_CAPITALIZE, capitalize(nomModule));

			contentBuffer = replaceAll(contentBuffer, "#actionButtons", actionButtons.toString());


			process = FileUtils.saveFile(FRONT_SOURCE_PATH + "/" + nomModule + "/components/main-content",
					"main-content"+".component.html", contentBuffer);
		}
		

		return process;
	}

	public static boolean createMainComponent(FormulaireV2 formV2) {

		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource("templates/angular/workflow/mainComponent").getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);
		StringBuffer columnBuffer = new StringBuffer();
		String mainFunctions = "";
		mainFunctions += WORKFLOW_MAIN_PRINCIPALES_FUNCTIONS;
		StringBuffer columnDefinitions = new StringBuffer();
		columnDefinitions.append("id");
		if(formV2.getSteps().size()>0)
		for(Step step: formV2.getSteps()) {
			if(step.getChamps().size()>0)
			for(int i = 0 ;i < step.getChamps().size();i++) {
				String chname = "'";
				if(step.getChamps().get(i).getChpType().equals("relation")) {
					chname = step.getChamps().get(i).getChpNom().substring(3);
				}else {
					chname = step.getChamps().get(i).getChpNom();
				}
				columnDefinitions.append("','"+chname);
				
			}
		}
		columnDefinitions.append("','action'");
		contentBuffer = replaceAll(contentBuffer, "#mainFunctions", mainFunctions);
		contentBuffer = replaceAll(contentBuffer, "#columnDefinitions", columnDefinitions.toString());
		contentBuffer = new StringBuffer(contentBuffer.toString().replaceAll("#module", formV2.getFrmNom()).replaceAll("#Module", capitalize(formV2.getFrmNom())));
		return FileUtils.saveFile( FRONT_SOURCE_PATH + "/" + formV2.getFrmNom() + "/components/main-content",
				"main-content.component.ts",contentBuffer);

	}
	
	// call based on workflow task liste 
	public static boolean createMainComponentWorkflow(FormulaireV2 formV2, List<FormulaireV2> forms) {

		boolean process = false;
		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource("templates/angular/workflow/mainComponent").getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);
		StringBuffer columnBuffer = new StringBuffer();
		String mainFunctions = "";
		mainFunctions += WORKFLOW_MAIN_PRINCIPALES_FUNCTIONS;
		StringBuffer columnDefinitions = new StringBuffer();
		columnDefinitions.append("'id");
		if(formV2.getSteps().size()>0)
		for(Step step: formV2.getSteps()) {
			if(step.getChamps().size()>0)
			for(int i = 0 ;i < step.getChamps().size();i++) {
				String chname = "";
				if(step.getChamps().get(i).getChpType().equals("relation")) {
					chname = step.getChamps().get(i).getChpNom().substring(3);
				}else {
					chname = step.getChamps().get(i).getChpNom();
				}
				columnDefinitions.append("','"+chname);
				
			}
		}
		StringBuffer buffer = new StringBuffer();
		StringBuffer imports = new StringBuffer();
		StringBuffer verifyCall = new StringBuffer();
		StringBuffer conditionTaskTraites = new StringBuffer();
		for(int k=0 ; k<forms.size();k++) {
			buffer.append(WORKFLOW_TRAITEMENT_FUNCTIONS.replaceAll("#formName", forms.get(k).getFrmNom()).replaceAll("#FormName", capitalize(forms.get(k).getFrmNom())));
			imports.append("import { Add"+capitalize(forms.get(k).getFrmNom())+"Component } from '../add-"+forms.get(k).getFrmNom()+"/add-"+forms.get(k).getFrmNom()+".component';\n");
			verifyCall.append("this.verify"+capitalize(forms.get(k).getFrmNom())+"();\n");
			conditionTaskTraites.append("if(this."+forms.get(k).getFrmNom()+"){\n"
					+ "traitant = 't"+(k+1)+"';"
					+ "}\n");
		}
		columnDefinitions.append("','action'");
		contentBuffer = replaceAll(contentBuffer, "#mainFunctions", mainFunctions);
		contentBuffer = replaceAll(contentBuffer, "#columnDefinitions", columnDefinitions.toString());
		contentBuffer = replaceAll(contentBuffer, "#listetraitementFunctions",buffer.toString());
		contentBuffer = replaceAll(contentBuffer,"#listeImports",imports.toString());
		contentBuffer = replaceAll(contentBuffer,"#verifyTraitementCall",verifyCall.toString());
		contentBuffer = replaceAll(contentBuffer,"#conditionTaskTraites",conditionTaskTraites.toString());

		contentBuffer = new StringBuffer(contentBuffer.toString().replaceAll("#module", formV2.getFrmNom()).replaceAll("#Module", capitalize(formV2.getFrmNom())));
		
		return FileUtils.saveFile(FRONT_SOURCE_PATH + "/" + formV2.getFrmNom() + "/components/main-content",
				"main-content.component.ts",contentBuffer);

	}

	public static boolean createEditStepperHtml(FormulaireV2 formV2) {
		boolean process = false;

		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		String template = "templates/angular/";
		boolean haveSteps = false;
		if (formV2.getSteps().size() > 1) {
			template += "editWithStep";
			haveSteps = true;
		} else {
			template += "editWithoutStep";

		}
		;

		File file = new File(classLoader.getResource(template).getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);

		StringBuffer stepsContent = new StringBuffer();
		String options = "";
		if (contentBuffer != null) {
			for (int i = 0; i < formV2.getSteps().size(); i++) {
				Step step = formV2.getSteps().get(i);
				StringBuffer currentStep = new StringBuffer();
				if (haveSteps) {
					String editStepper = EDIT_STEPPER.replaceAll("#module", formV2.getFrmNom());
					if (i == 0) {
						editStepper = editStepper.replaceAll("#stepButtons", EDIT_STEPPER_BUTTON_START);
					} else if (i == formV2.getSteps().size() - 1) {
						editStepper = editStepper.replaceAll("#stepButtons", EDIT_STEPPER_BUTTON_END);
					} else {
						editStepper = editStepper.replaceAll("#stepButtons", EDIT_STEPPER_BUTTON);
					}
					editStepper = editStepper.replaceAll("#stepName", step.getStepName());
					currentStep.append(editStepper);
				}else {
					String editStepper = EDIT_STEPPER_NON_STEPPER;
					currentStep.append(editStepper);

				}
				if (step.getChamps().size() > 0) {
					StringBuffer listeChamps = new StringBuffer();

					for (ChampsV2 champs : step.getChamps()) {
						String field = "";
						switch (champs.getChpType()) {
						case "date":
							field = EDIT_FIELD_DATE.replaceAll("#module", formV2.getFrmNom()).replaceAll("#fieldName",
									champs.getChpNom());
							break;
						case "file":
							field = EDIT_INPUT_FILE_HIDDEN.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#fileId", champs.getChpNom())
									+ EDIT_INPUT_FILE.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName",champs.getChpNom());
							break;
						case "textarea":
							field = EDIT_FIELD_TEXTAREA.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom());
							break;
						case "radio":
							options = "";
							if (champs.getValues().size() > 0) {
								for (ValueChampsV2 value : champs.getValues()) {
									String option = EDIT_FIELD_RADIO_OPTION.replaceAll("#module", formV2.getFrmNom())
											.replaceAll("#optionLabel", value.getLabel())
											.replaceAll("#optionValue", value.getValue());
									options += option;
								}

							}
							field = EDIT_FIELD_RADIO.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom()).replaceAll("#radioOptions", options);

							break;
						case "autocomplete":
							options = "";
							if (champs.getValues().size() > 0) {
								for (ValueChampsV2 value : champs.getValues()) {
									String option = EDIT_FIELD_SELECT_OPTION.replaceAll("#module", formV2.getFrmNom())
											.replaceAll("#optionLabel", value.getLabel())
											.replaceAll("#optionValue", value.getValue());
									options += option;
								}
							}
							field = EDIT_FIELD_SELECT.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom()).replaceAll("#selectOptions", options);

							break;
						case "checkbox":
							options = "";
							if (champs.getValues().size() > 0) {
								for (ValueChampsV2 value : champs.getValues()) {
									String option = EDIT_FIELD_SELECT_OPTION.replaceAll("#module", formV2.getFrmNom())
											.replaceAll("#optionLabel", value.getLabel())
											.replaceAll("#optionValue", value.getValue());
									options += option;
								}
							}
							field = EDIT_FIELD_SELECT_MULTIPLE.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom()).replaceAll("#selectOptions", options);


							break;
						case "relation":
							options = "";

							String option = EDIT_FIELD_RELATION_OPTION.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#optionLabel", getFieldNameRelation(champs.getChpChamps()))
									.replaceAll("#options", champs.getChpNom().substring(3)+"s");
							options += option;

							field = EDIT_FIELD_RELATION.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom().substring(3)).replaceAll("#selectOptions", options);

							break;
						default:
							// input type: text,number,email
							field = EDIT_FIELD_INPUT.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#fieldType", champs.getChpType());
							break;
						}
						listeChamps.append(field);

					}
					currentStep = new StringBuffer(currentStep.toString().replaceAll("#stepChamps", listeChamps.toString()));
				}

				stepsContent.append(currentStep);
			}

			contentBuffer = replaceAll(contentBuffer, "#steps", stepsContent.toString());

			process = FileUtils.saveFile(
					FRONT_SOURCE_PATH + "/" + formV2.getFrmNom() + "/components/add-" + formV2.getFrmNom(),
					"add-" + formV2.getFrmNom() + ".component.html", contentBuffer);
		}

		return process;
	}
	
	
	public static boolean createEditStepperHtmlWorkflow(String primaryForm,FormulaireV2 formV2) {
		boolean process = false;

		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		String template = "templates/angular/";
		boolean haveSteps = false;
		if (formV2.getSteps().size() > 1) {
			template += "editWithStep";
			haveSteps = true;
		} else {
			template += "editWithoutStep";

		}
		;

		File file = new File(classLoader.getResource(template).getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);

		StringBuffer stepsContent = new StringBuffer();
		String options = "";
		if (contentBuffer != null) {
			for (int i = 0; i < formV2.getSteps().size(); i++) {
				Step step = formV2.getSteps().get(i);
				StringBuffer currentStep = new StringBuffer();
				if (haveSteps) {
					String editStepper = EDIT_STEPPER.replaceAll("#module", formV2.getFrmNom());
					if (i == 0) {
						editStepper = editStepper.replaceAll("#stepButtons", EDIT_STEPPER_BUTTON_START);
					} else if (i == formV2.getSteps().size() - 1) {
						editStepper = editStepper.replaceAll("#stepButtons", EDIT_STEPPER_BUTTON_END);
					} else {
						editStepper = editStepper.replaceAll("#stepButtons", EDIT_STEPPER_BUTTON);
					}
					editStepper = editStepper.replaceAll("#stepName", step.getStepName());
					currentStep.append(editStepper);
				}else {
					String editStepper = EDIT_STEPPER_NON_STEPPER;
					currentStep.append(editStepper);
				}
				if (step.getChamps().size() > 0) {
					StringBuffer listeChamps = new StringBuffer();

					for (ChampsV2 champs : step.getChamps()) {
						String field = "";
						switch (champs.getChpType()) {
						case "date":
							field = EDIT_FIELD_DATE.replaceAll("#module", formV2.getFrmNom()).replaceAll("#fieldName",
									champs.getChpNom());
							break;
						case "file":
							field = EDIT_INPUT_FILE_HIDDEN.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#fileId", champs.getChpNom())
									+ EDIT_INPUT_FILE.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName",champs.getChpNom());
							break;
						case "textarea":
							field = EDIT_FIELD_TEXTAREA.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom());
							break;
						case "radio":
							options = "";
							if (champs.getValues().size() > 0) {
								for (ValueChampsV2 value : champs.getValues()) {
									String option = EDIT_FIELD_RADIO_OPTION.replaceAll("#module", formV2.getFrmNom())
											.replaceAll("#optionLabel", value.getLabel())
											.replaceAll("#optionValue", value.getValue());
									options += option;
								}

							}
							field = EDIT_FIELD_RADIO.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom()).replaceAll("#radioOptions", options);

							break;
						case "autocomplete":
							options = "";
							if (champs.getValues().size() > 0) {
								for (ValueChampsV2 value : champs.getValues()) {
									String option = EDIT_FIELD_SELECT_OPTION.replaceAll("#module", formV2.getFrmNom())
											.replaceAll("#optionLabel", value.getLabel())
											.replaceAll("#optionValue", value.getValue());
									options += option;
								}
							}
							field = EDIT_FIELD_SELECT.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom()).replaceAll("#selectOptions", options);

							break;
						case "checkbox":
							options = "";
							if (champs.getValues().size() > 0) {
								for (ValueChampsV2 value : champs.getValues()) {
									String option = EDIT_FIELD_SELECT_OPTION.replaceAll("#module", formV2.getFrmNom())
											.replaceAll("#optionLabel", value.getLabel())
											.replaceAll("#optionValue", value.getValue());
									options += option;
								}
							}
							field = EDIT_FIELD_SELECT_MULTIPLE.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom()).replaceAll("#selectOptions", options);


							break;
						case "relation":
							options = "";

							String option = EDIT_FIELD_RELATION_OPTION.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#optionLabel", getFieldNameRelation(champs.getChpChamps()))
									.replaceAll("#options", champs.getChpNom().substring(3)+"s");
							options += option;

							field = EDIT_FIELD_RELATION.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom().substring(3)).replaceAll("#selectOptions", options);

							break;
						default:
							// input type: text,number,email
							field = EDIT_FIELD_INPUT.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#fieldType", champs.getChpType());
							break;
						}
						listeChamps.append(field);

					}
					currentStep = new StringBuffer(currentStep.toString().replaceAll("#stepChamps", listeChamps.toString()));
				}

				stepsContent.append(currentStep);
			}

			contentBuffer = replaceAll(contentBuffer, "#steps", stepsContent.toString());

			process = FileUtils.saveFile(
					FRONT_SOURCE_PATH + "/" + primaryForm + "/components/add-" + formV2.getFrmNom(),
					"add-" + formV2.getFrmNom() + ".component.html", contentBuffer);
		}

		return process;
	}
	
	
	public static boolean createEditScss(String nomModule) {
		return fileContentFromTemplate("templates/angular/editCss", "", "",
				nomModule, FRONT_SOURCE_PATH + nomModule + "/components/add-"+nomModule, "add-"+nomModule + ".component.scss");
	
	}
	
	public static boolean createEditScssWorkflow(String primaryForm,String nomModule) {
		return fileContentFromTemplate("templates/angular/editCss", "", "",
				nomModule, FRONT_SOURCE_PATH + primaryForm + "/components/add-"+nomModule, "add-"+nomModule + ".component.scss");
	
	}
	
	public static boolean createDetailScss(String nomModule) {
		return fileContentFromTemplate("templates/angular/detailCss", "", "",
				nomModule, FRONT_SOURCE_PATH + nomModule + "/components/detail-"+nomModule, "detail-"+nomModule + ".component.scss");
	
	}
	
	public static boolean createDetailScssWorkflow(String nomModule) {
		return fileContentFromTemplate("templates/angular/detailCss", "", "",
				nomModule, FRONT_SOURCE_PATH + nomModule + "/components/view-"+nomModule, "view-"+nomModule + ".component.scss");
	
	}
	
	public static boolean createMainComponentScss(String nomModule) {
		return fileContentFromTemplate("templates/angular/mainComponentCss", "", "",
				nomModule, FRONT_SOURCE_PATH + nomModule + "/components/main-content", "main-content" + ".component.scss");
	
	}
	
	public static boolean createMainComponentScssWorkflow(String nomModule) {
		return fileContentFromTemplate("templates/angular/mainComponentCss", "", "",
				nomModule, FRONT_SOURCE_PATH + nomModule + "/components/main-content",  "main-content.component.scss");
	
	}


	public static boolean createDetailStepperHtml(FormulaireV2 formV2) {
		boolean process = false;

		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		String template = "templates/angular/";
		boolean haveSteps = false;
		if (formV2.getSteps().size() > 1) {
			template += "detailWithStepHtml";
			haveSteps = true;
		} else {
			template += "detailWithoutStepHtml";

		}
		;

		File file = new File(classLoader.getResource(template).getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);

		StringBuffer stepsContent = new StringBuffer();
		String options = "";
		if (contentBuffer != null) {
			for (int i = 0; i < formV2.getSteps().size(); i++) {
				Step step = formV2.getSteps().get(i);
				StringBuffer currentStep = new StringBuffer();
				if (haveSteps) {
					String editStepper = EDIT_STEPPER.replaceAll("#module", formV2.getFrmNom());
					if (i == 0) {
						editStepper = editStepper.replaceAll("#stepButtons", EDIT_STEPPER_BUTTON_START);
					} else if (i == formV2.getSteps().size() - 1) {
						editStepper = editStepper.replaceAll("#stepButtons", DETAIL_STEPPER_BUTTON_END);
					} else {
						editStepper = editStepper.replaceAll("#stepButtons", EDIT_STEPPER_BUTTON);
					}
					editStepper = editStepper.replaceAll("#stepName", step.getStepName());
					currentStep.append(editStepper);
				}
				if (step.getChamps().size() > 0) {
					StringBuffer listeChamps = new StringBuffer();
					
					for (ChampsV2 champs : step.getChamps()) {
						String field = "";
						switch (champs.getChpType()) {
						case "date":
							field = DETAIL_FIELD.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#pipe", "| date:'dd/MM/yyyy'");
							
							break;
						case "file":
							field = DETAIL_INPUT_FILE.replaceAll("#module", formV2.getFrmNom()).replaceAll("#fieldName",
									champs.getChpNom());
							break;
						case "textarea":
							field = DETAIL_FIELD_TEXTAREA.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom());
							break;
						case "radio":

							field = DETAIL_FIELD.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#pipe", "");

							break;
						case "autocomplete":

							field = DETAIL_FIELD.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#pipe", "");

							break;
						case "checkbox":

							field = DETAIL_FIELD.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom()).replaceAll("#pipe", "");

							break;
						case "relation":
			

							field = DETAIL_FIELD_RELATION.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom().substring(3))
									.replaceAll("#champsName", getFieldNameRelation(champs.getChpChamps()))
									.replaceAll("#pipe", "");

							break;
						default:
							// input type: text,number,email
							field = DETAIL_FIELD.replaceAll("#module", formV2.getFrmNom())
									.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#pipe", "");

							break;
						}
						listeChamps.append(field);
					}
					currentStep = new StringBuffer(currentStep.toString().replaceAll("#stepChamps", listeChamps.toString()));
				}

				stepsContent.append(currentStep);
			}

			contentBuffer = replaceAll(contentBuffer, "#steps", stepsContent.toString());

			process = FileUtils.saveFile(
					FRONT_SOURCE_PATH + "/" + formV2.getFrmNom() + "/components/view-" + formV2.getFrmNom(),
					"view-" + formV2.getFrmNom() + ".component.html", contentBuffer);
		}

		return process;
	}

	public static boolean createDetailComponent(String nomModule) {
		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		File file = new File(classLoader.getResource("templates/angular/detailComponent").getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);
		
		contentBuffer = replaceAll(contentBuffer, "#module", nomModule);
		contentBuffer = replaceAll(contentBuffer, "#Module", capitalize(nomModule));

		return FileUtils.saveFile( FRONT_SOURCE_PATH + nomModule + "/components/view-"+nomModule, "view-"+nomModule + ".component.ts", contentBuffer);
		
	}
	

	
	public static boolean createEditComponent(FormulaireV2 formV2) {
		boolean process = false;

		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		String template = "templates/angular/editComponent";
		

		File file = new File(classLoader.getResource(template).getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);
		
		StringBuffer listeFilesArguments = new StringBuffer();
		StringBuffer listeVariables = new StringBuffer();
		
		StringBuffer listeFunctions = new StringBuffer();
		
		StringBuffer listeOnInitInstructions = new StringBuffer();
		
		StringBuffer listeFilesFunctions = new StringBuffer();
		StringBuffer listeFilesTestConditions = new StringBuffer();
		

		StringBuffer stepsContent = new StringBuffer();
		String options = "";
		if (contentBuffer != null) {
			for (int i = 0; i < formV2.getSteps().size(); i++) {
				StringBuffer currentStep = new StringBuffer();
				Step step = formV2.getSteps().get(i);
				if (step.getChamps().size() > 0) {
					StringBuffer listeChamps = new StringBuffer();
					
					for (int j = 0; j < step.getChamps().size(); j++) {
						ChampsV2 champs = step.getChamps().get(j);
						String field = "";
						switch (champs.getChpType()) {
						case "date":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");
							
							
							break;
						case "file":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");
							listeFilesArguments.append(", this."+champs.getChpNom());
							listeFilesFunctions.append(EDIT_COMPONENT_FILE_CODE
									.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#module", formV2.getFrmNom()));
							if(listeFilesTestConditions.toString().isEmpty()) {
								listeFilesTestConditions.append("this."+champs.getChpNom());
							}else {
								listeFilesTestConditions.append(" || this."+champs.getChpNom());
							}
							break;
						case "textarea":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");

							break;
						case "radio":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");


							break;
						case "autocomplete":

							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");


							break;
						case "checkbox":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");
							
							break;
						case "relation":
			
							field = champs.getChpNom().substring(3) + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");
							listeVariables.append(champs.getChpNom().substring(3)+"s : any;");
							listeFunctions.append(""
									+ "liste"+capitalize(champs.getChpNom().substring(3))+"(){\n"
											+ "this."+formV2.getFrmNom()+"Service.liste"+capitalize(champs.getChpNom().substring(3))+"().subscribe((data)=>{\n"
													+ "	if(data.status){\n"
													+ "		this."+champs.getChpNom().substring(3)+"s=data.data;"
													+ "	}\n"
													+ "}"
									+ "}\n");
							listeOnInitInstructions.append("liste"+capitalize(champs.getChpNom().substring(3))+"();");
							
							
							break;
						default:
							
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + ((i==step.getChamps().size()-1)?"\n":",\n");
							
							break;
						}
						listeChamps.append(field);
					}
					currentStep.append(listeChamps);
				}

				stepsContent.append(currentStep);
			}

			String onSubmitCore = (formV2.getNumberOfFile()>0?EDIT_COMPONENT_SUBMIT_FUNCTION_FILES:EDIT_COMPONENT_SUBMIT_FUNCTION)
								.replaceAll("#filesArgument", listeFilesArguments.toString())
								.replaceAll("#Module", capitalize(formV2.getFrmNom()))
								.replaceAll("#module", formV2.getFrmNom())
								.replaceAll("#filesAddedCondition", listeFilesTestConditions.toString())
								
								;
			contentBuffer = replaceAll(contentBuffer, "#forms", stepsContent.toString());
			contentBuffer = replaceAll(contentBuffer, "#componentOnSubmitInstructions", onSubmitCore);
			contentBuffer = replaceAll(contentBuffer, "#componentVariables", listeVariables.toString());
			contentBuffer = replaceAll(contentBuffer, "#componentFunctions", listeFunctions.toString()+ listeFilesFunctions.toString());
			contentBuffer = replaceAll(contentBuffer, "#componentInstructionsOnInit", listeOnInitInstructions.toString());

			contentBuffer = new StringBuffer(contentBuffer.toString().replaceAll("#componentName", formV2.getFrmNom()).replaceAll("#ComponentName", capitalize(formV2.getFrmNom())));
			process = FileUtils.saveFile(
					FRONT_SOURCE_PATH + "/" + formV2.getFrmNom() + "/components/add-" + formV2.getFrmNom(),
					"add-" + formV2.getFrmNom() + ".component.ts", contentBuffer);
		}

		return process;
	}
	
	
	
	public static boolean createEditComponentWorkflow(FormulaireV2 formV2, boolean isPrimary,String primaryForm,List<FormulaireV2> forms) {
		boolean process = false;

		ClassLoader classLoader = FrontGenerator.class.getClassLoader();
		String template =(!isPrimary)? "templates/angular/workflow/editComponent":"templates/angular/workflow/editComponentPrincipale";
		

		File file = new File(classLoader.getResource(template).getFile());

		StringBuffer contentBuffer = new StringBuffer();
		contentBuffer = FileUtils.getFileContent(file);
		
		StringBuffer listeFilesArguments = new StringBuffer();
		StringBuffer listeVariables = new StringBuffer();
		
		StringBuffer listeFunctions = new StringBuffer();
		
		StringBuffer listeOnInitInstructions = new StringBuffer();
		
		StringBuffer listeFilesFunctions = new StringBuffer();
		StringBuffer listeFilesTestConditions = new StringBuffer();
//		StringBuffer listeTraitementFunctions = new StringBuffer();
//		if(isPrimary) {
//			if(forms.size()>0) {
//				for(FormulaireV2 fi : forms) {
//					listeTraitementFunctions.append(WORKFLOW_TRAITEMENT_FUNCTIONS.replaceAll("#formName", fi.getFrmNom())
//							.replaceAll("#FormName", capitalize(fi.getFrmNom())).replaceAll("#module", primaryForm));
//				}
//			}
//		}

		StringBuffer stepsContent = new StringBuffer();
		String options = "";
		if (contentBuffer != null) {
			for (int i = 0; i < formV2.getSteps().size(); i++) {
				StringBuffer currentStep = new StringBuffer();
				Step step = formV2.getSteps().get(i);
				if (step.getChamps().size() > 0) {
					StringBuffer listeChamps = new StringBuffer();
					
					for (int j = 0; j < step.getChamps().size(); j++) {
						ChampsV2 champs = step.getChamps().get(j);
						String field = "";
						switch (champs.getChpType()) {
						case "date":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");
							
							
							break;
						case "file":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");
							listeFilesArguments.append(", this."+champs.getChpNom());
							listeFilesFunctions.append(EDIT_COMPONENT_FILE_CODE
									.replaceAll("#fieldName", champs.getChpNom())
									.replaceAll("#module", formV2.getFrmNom()));
							if(listeFilesTestConditions.toString().isEmpty()) {
								listeFilesTestConditions.append("this."+champs.getChpNom());
							}else {
								listeFilesTestConditions.append(" || this."+champs.getChpNom());
							}
							break;
						case "textarea":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");

							break;
						case "radio":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");


							break;
						case "autocomplete":

							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");


							break;
						case "checkbox":
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");
							
							break;
						case "relation":
			
							field = champs.getChpNom().substring(3) + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");
							listeVariables.append(champs.getChpNom().substring(3)+"s : any;");
							listeFunctions.append(""
									+ "liste"+capitalize(champs.getChpNom().substring(3))+"(){\n"
											+ "this."+formV2.getFrmNom()+"Service.liste"+capitalize(champs.getChpNom().substring(3))+"().subscribe((data)=>{\n"
													+ "	if(data.status){\n"
													+ "		this."+champs.getChpNom().substring(3)+"s=data.data;"
													+ "	}\n"
													+ "}"
									+ "}\n");
							listeOnInitInstructions.append("liste"+capitalize(champs.getChpNom().substring(3))+"();");
							
							
							break;
						default:
							
							field = champs.getChpNom() + ":[''" + (champs.getChpObligatoire()?",Validators.required":"") + "]" + (",\n");
							
							break;
						}
						listeChamps.append(field);
					}
					currentStep.append(listeChamps);
				}

				stepsContent.append(currentStep);
			}

			String onSubmitCore = (formV2.getNumberOfFile()>0?WORKFLOW_EDIT_COMPONENT_SUBMIT_FUNCTION_FILES:WORKFLOW_EDIT_COMPONENT_SUBMIT_FUNCTION)
								.replaceAll("#filesArgument", listeFilesArguments.toString())
								.replaceAll("#Module", capitalize(formV2.getFrmNom()))
								.replaceAll("#module", formV2.getFrmNom())
								.replaceAll("#filesAddedCondition", listeFilesTestConditions.toString())
								;
			String taskUpdate = "";
			taskUpdate = WORKFLOW_TASK_UPDATE_FUNCTION.replaceAll("#componentName", formV2.getFrmNom())
					  .replaceAll("#ComponentName", capitalize(formV2.getFrmNom()));
		
			if(!isPrimary) {
				taskUpdate += WORKFLOW_TASK_UPDATE_FUNCTION.replaceAll("#componentName", primaryForm)
														  .replaceAll("#ComponentName", capitalize(primaryForm));
				
			}
			
			contentBuffer = replaceAll(contentBuffer, "#forms", stepsContent.toString());
			contentBuffer = replaceAll(contentBuffer, "#componentOnSubmitInstructions", onSubmitCore);
			contentBuffer = replaceAll(contentBuffer, "#componentVariables", listeVariables.toString());
			contentBuffer = replaceAll(contentBuffer, "#componentFunctions", listeFunctions.toString()+ listeFilesFunctions.toString());
			contentBuffer = replaceAll(contentBuffer, "#componentInstructionsOnInit", listeOnInitInstructions.toString());
			
			
				contentBuffer = replaceAll(contentBuffer,"#Module",capitalize(primaryForm));
				contentBuffer = replaceAll(contentBuffer,"#module",primaryForm);
			
			
//			if(isPrimary) {
//				contentBuffer = replaceAll(contentBuffer,"#listetraitementFunctions",listeTraitementFunctions.toString());
//			}
			contentBuffer = new StringBuffer(contentBuffer.toString().replaceAll("#componentName", formV2.getFrmNom()).replaceAll("#ComponentName", capitalize(formV2.getFrmNom())));
			contentBuffer = replaceAll(contentBuffer, "#submitUpdateTask", taskUpdate);
			process = FileUtils.saveFile(
					FRONT_SOURCE_PATH + "/" + primaryForm + "/components/add-" + formV2.getFrmNom(),
					"add-" + formV2.getFrmNom() + ".component.ts", contentBuffer);
		}

		return process;
	}

	
	
	public static boolean generateFormulaire(FormulaireV2 formV2) {
		boolean process = false;
		String module = formV2.getFrmNom();

		// generation du module
		createModule(module,null);
		createModuleComponent(module);
		createModuleRouting(module);
		createMainComponent(formV2);
		createMainComponentHtml(module, formV2);
		createMainComponentScss(module);
		
		createEditStepperHtml(formV2);
		createEditScss(module);
		createDetailStepperHtml(formV2);
		createDetailComponent(module);
		createDetailScss(module); 
		createEditComponent(formV2);
		createService(formV2.getFrmNom(),formV2);
		
		
		
		
		// generation des components

		return process;
	}
	
	public static boolean generateWorkflowModule(FormulaireV2 formV2,List<FormulaireV2> forms) {
		boolean process = false;
		
		createModule(formV2.getFrmNom(),forms);
		createModuleComponent(formV2.getFrmNom());
		createModuleRouting(formV2.getFrmNom());
		
		process=createMainComponentHtmlWorkflow(formV2.getFrmNom(), formV2,forms);
		process=createMainComponentScssWorkflow(formV2.getFrmNom());
		process=createMainComponentWorkflow(formV2,forms);
		process=createServiceWorkflow(formV2.getFrmNom(),formV2.getFrmNom(),formV2);
		
		
		
		
		process=createEditComponentWorkflow(formV2,true,formV2.getFrmNom(),forms);
		process=createEditScssWorkflow(formV2.getFrmNom(),formV2.getFrmNom());
		process=createEditStepperHtmlWorkflow(formV2.getFrmNom(),formV2);
		process=createDetailStepperHtml(formV2);
		process=createDetailScssWorkflow(formV2.getFrmNom());
		process=createDetailComponent(formV2.getFrmNom());
		
		
		if(forms.size()>0) {
			for(FormulaireV2 form: forms) {
				process=createEditComponentWorkflow(form,false,formV2.getFrmNom(),forms);
				process=createEditScssWorkflow(formV2.getFrmNom(),form.getFrmNom());
				process=createEditStepperHtmlWorkflow(formV2.getFrmNom(),form);
				process=createServiceWorkflow(formV2.getFrmNom(),form.getFrmNom(),form);

			}
		}

		return process; 
	}

}
