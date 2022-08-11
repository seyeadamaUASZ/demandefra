
import { Component, Inject, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ParametreService } from '../../services/parametre.service';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarRef, SimpleSnackBar, DateAdapter, MatDialog } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-parametre',
  templateUrl: './parametre.component.html',
  styleUrls: ['./parametre.component.scss']
})
export class ParametreComponent implements OnInit {
  Themes;
  Langues;
  usernam;
  Image;
  selectedFile: File;
  retreivedImage: any;
  base64Data: any;
  retreivedResponse: any;
  imageId: any;
  mimeType: any;
  loading = false;
  defaultTheme;
  defaultLangue;
  defaultNomApp;
  app;
  idParam;
  constructor(public paramService: ParametreService, public formBuilder: FormBuilder,
    private snackBar: MatSnackBar, private notification: NotificationService, private translate: TranslateService,
    public dateAdapter: DateAdapter<Date>, private route: Router, @Inject(DOCUMENT) private _document: Document,
    private router:Router,private dialog: MatDialog,
    

  ) { }
  ParametreForm = this.formBuilder.group({
    param_id: [],
    param_lng_id: [],
    param_thm_id: [],
    param_img_id: [],
    param_nom_app: [localStorage.getItem('appName'), Validators.required],
    paramUtiUsername: [localStorage.getItem('username'), Validators.required],
  });
  ngOnInit() {
    this.listTheme();
    this.listLangue();
    this.getListParam();
  }

  listTheme() {
    this.paramService.getTheme().subscribe(data => {
      this.Themes = data.data;
    });
  }
  listLangue() {
    this.paramService.getLangue().subscribe(data => {
      this.Langues = data.data;
    });
  }
  refreshPage() {
    this._document.defaultView.location.reload();
  }

  updateParametre() {
    const uploadImageData = new FormData();
    if (this.ParametreForm.value.param_img_id) {
      uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
      this.paramService.uplaodImage(uploadImageData).subscribe(data => {
        this.ParametreForm.value.param_img_id = data.data.imgId ? data.data.imgId : null;
        this.paramService.updateParametre(this.ParametreForm.value).subscribe(data => {
          this.translate.get(data.description).subscribe((res: string) => {
            this.notification.info(res);
            localStorage.removeItem('logo');
            this.getListParam();
           // alert("hoho");

            this.refreshPage();

          });
        }
        );
      });
    } else {
      if(this.imageId){
        this.ParametreForm.value.param_img_id=this.imageId;
        this.ParametreForm.value.paramId=this.idParam;  
      }
     
      this.paramService.updateParametre(this.ParametreForm.value).subscribe(data => {
        this.translate.get(data.description).subscribe((res: string) => {
          this.notification.info(res);
          
          this.getListParam();
         // alert("haha");
          // this.route.navigate(['/roles']);
          this.refreshPage();

        });
      });
    }

  }
  openSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar> {
    return this.snackBar.open(message, action, {
      duration: 2000,
    });
  }
  //récupere le logo de l'app
  getListParam() {
    this.paramService.getDefautParametre().subscribe(data => {
      this.defaultTheme = data.data[0].param_thm_id;
      this.defaultLangue = data.data[0].param_lng_id;
      this.app = data.data[0].param_nom_app;
      localStorage.setItem('appName', this.app);
      this.imageId = data.data[0].param_img_id?.imgId;
      this.idParam = data.data[0].paramId;

      this.getImage();
    })
  }

  public onFileChanged(even) {
    this.selectedFile = even.target.files[0];
    if (even.target.files.length === 0)
      return;
    if (even.target.files[0].size > 2000000) {
      //this.openSnackBar("la taille de logo depasse la taille attendue!!", 'Erreur');
      this.translate.get("la taille de logo depasse la taille attendue!!").subscribe((res: string) => {
        this.notification.warn(res);
      });
      return;

    }

    this.mimeType = even.target.files[0].type;
    //alert(this.mimeType);
    if (this.mimeType.match(/image\/*/) == null) {
      //alert("seul les images sont supportées!!!.");
      this.translate.get("seul les images sont supportées!!!.").subscribe((res: string) => {
        this.notification.warn(res);
      });
      return;
    }

    var reader = new FileReader();
    reader.readAsDataURL(even.target.files[0]);
    reader.onload = (_event) => {
      this.retreivedImage = reader.result;
    }
  }
  getImage() {
    // alert("------------------getImage--- parametre component-------------------- "+this.imageId);
    //Make a call to Spring Boot to get the Image Bytes.
    this.paramService.getImage(this.imageId)
      .subscribe(
        res => {
          this.retreivedResponse = res;
          this.base64Data = this.retreivedResponse.data.imgLogoByte;
          this.retreivedImage = 'data:image/png;base64,' + this.base64Data;
          localStorage.setItem('logo', this.retreivedImage);

        }
      );

  }
  compareTheme(t1, t2): boolean {
    return t1 && t2 ? t1.thmName === t2.thmName : false;
  }
  compareLangue(l1, l2): boolean {
    return l1 && l2 ? l1.lngLangue === l2.lngLangue : false;
  }

  switchLang(lang: string) {
    localStorage.setItem('langue', lang);
    this.useLang(lang);
    //alert("alert langue value"+JSON.stringify(this.userForm.value));
  }

  useLang(lang: string) {
    this.translate.use(lang);
    this.dateAdapter.setLocale(lang);
  }
  close(){
    this.dialog.closeAll();
  }

}
