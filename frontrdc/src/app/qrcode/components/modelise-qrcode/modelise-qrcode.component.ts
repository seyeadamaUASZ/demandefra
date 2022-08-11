import { Component, OnInit, Inject } from '@angular/core';
import { field, value } from 'src/app/global.model';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import Swal from 'sweetalert2';
import { DropEffect, DndDropEvent } from 'ngx-drag-drop';
import { ChampsQrcode } from 'src/app/qrcode/models/champsQrcode';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { QrcodeService } from '../../services/qrcode.service';

@Component({
  selector: 'app-modelise-qrcode',
  templateUrl: './modelise-qrcode.component.html',
  styleUrls: ['./modelise-qrcode.component.scss']
})
export class ModeliseQrcodeComponent implements OnInit {
  listeQrcode
  qrcodeModeliser
  qrcode
  nomQrcode
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
      "type": "datetime-local",
      "icon": "fa-calendar",
      "label": "DateTime",
      "placeholder": "Date Time",
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
    },
    {
      "_id": "",
      "type": "button",
      "icon": "fa-paper-plane",
      "subtype": "submit",
      "label": "Submit"
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
  champs: any;
  champsQrcode = new ChampsQrcode;
  fields: any = {
    _id: "",
    type: "",
    icon: "",
    label: "",
    description: "",
    name: "",
    placeholder: "",
    className: "",
    subtype: "t",
    regex: "",
    handle: true
  };
  constructor(private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private dialogRef: MatDialog,
    private snackBar: MatSnackBar, private qrcodeService: QrcodeService,
    private notification: NotificationService,
    private translate: TranslateService,
    @Inject(MAT_DIALOG_DATA) public donnee: any,
  ) {
    this.loading = false;
  }

  ngOnInit() {
    this.breakpoint = (window.innerWidth <= 400) ? 1 : 5;
    this.getField();
    this.nomQrcode = this.donnee.qrcNom
  }
  getField() {
    this.qrcodeService.fieldChampsByQrcode(this.donnee.qrcId).subscribe(data => {
      this.champs = data.data;
      for (let i = 0; i < this.champs.length; i++) {
        this.fields._id = this.champs[i].cqdId
        this.fields.label = this.champs[i].cqdLabel
        this.fields.name = this.champs[i].cqdNom
        this.fields.className = this.champs[i].cqdClass
        this.fields.type = this.champs[i].cqdType
        this.fields.handle = this.champs[i].cqdObligatoire
        this.fields.placeholder = this.champs[i].cqdPlaceholder
        this.fields.regex = this.champs[i].cqdRegex
        this.fields.icon = this.champs[i].cqdIcon
        this.model.attributes.push(this.fields)
        this.fields = {};

      }
    })
  }

  addChamps() {
    for (let i = 0; i < this.model.attributes.length; i++) {
      this.champsQrcode.cqdId = this.model.attributes[i]['_id'];
      this.champsQrcode.cqdLabel = this.model.attributes[i]['label'];
      this.champsQrcode.cqdNom = this.model.attributes[i]['name'];
      this.champsQrcode.cqdClass = this.model.attributes[i]['className'];
      this.champsQrcode.cqdType = this.model.attributes[i]['type'];
      this.champsQrcode.cqdObligatoire = this.model.attributes[i]['handle'];
      this.champsQrcode.cqdTaille = this.model.attributes[i]['max'];
      this.champsQrcode.cqdPlaceholder = this.model.attributes[i]['placeholder'];
      this.champsQrcode.cqdRegex = this.model.attributes[i]['regex'];
      this.champsQrcode.cqdIcon = this.model.attributes[i]['icon'];
      this.champsQrcode.cqdQrcId = this.donnee.qrcId;
      this.qrcodeService.addChamps(this.champsQrcode).subscribe(data => {
        this.translate.get("qrcode modelisée et validée").subscribe((res: string) => {
          this.notification.success(res);
        });
        this.closeDialog();
      });
      this.champs = {}
      this.qrcodeService.validerQrcode(this.donnee).subscribe(data => {
        this.qrcode = data.data;
      })
    }
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
    console.log("dragover", JSON.stringify(event, null, 1));
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
        if (champs._id) {
          this.qrcodeService.supprimerChamps(champs._id).subscribe(data => {
          });
        }
        this.model.attributes.splice(i, 1);
      }
    });*/

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
    let input = new FormData;
    input.append('qrcId', this.model._id);
    input.append('attributes', JSON.stringify(this.model.attributes))

  }
  closeDialog() {
    this.dialogRef.closeAll();
  }

}
