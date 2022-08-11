import { Component, OnInit, Inject } from '@angular/core';
import { field, value } from 'src/app/global.model';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import Swal from 'sweetalert2';
import { DropEffect, DndDropEvent } from 'ngx-drag-drop';
import { FichierService } from '../../services/fichier.service';
import { Champs } from '../../models/champs'

@Component({
  selector: 'app-edit-form',
  templateUrl: './edit-form.component.html',
  styleUrls: ['./edit-form.component.scss']
})
export class EditFormComponent implements OnInit {
  value:value={
    label:"",
    value:""
  };
  success = false;
  fieldModels:Array<field>=[
    {
      "_id": "",
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
      "_id": "",
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
      "_id": "",
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
      "_id": "",
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
      "_id": "",
      "type": "date",
      "icon":"fa-calendar",
      "label": "Date",
      "placeholder": "Date",
      "className": "form-control"
    },
    {
      "_id": "",
      "type": "datetime-local",
      "icon":"fa-calendar",
      "label": "DateTime",
      "placeholder": "Date Time",
      "className": "form-control"
    },
    {
      "_id": "",
      "type": "textarea",
      "icon":"fa-text-width",
      "label": "Textarea"
    },
    {
      "_id": "",
      "type": "paragraph",
      "icon": "fa-paragraph",
      "label": "Paragraph",
      "placeholder": "Type your text to display here only"
    },
    {
      "_id": "",
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
      "_id": "",
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
      "_id": "",
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
      "_id": "",
      "type": "file",
      "icon":"fa-file",
      "label": "File Upload",
      "className": "form-control",
      "subtype": "file"
    },
    {
      "_id": "",
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
champs:any;
champsRapport = new Champs;
fields:any={
  _id: "",
  type: "",
  icon: "",
  label: "",
  description: "",
  name: "",
  placeholder: "",
  className: "",
  subtype: "t",
  regex : "",
  handle:true
};
constructor( private router: Router,
  private route: ActivatedRoute,
  private dialog: MatDialog,
  private dialogRef: MatDialog,
  private snackBar: MatSnackBar,private fichierService:FichierService,
  private translate: TranslateService,
  @Inject(MAT_DIALOG_DATA) public donnee: any,
  ) {
    this.loading=false;
   }

  ngOnInit() {
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 5;
    this.getField();
  }
  getField(){
    this.fichierService.fieldChampsByRapport(this.donnee).subscribe(data=>{
      this.champs = data.data;
      for(let i=0; i<this.champs.length; i++){
        this.fields._id= this.champs[i].crtId
        this.fields.label= this.champs[i].crtLabel
        this.fields.name=this.champs[i].crtNom
        this.fields.className=this.champs[i].crtClass
        this.fields.type=this.champs[i].crtType
        this.fields.handle=this.champs[i].crtObligatoire
        this.fields.placeholder=this.champs[i].crtPlaceholder
        this.fields.regex=this.champs[i].crtRegex
        this.fields.icon=this.champs[i].crtIcon
        this.model.attributes.push(this.fields)
        this.fields={};

      }
    })
  }

 addChamps(){
  for(let i=0; i<this.model.attributes.length; i++){
    this.champsRapport.crtId=this.model.attributes[i]['_id'];
    this.champsRapport.crtLabel=this.model.attributes[i]['label'];
    this.champsRapport.crtNom=this.model.attributes[i]['name'];
    this.champsRapport.crtClass=this.model.attributes[i]['className'];
    this.champsRapport.crtType=this.model.attributes[i]['type'];
    this.champsRapport.crtObligatoire=this.model.attributes[i]['handle'];
    this.champsRapport.crtTaille=this.model.attributes[i]['max'];
    this.champsRapport.crtPlaceholder=this.model.attributes[i]['placeholder'];
    this.champsRapport.crtRegex=this.model.attributes[i]['regex'];
    this.champsRapport.crtIcon=this.model.attributes[i]['icon'];
    this.champsRapport.crtRptId=this.donnee;
    this.fichierService.addChamps(this.champsRapport).subscribe(data => {
      this.snackBar.open(data.description, 'Verification', {
        duration: 2000,
      });
      this.closeDialog();
    });
    this.champs={}

   }

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
    console.log("dragover", JSON.stringify(event, null, 1));
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

  removeField(i, champs){
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
        if(champs._id) {
          this.fichierService.supprimerChamps(champs._id).subscribe(data => {
          });
        }
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
    let input = new FormData;
    input.append('formId',this.model._id);
    input.append('attributes',JSON.stringify(this.model.attributes))

  }
  closeDialog() {
    this.dialogRef.closeAll();
  }
}
