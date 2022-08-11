import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DocumentService } from '../../services/document.service';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { MatDialogRef } from '@angular/material';
import { SelectAutocompleteComponent } from 'mat-select-autocomplete';
import { PrivilegeDocumentService } from '../../services/privilegeDocument.service';
import { PrivilegeSignerService } from '../../services/privilegeSigner.service';
import { Document } from '../../model/document';

@Component({
  selector: 'app-ajout-document',
  templateUrl: './ajout-document.component.html',
  styleUrls: ['./ajout-document.component.scss']
})
export class AjoutDocumentComponent implements OnInit {
  users;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  public document: Array<File>;
  isFileValid: boolean;
  images;
  href:any
  base64Data: any;
  based
  qrcodes: any[];
  isUpload: boolean;
  @ViewChild('file') file;

  fileDoc;

  addFile(){
    this.file.nativeElement.click();
  }

  onFileAdded(event){
    this.fileDoc = this.file.nativeElement.files[0];
    this.uploadDocument(event)
  }

  @Input() accept = '.pdf,.docx,.xls,.xlsx,.pptx';
  @ViewChild(SelectAutocompleteComponent,{static:false}) multiSelect: SelectAutocompleteComponent;
  options = [];
  selectedOptions = [];

  selected = this.selectedOptions;
  showError = false;
  errorMessage = '';


  FormulaireForm = this.formbuild.group({
    dctTitre: ['', Validators.required],
    dctAuteur:['', Validators.required],
    typeDocuments:['', Validators.required],

  });

  statusDocument:any
  typeDocument:any=[]
  constructor(private formbuild: FormBuilder, 	private translate:TranslateService,
    private router: Router,
    private privilegeDocumentS:PrivilegeDocumentService,
    private documentService: DocumentService,
    private privilegeSigner:PrivilegeSignerService,
    private notification: NotificationService,
    public dialogRef: MatDialogRef<AjoutDocumentComponent>,) { this.isUpload = false; }

  ngOnInit() {
    this.getTypeDocument();

  }

  onToggleDropdown() {
    this.multiSelect.toggleDropdown();
  }

  getSelectedOptions(selected) {
    this.selected = selected;
    console.log(JSON.stringify(this.selected))
  }

  onResetSelection() {
    this.selectedOptions = [];
  }

   getTypeDocument(){
     this.documentService.getTypeDocumentsByProfile(localStorage.getItem("profil")).subscribe((resp:any)=>{
       this.typeDocument=resp.data
     })
   }

   getUtilisateur(event){
    console.log(JSON.stringify(event.target))
   }

  onSubmit() {
    if (this.FormulaireForm.valid) {

      this.documentService.addDocument(this.document[0], this.FormulaireForm.value,localStorage.getItem("username"),this.statusDocument).subscribe(data => {
        if (data.statut) {
          let ReportSaveSuccess;
          this.translate.get('document.confirmEnr').subscribe((res: string) => {
            ReportSaveSuccess = res;
          });
          this.translate.get(ReportSaveSuccess).subscribe((res: string) => {
            this.notification.success(res);
          });
          if(this.statusDocument==1){
          for(let i=0; i<this.selected.length; i++){

              this.privilegeSigner.savePrivilegeSigner(this.selected[i],data.data.dctId).subscribe(data=>{
                console.log("ok")
            })
            }
          }
           else if(this.statusDocument==2){
            for(let i=0; i<this.selected.length; i++){
              this.privilegeSigner.savePrivilegeCertifier(this.selected[i],data.data.dctId).subscribe(data=>{
                console.log("ok")
            })
          }
            }
            else if(this.statusDocument==3){
              for(let i=0; i<this.selected.length; i++){
              this.privilegeSigner.savePrivilegeConsuler(this.selected[i],data.data.dctId).subscribe(data=>{
                console.log("ok")
            })
          }
            }else if(this.statusDocument == 4){
              for(let i=0; i<this.selected.length; i++){
                this.privilegeSigner.savePrivilegeApprouver(this.selected[i],data.data.dctId).subscribe(data=>{
                  console.log("ok")
              })
            }
          }
            else{
              alert("ttttttt")
            }


          this.FormulaireForm.reset();
          this.closeDialog();
        }
        else{
          let ReportSaveError;
        this.translate.get('document.erreurmemetitre').subscribe((res: string) => {
          ReportSaveError = res;
        });
        this.translate.get(ReportSaveError).subscribe((res: string) => {
          this.notification.error(res);
        })
        }
      }, error => {
        let ReportSaveError;
        this.translate.get('document.erreurEnr').subscribe((res: string) => {
          ReportSaveError = res;
        });
        this.translate.get(ReportSaveError).subscribe((res: string) => {
          this.notification.error(res);
        });
      });
    } else {
      let errorChamps;
      let form;
      this.translate.get('control.error').subscribe((res: string) => {
        form = res;
      });

      this.translate.get('control.required').subscribe((res: string) => {
        errorChamps = res;
      });
      this.translate.get(errorChamps).subscribe((res: string) => {
        this.notification.error(res);
      });

    }
  }

  uploadDocument(event: any) {
    this.privilegeDocumentS.listUtilisateurByIdTypD(this.FormulaireForm.value.typeDocuments.tydId).subscribe(res=>{
      this.options=res.data
    })
    if (event.target.files[0]) {
       const extension = event.target.files[0].name.split('.')[1].toLowerCase();
       if ( "pdf" === extension ||"docx"===extension || "xls"===extension || "xlsx"===extension ) {
         this.isFileValid = true;
       }
      this.document = event.target.files;
      if (this.document[0].size >3000000){
        this.translate.get("Verifier la taille du document!!").subscribe((res: string) => {
          this.notification.warn(res);
        });
        return;

      }
      this.isUpload = true;
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
