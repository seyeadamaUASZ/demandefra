import { Component, Input, OnInit, SimpleChanges, OnChanges, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { GeneratedFile } from '@angular/compiler';
import { filter } from 'rxjs/operators';
import { QrcodeComponent } from '@techiediaries/ngx-qrcode';
import { FieldBase } from 'src/app/sharedcomponent/dynamicform/models/field-base';
import { FieldControlService } from 'src/app/sharedcomponent/dynamicform/services/field-control.service';
import { FieldService } from 'src/app/sharedcomponent/dynamicform/services/field.service';
import { QrcodeService } from '../../services/qrcode.service';
import { MatDialog, MatSnackBar } from '@angular/material';
import { NotificationService } from 'src/app/shared/services/notification.service';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-qrcode-genere',
  templateUrl: './qrcode-genere.component.html',
  styleUrls: ['./qrcode-genere.component.css'],
  providers: [FieldControlService, FieldService]
})
export class QrcodeGenereComponent implements OnInit {
  @Input() fields: FieldBase<string>[] = [];
  @Input() data: any[];
  form: FormGroup;
  payLoad = '';
  exportFileId: any;
  previousUrl: String;
  dataValue;
  href;
  données: any;
  nomQrcode: any;
  qrcodeModeliser: any;
  display = false;
  @ViewChild('file1', { static: true }) file1;

  color: any;
  loading: any;
  QrcodeForm = this.formbuild.group({
    qrcCouleur: [''],
  });
  @ViewChild(QrcodeComponent) qrcodeChild: QrcodeComponent
  constructor(private router: Router, private translate: TranslateService, private qrcodeService: QrcodeService, private qcs: FieldControlService,
    private formbuild: FormBuilder,
    public dialogRef: MatDialog, private _snackBar: MatSnackBar,
    private route: ActivatedRoute, private notification: NotificationService,
    private service: FieldService) {
    if (this.router.getCurrentNavigation() && this.router.getCurrentNavigation().extras && this.router.getCurrentNavigation().extras.state) {
      sessionStorage.setItem('fileid', this.router.getCurrentNavigation().extras.state['data']);
      sessionStorage.setItem('previousUrl', this.router.getCurrentNavigation().extras.state['data']);
      this.exportFileId = this.router.getCurrentNavigation().extras.state['data'];
      this.previousUrl = this.router.getCurrentNavigation().extras.state['previousUrl'];
    } else {
      this.exportFileId = sessionStorage.getItem('fileid');
      this.previousUrl = sessionStorage.getItem('previousUrl');
    }
  }

  ngOnInit(): void {
    this.form = new FormGroup({});
    this.qrcodeService.fieldChampsByQrcode(this.exportFileId).subscribe(data => {
      for (var i = 0; i < data.data.length; i++) {
        this.fields.push(this.service.buildField(this.qrcodeService.convertChampsToField(data.data[i])));
      }
      if (this.fields) {
        this.fields = this.fields.sort((a, b) => a.order - b.order).slice(0);
        this.form = this.qcs.toFormGroup(this.fields);
      }
    });


  }
  ffile1: File;

  uploadLogo() {
    this.ffile1 = this.file1.nativeElement.files[0];
    const extension = this.ffile1.name.split('.')[1].toLowerCase();

    if ("pdf" === extension) {
      this.translate.get("qrcode.logo").subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.ffile1 = null;
      return;
    }
    if (this.ffile1.size > 3000000) {
      this.translate.get("qrcode.taille").subscribe((res: string) => {
        this.notification.warn(res);
      });
      this.ffile1 = null;
      return;
    }
  }
  addFiles1() {
    this.file1.nativeElement.click();
  }

  onSubmit() {
    if (this.QrcodeForm.valid) {
      this.color = Number.parseInt(this.QrcodeForm.value.qrcCouleur)
      this.QrcodeForm.value.qrcCouleur = this.color;
      this.données = JSON.stringify(this.form.value);
      this.qrcodeService.qrcodeGenere(this.ffile1, this.données, this.QrcodeForm.value).subscribe(data => {
        if (data.statut) {
          this.translate.get(data.description).subscribe((res: string) => {
            this.getAllQrcode();
            this.display = true;
          });
          console.log(data);
          this.QrcodeForm.reset();

        } else {
        }
      }, error => {
        this.translate.get(error).subscribe((res: string) => {
          this.notification.error(res);
        });
      });
    }
    else {
      this.translate.get('formulaire invalide').subscribe((res: string) => {
        this.notification.error(res);
      });
    }

  }

  gotoPreviousPage(): void {
    this.router.navigate([this.previousUrl]);
  }

  genererQrcode() {
    this.dataValue = JSON.stringify(this.form.value);
    this.display = true
  }

  downloadQrcodePersonnalise(id) {
    this.qrcodeService.download(id).subscribe(resp => {
      this.saveFile(resp.body, "qrcode");
    });
  }
  saveFile(data: any, filename?: string) {
    const blob = new Blob([data], { type: 'image/png' });
    fileSaver.saveAs(blob, filename);
  }



  getAllQrcode() {
    this.qrcodeService.getallQrcodeGenerer().subscribe(res => {
      this.qrcodeModeliser = res.data;
    })

  }

}
