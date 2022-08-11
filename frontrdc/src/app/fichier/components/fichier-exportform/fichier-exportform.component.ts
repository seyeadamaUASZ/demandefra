import { Component, Input, OnInit, SimpleChanges, OnChanges, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { FieldBase } from '../../../sharedcomponent/dynamicform/models/field-base';
import { FieldControlService } from '../../../sharedcomponent/dynamicform/services/field-control.service';
import { FieldService } from '../../../sharedcomponent/dynamicform/services/field.service';
import { FichierService } from '../../services/fichier.service';
import { Observable } from 'rxjs';
import { DropdownField } from '../../../sharedcomponent/dynamicform/models/field-dropdown';
import { TextboxField } from '../../../sharedcomponent/dynamicform/models/field-textbox';
import { DateField } from '../../../sharedcomponent/dynamicform/models/field-date';
import { GeneratedFile } from '@angular/compiler';
import { filter } from 'rxjs/operators';
import { QrcodeComponent } from '@techiediaries/ngx-qrcode';

@Component({
  selector: 'app-fichier-exportform',
  templateUrl: './fichier-exportform.component.html',
  styleUrls: ['./fichier-exportform.component.css'],
  providers: [ FieldControlService, FieldService ]
})
export class FichierExportformComponent implements OnInit {
  @Input() fields: FieldBase<string>[] = [];
   @Input() data: any[];
  form: FormGroup;
  payLoad = '';
  exportFileId:any;
  previousUrl: String;
  dataValue
  href
  display = false;
  blob
  qrcode='';
  @ViewChild(QrcodeComponent) qrcodeChild: QrcodeComponent
  constructor(private router: Router, private translate: TranslateService,private fichierService: FichierService, private qcs: FieldControlService, private service: FieldService) {    
    if(this.router.getCurrentNavigation()&&this.router.getCurrentNavigation().extras&&this.router.getCurrentNavigation().extras.state){         
       sessionStorage.setItem('fileid', this.router.getCurrentNavigation().extras.state['data']);
       sessionStorage.setItem('previousUrl', this.router.getCurrentNavigation().extras.state['data']); 
       this.exportFileId  = this.router.getCurrentNavigation().extras.state['data'];
       this.previousUrl = this.router.getCurrentNavigation().extras.state['previousUrl'];  
     } else {
       this.exportFileId = sessionStorage.getItem('fileid');
       this.previousUrl = sessionStorage.getItem('previousUrl');
     } 
  }

  ngOnInit(): void {
 
     this.form = new FormGroup({});      
     this.fichierService.fieldChampsByRapport(this.exportFileId).subscribe(data => {                
     for(var i=0; i < data.data.length; i++){                                           
      this.fields.push(this.service.buildField(this.fichierService.convertChampsToField(data.data[i])));                                                             
     }                                         
      if (this.fields) {
        this.fields = this.fields.sort((a, b) => a.order - b.order).slice(0); 
        this.form = this.qcs.toFormGroup(this.fields);         
      }
      });
     
  }
  
  onSubmit() { 
 
  }

  

  genererRapportExcel() { 
    this.payLoad = JSON.stringify(this.form.getRawValue());
    let varr = this.fichierService.genererRapportExcel(this.exportFileId,this.payLoad).subscribe((response) => {
      const file = new Blob([response], {type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }

  genererRapportPdf() {
      this.payLoad = JSON.stringify(this.form.getRawValue());
      let varr = this.fichierService.genererRapportPdf(this.exportFileId,this.payLoad).subscribe((response) => {
        const file = new Blob([response], {type:'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      });


  }

  gotoPreviousPage(): void{   
    this.router.navigate([this.previousUrl]);
  }


}
