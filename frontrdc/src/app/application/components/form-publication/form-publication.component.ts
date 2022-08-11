import { Component, Inject, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { TranslateService } from '@ngx-translate/core';
import { ReplaySubject } from 'rxjs';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/sharedcomponent/confirm-dialog/confirm-dialog.component';
import { Application } from '../../models/application';
import { ApplicationService } from '../../services/application.service';

@Component({
  selector: 'app-form-publication',
  templateUrl: './form-publication.component.html',
  styleUrls: ['./form-publication.component.scss']
})
export class FormPublicationComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<FormPublicationComponent>,
    @Inject(MAT_DIALOG_DATA) public app: Application, private applicationService: ApplicationService,
    private notification: NotificationService, private translate: TranslateService,
    private sanitizer: DomSanitizer, private dialog: MatDialog) { }
  @ViewChild('file', { static: true }) file;

  public files: Set<File> = new Set();
  fileSelected: File;
  imgSRC: string;
  ngOnInit() {
    if (this.app.appImg) {
      let b = "data:image/png;base64," + this.app.appImg;
      this.imgSRC = b;
    }
  }

  ngAfterViewInit() {

  }
  addFiles() {
    this.file.nativeElement.click();
  }

  onFilesAdded() {
    console.log('file added');
    let isOk = true;
    if (this.file.nativeElement.files[0]) {
      const extension = this.file.nativeElement.files[0].name.split('.')[1].toLowerCase();
      const exts = ['png', 'jpg', 'jpeg', 'svg'];
      if (!this.file.nativeElement.files[0].type.includes('image')) {
        isOk = false;
        this.translate.get("application.publication.fileType").subscribe((res: string) => {
          this.notification.warn(res);
        });
        return;
      }
      if (this.file.nativeElement.files[0].size > 3000000) {
        this.translate.get("application.publication.fileSize").subscribe((res: string) => {
          this.notification.warn(res);
        });
        return;

      }
    }
    this.fileSelected = this.file.nativeElement.files[0];

    if (this.fileSelected) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imgSRC = reader.result as string;
      }
      reader.readAsDataURL(this.fileSelected)
    }
  }

  publier() {
    if (!this.fileSelected) {
      if (this.imgSRC) {
        this.createFileFromImg().then(res => this.fileSelected = res).finally(() => {
          this.applicationService.publierApp(this.app.appId, this.app.appUrl, this.fileSelected).subscribe((data) => {
            console.log(data.statut);
            if (data.statut) {
              this.translate.get(data.description).subscribe((res: string) => {
                this.notification.success(res);
                this.dialogRef.close(data);
              });
            } else {
              this.translate.get("application.publication.notification.error").subscribe((res: string) => {
                this.notification.warn(res);
              });
            }
          });
        });

      }
    }
    else {
      if (this.app.appUrl.trim() != "" && this.fileSelected) {

        this.applicationService.publierApp(this.app.appId, this.app.appUrl, this.fileSelected).subscribe((data) => {
          console.log(data.statut);
          if (data.statut) {
            this.translate.get(data.description).subscribe((res: string) => {
              this.notification.success(res);
              this.dialogRef.close(data);
            });
          } else {
            this.translate.get("application.publication.notification.error").subscribe((res: string) => {
              this.notification.warn(res);
            });
          }
        });
      } else {
        this.translate.get("application.publication.notification.setAllFields").subscribe(res => {
          this.notification.warn(res);
        })
      }
    }
  }


  removeFile(key: File) {
    this.files.delete(key);
  }
  closeDialog() {
    this.dialogRef.close();
  }

  createFileFromImg = async (): Promise<File> => {
    const img: HTMLImageElement = document.getElementById("imageId") as HTMLImageElement;
    return await fetch(img.src).then(res => res.blob()).then(blob => {
      const file: File = new File([blob], "illustration.png", blob);
      return file;
    }).catch(error => {
      return null;
    });

  };
}
