import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MatTableDataSource } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { StockageblockchainService } from '../../services/stockageblockchain.service';
import shajs from 'sha.js';

@Component({
  selector: 'app-verify-blockchain',
  templateUrl: './verify-blockchain.component.html',
  styleUrls: ['./verify-blockchain.component.scss']
})
export class VerifyBlockchainComponent implements OnInit {
  @ViewChild('file', { static: true }) file;
  public files: Set<File> = new Set();
  public filesHash: Set<DocHash> = new Set();
  dataSource: MatTableDataSource<DocHash>;
  
  ffile: File;
  hash: string = null;
  hashprogress = false;
  transferFrom: any;
  balance: any;
  constructor(public dialogRef: MatDialogRef<VerifyBlockchainComponent>,
    private translate: TranslateService, private notification: NotificationService,
    private stockageBlockchaineService: StockageblockchainService) { }

  ngOnInit() {
    
  }

  addFiles() {
    this.filesHash = new Set();
    this.file.nativeElement.click();
  }
  onFilesAdded() {
    this.hashprogress = true;
    let that = this;
    // this.ffile = this.file.nativeElement.files[0];
    const files: { [key: string]: File } = this.file.nativeElement.files;
    
    for (const key in files) {

      console.log('------- ',files[key])
      if(key == 'length'){
        return;
      }
      const extension = files[key].name.split('.')[1].toLowerCase();
      if ("pdf" != extension) {
        this.translate.get("demandevehicule.fileExtenxionNotif").subscribe((res: string) => {
          this.notification.warn(res);
        });
        this.ffile = null;
        this.hashprogress = false;
        // return;
      } else if (files[key].size > 3000000) {
          this.translate.get("demandevehicule.fileSizeNotif").subscribe((res: string) => {
            this.notification.warn(res);
          });
          this.ffile = null;
          this.hashprogress = false;
          // return;
        } else {
          if (!isNaN(parseInt(key, 10))) {
            this.files.add(files[key]);
            // getting file hash content
            let reader = new FileReader();
            reader.onload = (event) => {
              let binary = event.target.result;

              let newHash= '0x' + new shajs.sha256().update(binary).digest('hex');
              this.hashprogress = false;
              let val: DocHash = {file:null,hash:null,result:null};
              val.file = files[key]
              val.hash = newHash;
              this.filesHash.add(val)
            };

            reader.readAsBinaryString(files[key]);
            
          }
        }


    }



  }

  initAndDisplayAccount() {

    let that = this;
    this.stockageBlockchaineService.getAccountInfo().then(function (acctInfo: any) {
      that.transferFrom = acctInfo.fromAccount;
      that.balance = acctInfo.balance;
    }).catch(function (error) {
    });

  }
  closeDialog() {
    this.dialogRef.close();
  }
  result: any;

  onSubmit() {
    
    let that = this;
    this.hashprogress = true;
    this.filesHash.forEach((element,index,arr) => {
      this.hashprogress = true;
      this.stockageBlockchaineService.verifyOnBlockchainNetwork(element.hash).then(function (instance: any) {
        console.log(instance)
        that.result = {}
        that.result.time = new Date(instance[0].words[0])
        that.result.block = instance[1].words[0]
        that.result.nom = instance[2]
        that.result.username = instance[3]
        arr.delete(element);
        element.result = that.result;
        arr.add(element);
        that.hashprogress=false

      }).catch(function (error) {
        console.log(error);
        that.result = {}
        that.result.block = -1
        arr.delete(element);
        element.result = that.result;
        arr.add(element);
        that.hashprogress=false

      });
    });
    
  }
}

interface DocHash{
  file:File;
  hash:string;
  result:{};
}