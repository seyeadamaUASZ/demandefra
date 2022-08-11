import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ParametreService } from '../../services/parametre.service';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarRef, SimpleSnackBar, DateAdapter } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from '../../../shared/services/notification.service';
@Component({
  selector: 'app-actualites',
  templateUrl: './actualites.component.html',
  styleUrls: ['./actualites.component.scss']
})
export class ActualitesComponent implements OnInit {
  Themes;
  Langues;
  usernam;
  Image;
  selectedFile1: File;
  selectedFile2: File;
  selectedFile3: File;
  selectedFile4: File;
  selectedFile5: File;
  selectedFile6: File;
  selectedFile7: File;
  selectedFile8: File;
  selectedFile9: File;
  selectedFile10: File;


  retreivedImage1: any;
  retreivedImage2: any;
  retreivedImage3: any;
  retreivedImage4: any;
  retreivedImage5: any;
  retreivedImage6: any;
  retreivedImage7: any;
  retreivedImage8: any;
  retreivedImage9: any;
  retreivedImage10: any;


  base64Data5: any;
  base64Data1: any;
  base64Data2: any;
  base64Data3: any;
  base64Data4: any;
  base64Data6: any;
  base64Data7: any;
  base64Data8: any;
  base64Data9: any;
  base64Data10: any;


  retreivedResponse: any;
  imageId: any;
  mimeType: any;
  loading = false;
  defaultTheme;
  defaultLangue;
  defaultNomApp;
  app;
  //constructor(public paramService: ParametreService, public formBuilder: FormBuilder) { }

  constructor(public paramService: ParametreService, public formBuilder: FormBuilder,
    private snackBar: MatSnackBar, private notification: NotificationService, private translate: TranslateService,
    public dateAdapter: DateAdapter<Date>, private route: Router

  ) { }
  LandingForm = this.formBuilder.group({
    lndId: [],
    lndIm1: [],
    lndIm2: [],
    lndIm3: [],
    lndIm4: [],
    lndIm5: [],
    lndIm6: [],
    lndIm7: [],
    lndIm8: [],
    lndIm9: [],
    lndIm10: [],


    username: [localStorage.getItem('username'), Validators.required],

  });
  ngOnInit() {
    this.getImagelanding();

  }
  uploadImageData = new FormData();

  updateParametre() {
    this.uploadImageData.append('username', localStorage.getItem('username'));
    //alert("file name"+this.selectedFile1)
    this.paramService.chargerImageLanding(this.uploadImageData).subscribe(data => {
      this.translate.get('landing.imgCharger').subscribe((res: string) => {
        this.notification.info(res);
        //localStorage.setItem('logo', 'data:image/png;base64,' + this.base64Data);
      });
      location.reload;
    });
  }

  getImagelanding() {
    this.paramService.getImageLanding().subscribe(data => {
      console.log(data);
      if (data.statut) {
        this.retreivedResponse = data.data;
       
        this.base64Data8 = this.retreivedResponse.lndIm8;
        this.base64Data9 = this.retreivedResponse.lndIm9;
        this.base64Data10 = this.retreivedResponse.lndIm10;


        //alert('hello'+this.retreivedResponse);
      
        this.retreivedImage8 = 'data:image/png;base64,' + this.base64Data8;
        this.retreivedImage9 = 'data:image/png;base64,' + this.base64Data9;
        this.retreivedImage10 = 'data:image/png;base64,' + this.base64Data10;



      } else {
        this.retreivedResponse = false;
      }
    })
  }

  public onFileChanged8(even) {
    this.selectedFile8 = even.target.files[0];
    this.uploadImageData.append('imageFile8', this.selectedFile8, this.selectedFile8.name);
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
      this.retreivedImage8 = reader.result;
    }

  }

  public onFileChanged9(even) {
    this.selectedFile9 = even.target.files[0];
    this.uploadImageData.append('imageFile9', this.selectedFile9, this.selectedFile9.name);
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
      this.retreivedImage9 = reader.result;
    }

  }




  public onFileChanged10(even) {
    this.selectedFile10 = even.target.files[0];
    this.uploadImageData.append('imageFile10', this.selectedFile10, this.selectedFile10.name);
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
      this.retreivedImage10 = reader.result;
    }

  }


}
