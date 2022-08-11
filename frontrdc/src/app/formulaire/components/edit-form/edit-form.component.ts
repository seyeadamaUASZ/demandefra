import { Component, OnInit, Inject } from '@angular/core';
import { field, value } from 'src/app/global.model';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import swal from 'sweetalert2';
import { DropEffect, DndDropEvent } from 'ngx-drag-drop';
import { Champs } from '../model/champs';
import { FormulaireServiceService } from '../service/formulaireService.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-edit-form',
  templateUrl: './edit-form.component.html',
  styleUrls: ['./edit-form.component.scss']
})
export class EditFormComponent implements OnInit {
  value: value = {
    label: "",
    value: ""
  };
  success = false;
  fieldModels: Array<field> = [
    {
      "_id": "",
      "type": "text",
      "icon": "fa-font",
      "label": "Text",
      "description": "Enter your name",
      "placeholder": "Enter your name",
      "className": "form-control",
      "subtype": "text",
      "regex": "",
      "handle": true
    },
    {
      "_id": "",
      "type": "relation",
      "icon": "fa-font",
      "label": "Relation table",
      "description": "Enter your name",
      "placeholder": "Enter your name",
      "className": "form-control",
      "subtype": "text",
      "regex": "",
      "handle": true,
      "champstable": "Champs de la table relation"
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
      "regex": "^([a-zA-Z0-9_.-]+)@([a-zA-Z0-9_.-]+)\.([a-zA-Z]{2,5})$",
      "errorText": "Please enter a valid email",
      "handle": true
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
      "regex": "^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$",
      "errorText": "Please enter a valid phone number",
      "handle": true
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
      "icon": "fa-calendar",
      "label": "Date",
      "placeholder": "Date",
      "className": "form-control"
    },
    {
      "_id": "",
      "type": "textarea",
      "icon": "fa-text-width",
      "label": "Textarea"
    },
    {
      "_id": "",
      "type": "checkbox",
      "required": true,
      "label": "Checkbox",
      "icon": "fa-list",
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
      "icon": "fa-list-ul",
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
      "icon": "fa-bars",
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
      "icon": "fa-file",
      "label": "File Upload",
      "className": "form-control",
      "subtype": "file"
    }
  ];
  loading: boolean;
  modelFields: Array<field> = [];
  model: any = {
    name: 'App name...',
    description: 'App Description...',
    theme: {
      bgColor: "ffffff",
      textColor: "555555",
      bannerImage: ""
    },
    attributes: this.modelFields
  };
  breakpoint: any;
  report = false;
  reports: any = [];
  formulaire: any;
  formulairegenere: any;
  formulairenotgenere: any;
  descriptionForm: any;
  valueChamps: any = { label: "", value: "", valueChp: null }
  champ: any;
  champs = new Champs;
  fields: any = {
    _id: "",
    type: "",
    icon: "",
    label: "",
    description: "",
    placeholder: "",
    className: "",
    name: "",
    subtype: "t",
    regex: "",
    handle: true,
    values: Array<value>()
  };
  values: Array<value> = []
  icone: any;
  tables: any
  champstable: any
  constructor(private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private dialogRef: MatDialog,
    // private dialogRef: MatDialogRef<AjoutAppComponent>,
    private notification: NotificationService, private userService: FormulaireServiceService, private translate: TranslateService, @Inject(MAT_DIALOG_DATA) public donnee: any,) {
    this.loading = false;
    this.descriptionForm = this.donnee.frmNom

  }

  ngOnInit() {
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 5;
    this.getField();
    this.getIcone();
    this.getTable();
    //this. selectId("td_application");

  }
  getTable() {
    this.userService.getTable().subscribe(data => {
      this.tables = data.data
      //console.log(this.tables)
    })
  }


  selectId(id) {
    this.userService.getColonne(id).subscribe(data => {
      this.champstable = data.data
      console.log(this.champstable)
    })
  }

  listeChamps(champs) {
    this.userService.getColonne(champs).subscribe(data => {
      this.champstable = data.data
      //console.log(this.champstable)
    })
  }
  getIcone() {
    this.userService.getIcone().subscribe(data => {
      this.icone = data.data
    })
  }
  listeFormulairegenerer() {
    this.userService.listeFormulairegenerer().subscribe(res => {
      this.formulairegenere = res.data;
    })
  }
  listeFormulairenotgenerer() {
    this.userService.listeFormulaireNotgenerer().subscribe(res => {
      this.formulairenotgenere = res.data;
    })
  }
  listeFormulaire() {
    this.userService.listeFormulaire().subscribe(res => {
      this.formulaire = res.data;
    })
  }
  getListeTab
  getField() {
    this.userService.fieldChampsByForm(this.donnee.frmId).subscribe(data => {
      this.champ = data.data;

      for (let i = 0; i < this.champ.length; i++) {
        // console.log(JSON.stringify(this.champs[i]))
        this.fields._id = this.champ[i].chpId
        this.fields.label = this.champ[i].chpLabel
        this.fields.className = this.champ[i].chpNom
        this.fields.name = this.champ[i].chpNom
        this.fields.type = this.champ[i].chpType
        this.fields.handle = this.champ[i].chpObligatoire
        this.fields.placeholder = this.champ[i].chpPlaceholder
        this.fields.regex = this.champ[i].chpRegex
        this.fields.icon = this.champ[i].chpIcon
        if (this.champ[i].chpType == 'relation') {
          this.fields.champstable = this.champ[i].chpChamps
        }

        if (this.champ[i].chpType == 'checkbox' || this.champ[i].chpType == 'radio' || this.champ[i].chpType == 'autocomplete') {
          this.userService.fieldValueChamps(this.champ[i].chpId).subscribe(data => {
            this.valueChamps = data.data;

            for (let j = 0; j < this.valueChamps.length; j++) {
              this.value.id = this.valueChamps[j].id
              this.value.label = this.valueChamps[j].label
              this.value.value = this.valueChamps[j].value
              this.values.push(this.value)
              this.value = {}
            }

            this.model.attributes[i].values = this.values
            // alert(JSON.stringify( this.model.attributes[i].values))
            this.values = [];
          })
        }
        this.model.attributes.push(this.fields)
        this.fields = {};

      }
      console.log(this.model.attributes);
    })
  }

  addChamps() {

    //alert("add champs");
    for (let i = 0; i < this.model.attributes.length; i++) {
      this.champs.chpId = this.model.attributes[i]['_id'];
      this.champs.chpLabel = this.model.attributes[i]['label'];
      this.champs.chpNom = this.model.attributes[i]['name'];
      this.champs.chpClass = this.model.attributes[i]['className'];
      this.champs.chpType = this.model.attributes[i]['type'];
      this.champs.chpObligatoire = this.model.attributes[i]['handle'];
      this.champs.chpTaille = this.model.attributes[i]['max'];
      this.champs.chpPlaceholder = this.model.attributes[i]['placeholder'];
      this.champs.chpRegex = this.model.attributes[i]['regex'];
      this.champs.chpIcon = this.model.attributes[i]['icon'];
      this.champs.chFrmId = this.donnee.frmId;
      if (this.model.attributes[i]['type'] == 'relation') {
        this.champs.chpChamps = this.model.attributes[i]['champstable'];
      }
      // suppression pour les fields ayant des values
      // if (this.model.attributes[i]['type'] == 'relation' || this.model.attributes[i]['type'] == 'checkbox' || this.model.attributes[i]['type'] == 'radio' || this.model.attributes[i]['type'] == 'select' || this.model.attributes[i]['type'] == 'autocomplete') {
      //   this.userService.deleteChamps(this.champs.chpId).subscribe(data => {

      //   });
      // }
      this.userService.addChamps(this.champs).subscribe(data => {
        this.notification.info(data.description);
        if (this.model.attributes[i]['type'] == 'checkbox' || this.model.attributes[i]['type'] == 'radio' || this.model.attributes[i]['type'] == 'select' || this.model.attributes[i]['type'] == 'autocomplete') {
          for (let j = 0; j < this.model.attributes[i]['values'].length; j++) {
            this.valueChamps = {id:null, label: null, value: null, valueChp: null };
            this.valueChamps.id = this.model.attributes[i]['values'][j]['id']
            this.valueChamps.label = this.model.attributes[i]['values'][j]['label']
            this.valueChamps.value = this.model.attributes[i]['values'][j]['value']
            this.valueChamps.valueChp = data.data
            this.userService.addValue(this.valueChamps).subscribe(data => {
              this.notification.info(data.description);

            })
          }
        }
        this.userService.modeliserFormulaire(this.donnee).subscribe(data => {
          this.notification.info(data.description);
          //  location.reload();  
        })
        this.closeDialog();

      });
      this.champ = {}

    }

  }
  closeDialog() {
    this.dialogRef.closeAll();

  }
  closepopup() {
    this.dialogRef.closeAll();
  }
  onDragStart(event: DragEvent) {
    console.log("drag started", JSON.stringify(event, null, 2));
  }

  onDragEnd(event: DragEvent) {
    console.log("drag ended", JSON.stringify(event, null, 2));
  }

  onDraggableCopied(event: DragEvent) {
    console.log("draggable copied", JSON.stringify(event, null, 2));
  }

  onDraggableLinked(event: DragEvent) {
    console.log("draggable linked", JSON.stringify(event, null, 2));
  }

  onDragged(item: any, list: any[], effect: DropEffect) {
    if (effect === "move") {
      const index = list.indexOf(item);
      list.splice(index, 1);
    }
  }

  onDragCanceled(event: DragEvent) {
    console.log("drag cancelled", JSON.stringify(event, null, 2));
  }

  onDragover(event: DragEvent) {
    console.log("dragover", JSON.stringify(event, null, 2));
  }

  onDrop(event: DndDropEvent, list?: any[]) {
    if (list && (event.dropEffect === "copy" || event.dropEffect === "move")) {

      if (event.dropEffect === "copy")
        event.data.name = event.data.type + '-' + new Date().getTime();
      let index = event.index;
      if (typeof index === "undefined") {
        index = list.length;
      }
      list.splice(index, 0, event.data);
    }
  }

  addValue(values) {
    values.push(this.value);
    this.value = { label: "", value: "" };
  }

  removeField(i, champs) {
    let alertSupp;
    this.translate.get('formulaire.confirm-suppression').subscribe((res: string) => {
      alertSupp = res;
    });
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("configuration.alert-suppression", message);
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      maxWidth: "400px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      if (dialogResult === true) {
        this.loading = true;

        this.userService.deleteChamps(champs._id).subscribe(data => {
          this.loading = false;

        });

        this.notification.info(alertSupp);
        this.model.attributes.splice(i, 1);
      }
    });
    // swal({
    //   title: 'Are you sure?',
    //   text: "Do you want to remove this field?",
    //   type: 'warning',
    //   showCancelButton: true,
    //   confirmButtonColor: '#00B96F',
    //   cancelButtonColor: '#d33',
    //   confirmButtonText: 'Yes, remove!'
    // }).then((result) => {
    //   if (result.value) {
    //       this.userService.deleteChamps(champs._id).subscribe(data => {
    //       });
    //     this.model.attributes.splice(i,1);
    //   }
    // });

  }

  updateForm() {
    let input = new FormData;
    input.append('id', this.model._id);
    input.append('name', this.model.name);
    input.append('description', this.model.description);
    input.append('bannerImage', this.model.theme.bannerImage);
    input.append('bgColor', this.model.theme.bgColor);
    input.append('textColor', this.model.theme.textColor);
    input.append('attributes', JSON.stringify(this.model.attributes));

    // this.us.putDataApi('/admin/updateForm',input).subscribe(r=>{
    //   console.log(r);
    //   swal('Success','App updated successfully','success');
    // });
  }


  initReport() {
    this.report = true;
    let input = {
      id: this.model._id
    }

    // this.us.getDataApi('/admin/allFilledForms',input).subscribe(r=>{
    //   this.reports = r.data;
    //   console.log('reports',this.reports);
    //   this.reports.map(records=>{
    //     return records.attributes.map(record=>{
    //       if(record.type=='checkbox'){
    //         record.value = record.values.filter(r=>r.selected).map(i=>i.value).join(',');
    //       }
    //     })
    //   });
    // });
  }



  toggleValue(item) {
    item.selected = !item.selected;
  }

  submit() {
    let valid = true;
    let validationArray = JSON.parse(JSON.stringify(this.model.attributes));
    validationArray.reverse().forEach(field => {
      console.log(field.label + '=>' + field.required + "=>" + field.value);
      if (field.required && !field.value && field.type != 'checkbox') {
        Swal.fire('Error', 'Please enter ' + field.label, 'error');
        valid = false;
        return false;
      }
      if (field.required && field.regex) {
        let regex = new RegExp(field.regex);
        if (regex.test(field.value) == false) {
          Swal.fire('Error', field.errorText, 'error');
          valid = false;
          return false;
        }
      }
      if (field.required && field.type == 'checkbox') {
        if (field.values.filter(r => r.selected).length == 0) {
          Swal.fire('Error', 'Please enterrr ' + field.label, 'error');
          valid = false;
          return false;
        }

      }
    });
    if (!valid) {
      return false;
    }
    console.log('Save', this.model);
    let input = new FormData;
    input.append('formId', this.model._id);
    input.append('attributes', JSON.stringify(this.model.attributes))
    // this.us.postDataApi('/user/formFill',input).subscribe(r=>{
    //   console.log(r);
    //   swal('Success','You have contact sucessfully','success');
    //   this.success = true;
    // },error=>{
    //   swal('Error',error.message,'error');
    // });

  }
}
