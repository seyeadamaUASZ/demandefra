import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import shajs  from 'sha.js';

import { FormBuilder, FormGroup } from '@angular/forms';
import { StockageblockchainService } from '../../services/stockageblockchain.service';
@Component({
  selector: 'app-ajout-blockchain',
  templateUrl: './ajout-blockchain.component.html',
  styleUrls: ['./ajout-blockchain.component.scss']
})
export class AjoutBlockchainComponent implements OnInit {
  @ViewChild('file', { static: true }) file;
  ffile: File;
  hash: string = null;
  hashprogress = false; 
  transferFrom: any;
  balance: any;
  showprogress = false;
  constructor(public dialogRef: MatDialogRef<AjoutBlockchainComponent>,
    private translate: TranslateService, private notification: NotificationService,
    private formBuilder: FormBuilder
    ,private stockageBlockchaineService: StockageblockchainService
  ) { }

  formGroup: FormGroup = this.formBuilder.group({
    stblDescription: [''],
    stblHash:['']
  });

  ngOnInit() {
    this.initAndDisplayAccount()

  }




  addFiles() {
    this.file.nativeElement.click();
  }
  onFilesAdded() {
    this.hashprogress = true;
    this.ffile = this.file.nativeElement.files[0];
    const extension = this.ffile.name.split('.')[1].toLowerCase();
    if ("pdf" != extension) {
      this.translate.get("demandevehicule.fileExtenxionNotif").subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.ffile = null;
      this.hashprogress = false;
      return;
    }
    if (this.ffile.size > 3000000) {
      this.translate.get("demandevehicule.fileSizeNotif").subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.ffile = null;
      this.hashprogress = false;
      return;
    }

    // getting file hash content
    let reader = new FileReader();

    reader.onload = (event) => {
      let binary = event.target.result;
      
      this.hash = '0x'+new shajs.sha256().update(binary).digest('hex');
      this.formGroup.controls['stblHash'].setValue(this.hash);
      console.log(this.hash);
      this.hashprogress = false;
    }; 

    reader.readAsBinaryString(this.ffile);


  }

  


  onSubmit(){
    if(this.transferFrom == null || this.transferFrom == undefined){
      this.translate.get('blockchain.createWalletNotif').subscribe((res)=>{
        this.notification.warn(res);
        
      })
      this.closeDialog();
      return;
    }
    this.showprogress = true;
    let that = this;
    this.stockageBlockchaineService.saveBlockchain(this.formGroup.value,this.ffile,localStorage.getItem('username')).subscribe((data)=>{
      if(data.statut){
        this.stockageBlockchaineService.saveOnBlockchainNetwork(
          this.hash,
          this.ffile.name,
          that.transferFrom,
          localStorage.getItem('username')
     
        ).then(function(instance:any){
          that.showprogress = false;
         console.log(instance)
         if(instance.receipt.status){
           that.translate.get('blockchain.ajoutReussieNotif').subscribe((res)=>{
             that.notification.success(res);
             that.closeDialog();
           })
         }
         
        }).catch(function(error){
          //that.initAndDisplayAccount();
          that.showprogress = false;
          if(error == '4001'){
            that.translate.get('blockchain.rejetNotif').subscribe((res)=>{
              that.notification.warn(res);
              that.closeDialog();
            })
          }
          that.stockageBlockchaineService.deleteFileOnBd(that.hash).subscribe((data)=>{

          });
        });
      }else{
        that.showprogress = false;
        this.translate.get(data.description).subscribe(res=>{
          this.notification.warn(res);
        })
      }
    })
   

  }

  verifyOnBlock(hash){
    this.stockageBlockchaineService.verifyOnBlockchainNetwork(hash).then((instance:any)=>{
      console.log(instance);
      
    }).catch((error)=>{
      
    })
  }

  initAndDisplayAccount (){
 
    let that = this;
    this.stockageBlockchaineService.getAccountInfo().then(function(acctInfo : any){
      console.log(acctInfo);
      that.transferFrom = acctInfo.fromAccount;
      that.balance = acctInfo.balance;
    }).catch(function(error){
      console.log(error +'++++++++++++++++++');
    });
 
  }

  closeDialog(){
    this.dialogRef.close();
  }
}
