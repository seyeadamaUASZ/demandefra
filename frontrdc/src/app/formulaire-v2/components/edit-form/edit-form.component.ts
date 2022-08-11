import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DndDropEvent, DropEffect } from 'ngx-drag-drop';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { Formulairev2Service } from '../../services/formulairev2.service';

@Component({
  selector: 'app-edit-form',
  templateUrl: './edit-form.component.html',
  styleUrls: ['./edit-form.component.scss']
})
export class EditFormComponent implements OnInit {
  statusDialog: boolean = false; // permet de faire un reload à la fermeture si un element du formulaire est modifiee
  tables: any;
  champstable: any;
  icone: any;
  value = { label: '', value: '' };
  constructor(
    private dialog: MatDialogRef<EditFormComponent>,
    private dialog2: MatDialog,
    private notification: NotificationService,
    private formulaireService: Formulairev2Service,
    private translate: TranslateService,
    @Inject(MAT_DIALOG_DATA) public donnee: any,) {

  }
  formChampsList: FormChampsList = { frmNom: '', frmDescription: '',
    numberOfFile: 0,
   steps: [{ stepName: '', frmId: null, champs: [] }] };
  fieldModels: Array<any> = [
    {
      "_id": "",
      "chpType": "text",
      "chpIcon": "fa-font",
      "chpLabel": "Text",
      "chpObligatoire": false,
      "chpDescription": "Enter your name",
      "chpPlaceholder": "Enter your name",
      "className": "form-control",
      "subtype": "text",
      "chpRegex": "",
      "handle": true
    },
    {
      "_id": "",
      "chpType": "relation",
      "chpIcon": "fa-font",
      "chpLabel": "Relation table",
      "chpObligatoire": false,
      "chpDescription": "Enter your name",
      "chpPlaceholder": "Enter your name",
      "className": "form-control",
      "subtype": "text",
      "chpRegex": "",
      "handle": true,
      "champstable": "Champs de la table relation"
    },
    {
      "_id": "",
      "chpType": "email",
      "chpIcon": "fa-envelope",
      "chpObligatoire": true,
      "chpLabel": "Email",
      "chpDescription": "Enter your email",
      "chpPlaceholder": "Enter your email",
      "className": "form-control",
      "subtype": "text",
      "chpRegex": "^([a-zA-Z0-9_.-]+)@([a-zA-Z0-9_.-]+)\.([a-zA-Z]{2,5})$",
      "errorText": "Please enter a valid email",
      "handle": true
    },
    {
      "_id": "",
      "chpType": "phone",
      "chpIcon": "fa-phone",
      "chpLabel": "Phone",
      "chpObligatoire": false,
      "chpDescription": "Enter your phone",
      "chpPlaceholder": "Enter your phone",
      "className": "form-control",
      "subtype": "text",
      "chpRegex": "^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$",
      "errorText": "Please enter a valid phone number",
      "handle": true
    },
    {
      "_id": "",
      "chpType": "number",
      "chpLabel": "Number",
      "chpObligatoire": false,
      "chpIcon": "fa-html5",
      "chpDescription": "Age",
      "chpPlaceholder": "Enter your age",
      "className": "form-control",
      "value": "20",
      "min": 12,
      "max": 90
    },
    {
      "_id": "",
      "chpType": "date",
      "chpObligatoire": false,
      "chpIcon": "fa-calendar",
      "chpLabel": "Date",
      "chpPlaceholder": "Date",
      "className": "form-control"
    },
    {
      "_id": "",
      "chpType": "textarea",
      "chpObligatoire": false,
      "chpIcon": "fa-text-width",
      "chpLabel": "Textarea"
    },
    {
      "_id": "",
      "chpType": "checkbox",
      "chpObligatoire": true,
      "chpLabel": "Checkbox",
      "chpIcon": "fa-list",
      "chpDescription": "Checkbox",
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
      "chpType": "radio",
      "chpIcon": "fa-list-ul",
      "chpObligatoire": false,
      "chpLabel": "Radio",
      "chpDescription": "Radio boxes",
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
      "chpType": "autocomplete",
      "chpObligatoire": false,
      "chpIcon": "fa-bars",
      "chpLabel": "Select",
      "chpDescription": "Select",
      "chpPlaceholder": "Select",
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
      "chpType": "file",
      "chpObligatoire": false,
      "chpIcon": "fa-file",
      "chpLabel": "File Upload",
      "className": "form-control",
      "subtype": "file"
    }
  ];

  ngOnInit() {
    if (this.donnee) {
      this.formulaireService.getById(this.donnee.frmId).subscribe((data) => {
        if (data.statut) {
          this.formChampsList = data.data

          this.formChampsList.steps.sort((a: any, b: any) => a.id - b.id);

          for (let i = 0; i < this.formChampsList.steps.length; i++) {

            this.formChampsList.steps[i].champs.sort((a: any, b: any) => a.chpId - b.chpId)

          }



        }
      })
    }
    this.getTable();
    this.getIcone();
  }




  onDragStart(event: DragEvent) {
    // console.log("drag started", JSON.stringify(event, null, 2));
  }

  onDragEnd(event: DragEvent) {
    // console.log("drag ended", JSON.stringify(event, null, 2));
  }

  onDraggableCopied(event: DragEvent) {
    // console.log("draggable copied", JSON.stringify(event, null, 2));
  }

  onDraggableLinked(event: DragEvent) {
    // console.log("draggable linked", JSON.stringify(event, null, 2));
  }

  onDragged(item: any, list: any[], effect: DropEffect) {
    // console.log(this.formChampsList);

    if (effect === "move") {
      const index = list.indexOf(item);
      list.splice(index, 1);
    }

  }

  onDragCanceled(event: DragEvent) {
    // console.log("drag cancelled", JSON.stringify(event, null, 2));
  }

  onDragover(event: DragEvent) {
    // console.log("dragover", JSON.stringify(event, null, 2));
  }

  onDrop(event: DndDropEvent, list?: any[]) {

    if (list && (event.dropEffect === "copy" || event.dropEffect === "move")) {

      if (event.dropEffect === "copy"){
        event.data.chpNom = event.data.chpType + '-' + new Date().getTime();
        if(event.data.chpType=='file'){
          this.formChampsList.numberOfFile++;          
        }
      }
      let index = event.index;
      if (typeof index === "undefined") {
        index = list.length;
      }
      list.splice(index, 0, event.data);
    }
  }

  addStep() {
    this.formChampsList.steps.push({ stepName: '', frmId: null, champs: [] })
  }
  removeStep(stepIndex, step) {
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("configuration.alert-suppression", message);

    const dialogRef = this.dialog2.open(ConfirmDialogComponent, {
      disableClose: true,
      data: dialogData

    }).afterClosed().subscribe(result => {
			if(result===true){
        if (step.id != null && step.id != undefined) {
          this.formulaireService.deleteSteps(step).subscribe(data => {
            if (data.statut) {
              this.translate.get('formulaire.deleteSuccessNotif').subscribe((resp:any)=>{
                this.notification.success(resp);
              })
              this.statusDialog = true;
            }
          })
        }
        this.formChampsList.steps.splice(stepIndex, 1);
      }
		});
    
  }
  removeField(stepIndex, champsIndex) {

    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("configuration.alert-suppression", message);

    const dialogRef = this.dialog2.open(ConfirmDialogComponent, {
      disableClose: true,
      data: dialogData

    }).afterClosed().subscribe(result => {
			if(result===true){
        if (this.formChampsList.steps[stepIndex].champs[champsIndex].chpId != undefined && this.formChampsList.steps[stepIndex].champs[champsIndex].chpId != null) {
          this.formulaireService.deleteChamps(this.formChampsList.steps[stepIndex].champs[champsIndex]).subscribe(data => {
            if (data.statut) {
              this.translate.get('formulaire.deleteSuccessNotif').subscribe((resp:any)=>{
                this.notification.success(resp);
              })
              this.statusDialog = true;
            }

          });
        }
        if(this.formChampsList.steps[stepIndex].champs[champsIndex].chpType=='file'){
          this.formChampsList.numberOfFile--;
        }
        this.formChampsList.steps[stepIndex].champs.splice(champsIndex, 1);
        
      }
		});
    
  }

  removeFieldValue(stepIndex,champsIndex,valueIndex){
    const message = "Alert.confirm-action";
    const dialogData = new ConfirmDialogModel("configuration.alert-suppression", message);

    const dialogRef = this.dialog2.open(ConfirmDialogComponent, {
      disableClose: true,
      data: dialogData

    }).afterClosed().subscribe(result => {
      
			if(result===true){
        if (this.formChampsList.steps[stepIndex].champs[champsIndex].values[valueIndex].id != undefined && this.formChampsList.steps[stepIndex].champs[champsIndex].values[valueIndex].id != null) {
          this.formulaireService.deleteChampsValues(this.formChampsList.steps[stepIndex].champs[champsIndex].values[valueIndex]).subscribe(data => {
            if (data.statut) {
              this.translate.get('formulaire.deleteSuccessNotif').subscribe((resp:any)=>{
                this.notification.success(resp);
              })
              this.statusDialog = true;
            }

          });
        }
        this.formChampsList.steps[stepIndex].champs[champsIndex].values.splice(valueIndex,1)

      }
		});

  }

  getTable() {
    this.formulaireService.getTable().subscribe(data => {
      this.tables = data.data
    })
  }


  selectId(id) {
    this.formulaireService.getColonne(id).subscribe(data => {
      this.champstable = data.data
    })
  }

  getIcone() {
    this.formulaireService.getIcone().subscribe(data => {
      this.icone = data.data
    })
  }

  saveForm() {
    let formError = false;
    this.formChampsList.steps.forEach(step => {
      if (step.stepName === '' || step.stepName === undefined ||
        step.stepName === null || this.formChampsList.frmNom == '' ||
        this.formChampsList.frmNom == undefined || this.formChampsList.frmNom == null) {
        this.translate.get('formulairev2.notifAddNameStep').subscribe((res) => {
          this.notification.warn(res);
        })
        formError = true;
        return;
      }
    });
    if (formError) { return; }

    this.formulaireService.saveForm(this.formChampsList).subscribe((data: any) => {
      if(data.statut){
        this.translate.get('formulaire.saveSuccessNotif').subscribe((resp:any)=>{
          this.notification.success(resp);
          this.statusDialog = true;
        })
        this.dialog.close({status: true})
      }
    })
  }
  
  closeDialog() {
    this.dialog.close(this.statusDialog);
  }

}

export interface FormChampsList {
  frmNom: string;
  frmDescription: string;
  numberOfFile: number;
  steps: Step[];
}

export interface Step {
  stepName: string;
  frmId: number;
  champs: any[];
}