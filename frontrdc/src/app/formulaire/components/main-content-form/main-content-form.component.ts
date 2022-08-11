import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatPaginator, MatSnackBar, MatTableDataSource } from '@angular/material';
import { AjouterFormComponent } from '../ajouter-form/ajouter-form.component';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { TranslateService } from '@ngx-translate/core';
import { DndDropEvent,DropEffect} from 'ngx-drag-drop';
import Swal from 'sweetalert2';
import { field, value} from 'src/app/global.model';
import { Champs } from 'src/app/utilisateur/models/champs';
import { Formulaire } from 'src/app/utilisateur/models/formulaire';
import { ViewFormComponent } from '../view-form/view-form.component';
import { EditFormComponent } from '../edit-form/edit-form.component';
import { ConfirmDialogModel, ConfirmDialogComponent } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { ThemeService } from 'ng2-charts';
import { NotificationService } from '../../../shared/services/notification.service';
import { FormulaireServiceService } from '../service/formulaireService.service';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable,merge } from 'rxjs';
@Component({
  selector: 'app-main-content-form',
  templateUrl: './main-content-form.component.html',
  styleUrls: ['./main-content-form.component.scss']
})
export class MainContentFormComponent implements OnInit {
  value:value={
    label:"",
    value:""
  };
  success = false;
  fieldModels:Array<field>=[
    {
      "type": "text",
      "icon": "fa-font",
      "label": "Text",
      "description": "Enter your name",
      "placeholder": "Enter your name",
      "className": "form-control",
      "subtype": "text",
      "regex" : "",
      "handle":true
    },
    {
      "type": "email",
      "icon": "fa-envelope",
      "required": true,
      "label": "Email",
      "description": "Enter your email",
      "placeholder": "Enter your email",
      "className": "form-control",
      "subtype": "text",
      "regex" : "^([a-zA-Z0-9_.-]+)@([a-zA-Z0-9_.-]+)\.([a-zA-Z]{2,5})$",
      "errorText": "Please enter a valid email",
      "handle":true
    },
    {
      "type": "phone",
      "icon": "fa-phone",
      "label": "Phone",
      "description": "Enter your phone",
      "placeholder": "Enter your phone",
      "className": "form-control",
      "subtype": "text",
      "regex" : "^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$",
      "errorText": "Please enter a valid phone number",
      "handle":true
    },
    {
      "type": "number",
      "label": "Number",
      "icon": "fa-html5",
      "description": "Age",
      "placeholder": "Enter your age",
      "className": "form-control",
      "value": "20",
      "min": 12,
      "max": 90
    },
    {
      "type": "date",
      "icon":"fa-calendar",
      "label": "Date",
      "placeholder": "Date",
      "className": "form-control"
    },
    {
      "type": "datetime-local",
      "icon":"fa-calendar",
      "label": "DateTime",
      "placeholder": "Date Time",
      "className": "form-control"
    },
    {
      "type": "textarea",
      "icon":"fa-text-width",
      "label": "Textarea"
    },
    {
      "type": "paragraph",
      "icon": "fa-paragraph",
      "label": "Paragraph",
      "placeholder": "Type your text to display here only"
    },
    {
      "type": "checkbox",
      "required": true,
      "label": "Checkbox",
      "icon":"fa-list",
      "description": "Checkbox",
      "inline": true,
      "values": [
        {
          "label": "Option 1",
          "value": "option-1"
        },
        {
          "label": "Option 2",
          "value": "option-2"
        }
      ]
    },
    {
      "type": "radio",
      "icon":"fa-list-ul",
      "label": "Radio",
      "description": "Radio boxes",
      "values": [
        {
          "label": "Option 1",
          "value": "option-1"
        },
        {
          "label": "Option 2",
          "value": "option-2"
        }
      ]
    },
    {
      "type": "autocomplete",
      "icon":"fa-bars",
      "label": "Select",
      "description": "Select",
      "placeholder": "Select",
      "className": "form-control",
      "values": [
        {
          "label": "Option 1",
          "value": "option-1"
        },
        {
          "label": "Option 2",
          "value": "option-2"
        },
        {
          "label": "Option 3",
          "value": "option-3"
        }
      ]
    },
    {
      "type": "file",
      "icon":"fa-file",
      "label": "File Upload",
      "className": "form-control",
      "subtype": "file"
    },
    {
      "type": "button",
      "icon":"fa-paper-plane",
      "subtype": "submit",
      "label": "Submit"
    }
  ];
  loading: boolean;
  modelFields:Array<field>=[];
  model:any = {
    name:'App name...',
    description:'App Description...',
    theme:{
      bgColor:"ffffff",
      textColor:"555555",
      bannerImage:""
    },
    attributes:this.modelFields
  };
  breakpoint:any;
  report = false;
  reports:any = [];
formulaire:any;
formulairegenere:any;
formulairenotgenere:any;
fields:any=[];
champs:any={chpClass:"",chpLabel:"",chpNom:"",chpObligatoire:"",chpPlaceholder:"",chpTaille:"",chpType:"",chpRegex:"",chpIcon:"",chFrmId:0};
champsd:any={chpClass:"",chpLabel:"",chpNom:"",chpObligatoire:"",chpPlaceholder:"",chpTaille:"",chpType:"",chpRegex:"",chpIcon:"",chFrmId:0};
dupliForm:any={frmDescription:"",frmNom:""}

dataSourceFormulaireNotGeneres: MatTableDataSource<Formulaire>;
displayedColumnsFormulaireNotGeneres = ['frmNom','frmDescription','action'];
dataSourceFormulaireGeneres: MatTableDataSource<Formulaire>;
displayedColumnsFormulaireGeneres = ['frmNom','frmDescription','frmStatus','action'];
@ViewChildren(MatPaginator) paginator = new QueryList<MatPaginator>();

  constructor( private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private dialogRef: MatDialog,
   // private dialogRef: MatDialogRef<AjoutAppComponent>,
    private notification: NotificationService,private userService:FormulaireServiceService, private translate: TranslateService) {
      this.loading=false;
     }

     applyFilterNotGeneres(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSourceFormulaireNotGeneres.filter = filterValue.trim().toLowerCase();
    }
    applyFilterGeneres(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSourceFormulaireGeneres.filter = filterValue.trim().toLowerCase();
    }
  ngOnInit() {
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 5;
    this.listeFormulaire();
    this.listeFormulairegenerer();
    this.listeFormulairenotgenerer();
  }

  form: FormGroup = new FormGroup({
		frmNom: new FormControl(false),
		frmDescription: new FormControl(false),
    frmStatus: new FormControl(false),
		action: new FormControl(false),


	});

  form1: FormGroup = new FormGroup({
		frmNom: new FormControl(false),
		frmDescription: new FormControl(false),
		action: new FormControl(false),


	});

	frmNom = this.form.get('frmNom');
	frmDescription = this.form.get('frmDescription');
  frmStatus = this.form.get('frmStatus');
	action = this.form.get('action');

  frmNom1 = this.form1.get('frmNom');
	frmDescription1 = this.form1.get('frmDescription');
	action1 = this.form1.get('action');

	columnDefinitions = [
		{ def: 'frmNom', label: 'formulaire.frmNom', hide: false },
		{ def: 'frmDescription', label: 'formulaire.frmDescription', hide: false },
    { def: 'frmStatus', label: 'formulaire.frmStatus', hide: false },
		{ def: 'action', label: 'register.action', hide: false },

	]

  columnDefinitionsNoGeneres = [
		{ def: 'frmNom', label: 'formulaire.frmNom', hide: false },
		{ def: 'frmDescription', label: 'formulaire.frmDescription', hide: false },
		{ def: 'action', label: 'register.action', hide: false },

	]
	getDisplayedColumns(): string[] {
		return this.columnDefinitions.filter(cd => !cd.hide).map(cd => cd.def);
	}

  getDisplayedColumnsNoGeneres(): string[] {
		return this.columnDefinitionsNoGeneres.filter(cd => !cd.hide).map(cd => cd.def);
	}


	
	ngAfterViewInit() {
		let o1: Observable<boolean> = this.frmNom.valueChanges;
		let o2: Observable<boolean> = this.frmDescription.valueChanges;
		let o3: Observable<boolean> = this.frmStatus.valueChanges;
    let o4: Observable<boolean> = this.action.valueChanges;

		merge(o1, o2, o3,o4).subscribe(v => {
			this.columnDefinitions[0].hide = this.frmNom.value;
			this.columnDefinitions[1].hide = this.frmDescription.value;
      this.columnDefinitions[2].hide = this.frmStatus.value;
			this.columnDefinitions[3].hide = this.action.value;
			
		});


    let o11: Observable<boolean> = this.frmNom1.valueChanges;
		let o22: Observable<boolean> = this.frmDescription1.valueChanges;
    let o33: Observable<boolean> = this.action1.valueChanges;

		merge(o11, o22, o33).subscribe(v => {
			this.columnDefinitionsNoGeneres[0].hide = this.frmNom1.value;
			this.columnDefinitionsNoGeneres[1].hide = this.frmDescription1.value;
			this.columnDefinitionsNoGeneres[2].hide = this.action1.value;
			
		});

	}

  openDialogAddForm(): void {
    const dialog1 = this.dialog.open(AjouterFormComponent, {
      disableClose: true,
      // width: '700px',

    }).afterClosed().subscribe(result => {
			location.reload();
			this.listeFormulairenotgenerer();
		});

  }

  getField(data){
    this.userService.fieldChampsByForm(data).subscribe(data=>{
      this.fields = data.data;
    })
  }
  listeFormulaire(){
    this.userService.listeFormulaire().subscribe(res=>{
      this.formulaire=res.data;
    })
  }
  listeFormulairegenerer(){
    this.userService.listeFormulairegenerer().subscribe(res=>{
      this.formulairegenere=res.data;
      this.dataSourceFormulaireGeneres = new MatTableDataSource(this.formulairegenere);
      this.dataSourceFormulaireGeneres.paginator = this.paginator.toArray()[1];
    })
  }
 validerFormulaire(data){
  if(!data.frmModeliser){
    this.notification.error("Formulaire non  modelisé");
  }else{
    this.userService.validerFormulaire(data).subscribe(res=>{
      this.notification.info(res.description);
      this.listeFormulairenotgenerer();
      this.listeFormulairegenerer();
    })
  }

  }
  modeliserFormulaire(data){
    this.userService.modeliserFormulaire(data).subscribe(res=>{
      this.notification.info(res.description);
      this.listeFormulairenotgenerer();
      this.listeFormulairegenerer();
    })
  }
  listeFormulairenotgenerer(){
    this.userService.listeFormulaireNotgenerer().subscribe(res=>{
      this.formulairenotgenere=res.data;
      this.dataSourceFormulaireNotGeneres = new MatTableDataSource(this.formulairenotgenere);
      this.dataSourceFormulaireNotGeneres.paginator = this.paginator.toArray()[0];
    })
  }
  onDragStart(event:DragEvent) {
    console.log("drag started", JSON.stringify(event, null, 2));
  }

  onDragEnd(event:DragEvent) {
    console.log("drag ended", JSON.stringify(event, null, 2));
  }

  onDraggableCopied(event:DragEvent) {
    console.log("draggable copied", JSON.stringify(event, null, 2));
  }

  onDraggableLinked(event:DragEvent) {
    console.log("draggable linked", JSON.stringify(event, null, 2));
  }

   onDragged( item:any, list:any[], effect:DropEffect ) {
    if( effect === "move" ) {
      const index = list.indexOf( item );
      list.splice( index, 1 );
    }
  }

  onDragCanceled(event:DragEvent) {
    console.log("drag cancelled", JSON.stringify(event, null, 2));
  }

  onDragover(event:DragEvent) {
    console.log("dragover", JSON.stringify(event, null, 2));
  }

  onDrop( event:DndDropEvent, list?:any[] ) {
    if( list && (event.dropEffect === "copy" || event.dropEffect === "move") ) {

      if(event.dropEffect === "copy")
      event.data.name = event.data.type+'-'+new Date().getTime();
      let index = event.index;
      if( typeof index === "undefined" ) {
        index = list.length;
      }
      list.splice( index, 0, event.data );
    }
  }

  addValue(values){
    values.push(this.value);
    this.value={label:"",value:""};
  }

  removeField(i){
    /*Swal.fire({
      title: 'Are you sure?',
      text: "Do you want to remove this field?",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#00B96F',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, remove!'
    }).then((result) => {
      if (result.value) {
        this.model.attributes.splice(i,1);
      }
    });*/

  }

  updateForm(){
    let input = new FormData;
    input.append('id',this.model._id);
    input.append('name',this.model.name);
    input.append('description',this.model.description);
    input.append('bannerImage',this.model.theme.bannerImage);
    input.append('bgColor',this.model.theme.bgColor);
    input.append('textColor',this.model.theme.textColor);
    input.append('attributes',JSON.stringify(this.model.attributes));
  }


  initReport(){
    this.report = true;
    let input = {
      id:this.model._id
    }

  }



  toggleValue(item){
    item.selected = !item.selected;
  }

  submit(){
    let valid = true;
    let validationArray = JSON.parse(JSON.stringify(this.model.attributes));
    validationArray.reverse().forEach(field => {
      console.log(field.label+'=>'+field.required+"=>"+field.value);
      if(field.required && !field.value && field.type != 'checkbox'){
        Swal.fire('Error','Please enter '+field.label,'error');
        valid = false;
        return false;
      }
      if(field.required && field.regex){
        let regex = new RegExp(field.regex);
        if(regex.test(field.value) == false){
          Swal.fire('Error',field.errorText,'error');
          valid = false;
          return false;
        }
      }
      if(field.required && field.type == 'checkbox'){
        if(field.values.filter(r=>r.selected).length == 0){
          Swal.fire('Error','Please enterrr '+field.label,'error');
          valid = false;
          return false;
        }

      }
    });
    if(!valid){
      return false;
    }
    console.log('Save',this.model);
    let input = new FormData;
    input.append('formId',this.model._id);
    input.append('attributes',JSON.stringify(this.model.attributes))

  }
  addChamps(ch_frm_id){

    for(let i=0; i<this.model.attributes.length; i++){
      this.champs.chpId=this.model.attributes[i]['_id'];
      this.champs.chpLabel=this.model.attributes[i]['label'];
      this.champs.chpNom=this.model.attributes[i]['name'];
      this.champs.chpClass=this.model.attributes[i]['className'];
      this.champs.chpType=this.model.attributes[i]['type'];
      this.champs.chpObligatoire=this.model.attributes[i]['handle'];
      this.champs.chpTaille=this.model.attributes[i]['max'];
      this.champs.chpPlaceholder=this.model.attributes[i]['placeholder'];
      this.champs.chpRegex=this.model.attributes[i]['regex'];
      this.champs.chpIcon=this.model.attributes[i]['icon'];
      this.champs.chFrmId=ch_frm_id;

      this.userService.addChamps(this.champs).subscribe(data => {
        this.notification.info(data.description);
      })
      this.champs={}

     }

   }
 genererFormulaire(id){
  this.loading=true;
   this.userService.genererFormulaire("frontrdc",id).subscribe(data=>{
    this.loading = false;
    this.notification.info(data.description);
    this.listeFormulairenotgenerer();
    this.listeFormulairegenerer();
   })
 }
 delete(id,message){
  this.loading=true;
   this.userService.supprimerFormulaire("frontrdc",id).subscribe(data=>{
    this.loading = false;
    this.notification.info(message);
    this.listeFormulairenotgenerer();
    this.listeFormulairegenerer();
   })
 }

 supprimerFormulaire(utiId) {
  let alertSupp;
  this.translate.get('formulaire.confirm-supp').subscribe((res: string) => {
    alertSupp = res;
  });
  const message = "Alert.confirm-action";
  const dialogData = new ConfirmDialogModel("formulaire.alert-suppression", message);
  const dialogRef = this.dialogRef.open(ConfirmDialogComponent, {
    disableClose: true,
    maxWidth: "400px",
    data: dialogData
  });
  dialogRef.afterClosed().subscribe(dialogResult => {
    if (dialogResult === true) {
      this.delete(utiId, alertSupp);
    }
  });
}

openDialogDupliquer(form) {
  let messageDesactive;
  this.translate.get('formulaire.confirmedupliquer').subscribe((res: string) => {
    messageDesactive = res;
  });
  const message = "Alert.confirm-action";
  const dialogData = new ConfirmDialogModel("formulaire.confirmedupliquer", message);
  const dialogRef = this.dialog.open(ConfirmDialogComponent, {
    disableClose: true,
    maxWidth: "400px",
    data: dialogData
  });
  dialogRef.afterClosed().subscribe(dialogResult => {
    if (dialogResult === true) {
      this.dupliquer(form);
    }
  });
}

dupliquer(m){
  this.dupliForm.frmDescription=m.frmDescription
  this.dupliForm.frmNom=m.frmNom+"-Dupliqué"

  this.userService.addFormulaire(this.dupliForm).subscribe(res=>{
    this.userService.fieldChampsByForm(m.frmId).subscribe(data=>{
      this.champs = data.data;
      this.champsd.chFrmId=res.data.frmId

      for(let i=0; i<this.champs.length; i++){
        this.champsd.chpClass=this.champs[i].chpClass
        this.champsd.chpLabel=this.champs[i].chpLabel
        this.champsd.chpNom=this.champs[i].chpNom
        this.champsd.chpObligatoire=this.champs[i].chpObligatoire
        this.champsd.chpTaille=this.champs[i].chpTaille
        this.champsd.chpType=this.champs[i].chpType
        this.champsd.chpRegex=this.champs[i].chpRegex
        this.champsd.chpIcon=this.champs[i].chpIcon
        this.champsd.chpPlaceholder=this.champs[i].chpPlaceholder
        console.log(this.champsd)

        this.userService.addChamps(this.champsd).subscribe(data => {
          this.notification.info(data.description);
        })

      }


    })
    this.listeFormulairenotgenerer();
    this.listeFormulairegenerer();
  })
}

 openDialogViewForm(data): void {
  const dialog = this.dialogRef.open(ViewFormComponent, {
    disableClose: true,
    width: '40%',
    data: data
  }).afterClosed().subscribe(result => {


  });

}
openDialogEditForm(data): void {
  const dialog = this.dialogRef.open(EditFormComponent, {
    disableClose: true,
    // width: '1000px',
    data: data
  }).afterClosed().subscribe(result => {


  });

}
}
