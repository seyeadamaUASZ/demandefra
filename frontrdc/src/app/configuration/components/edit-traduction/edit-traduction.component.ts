import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { Traduction } from '../../models/traduction';
import { TraductionService } from '../../services/traduction.service';
import {map, startWith} from 'rxjs/operators';
import { debounceTime } from 'rxjs-compat/operator/debounceTime';

@Component({
  selector: 'app-edit-traduction',
  templateUrl: './edit-traduction.component.html',
  styleUrls: ['./edit-traduction.component.scss']
})
export class EditTraductionComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private traductionService: TraductionService,
    private notification: NotificationService, public dialogRef: MatDialogRef<EditTraductionComponent>,
    private translate: TranslateService, @Inject(MAT_DIALOG_DATA) public data: any) { }
  editTraductionTitle;
  traductionForm = this.formBuilder.group({
    reference: [{ value: this.data?.traduction?.reference, disabled: this.data.update ? true : false }, Validators.required],
    defaultLangue: [{ value: this.data?.traduction?.defaultLangue, disabled: (this.data.update ? true : (this.data?.traduction?.langue?.lngLangue == "fr") ? true : (this.data.lngLangue == "fr") ? true : false) }, Validators.required],
    selectedLangue: [this.data?.traduction?.selectedLangue, Validators.required],
    langue: [this.data.update ? this.data.traduction.langue : this.data]
  });
  langue;

  traductions: Traduction[];
  filteredOptions: Observable<Traduction[]>;

  ngOnInit() {
    this.editTraductionTitle = this.data.update ? 'configuration.editTraductionTitleUpdate' : 'configuration.editTraductionTitleCreation'
    this.langue = !this.data.update ? this.data : this.data.traduction.langue;
    this.traductionService.getTraduction(this.langue).subscribe((data)=>{
      if(data.statut){
        this.traductions = data.data;
      }
     
    },(error)=>{},()=>{
      this.filteredOptions = this.traductionForm.controls['reference'].valueChanges.pipe(
        startWith(''),
        map(value => this._filter(value))
      );
    });

  }
  
  
  private _filter (value: string): Traduction[] {
    const filterValue = value;
    return this.traductions.filter(option =>option.reference.includes(filterValue)
    );
  }

  update(traduction:Traduction){
    this.closeDialog({statut:false,update:true,traduction:traduction});
  }

  checkKeyOK(){
    //test key
    let isOk = true;
    let key0:string   = this.traductionForm.controls['reference'].value;
    let key = key0;
    let keys:string[] = key.split(".") ;
    let kl = keys.length;
    key=keys[0];
    for(let i =1;i<kl-1;i++){
      key += `.${keys[i]}`;
    }
    this.traductions.forEach(element => {
        
        
        if(element.reference==key  || element.reference==key0 || (element.reference.includes(key0) && element.reference[key0.length]==".")){
          this.translate.get("configuration.traduction.notification.referenceExist").subscribe(res=>{
            this.notification.warn(res);
          });
          isOk =false;
          return isOk;
        }
    });
    return isOk;
  }
  
  onSubmit() {
    if (this.traductionForm.valid) {
      if (!this.data.update) {
        if(!this.checkKeyOK()){
          return;
        }
        this.traductionService.addTraduction(this.traductionForm.getRawValue()).subscribe(data => {
          if (data.statut) {
            this.translate.get(data.description).subscribe((res:string)=>{
              this.notification.success(res);
            });

            this.traductionForm.reset();

            this.closeDialog(data);
          } else {
            this.translate.get(data.description).subscribe((res: string)=>{
              this.notification.warn(res);
            });
          }
        }, error => {
          this.translate.get('Error.internalservererror').subscribe((res: string) => {
            this.notification.error(res);
          });
        });
      } else {
        this.traductionService.updateTraduction(this.traductionForm.getRawValue()).subscribe(data => {
          if (data.statut) {
            this.translate.get(data.description).subscribe((res:string)=>{
              this.notification.success(res);
            });

            this.traductionForm.reset();

            this.closeDialog(data);
          } else {
            this.translate.get(data.description).subscribe((res:string)=>{
              this.notification.warn(res);
            });
          }
        }, error => {
          this.translate.get('Error.internalservererror').subscribe((res: string) => {
            this.notification.error(res);
          });
        });
      }
    } else {
      this.translate.get('invalid-form').subscribe((res: string) => {
        this.notification.warn(res);
      });
    }
  }
  closeDialog(data) {
    this.dialogRef.close(data);
  }

  closeDialog2() {
    this.dialogRef.close();
  }

}
