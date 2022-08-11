import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { MAT_DIALOG_DATA, MatSnackBar, MatDialog } from '@angular/material';
import { NotificationService } from '../../../shared/services/notification.service';
import { field, value } from 'src/app/global.model';
import { QrcodeService } from 'src/app/qrcode/services/qrcode.service';
import { FormulaireServiceService } from '../service/formulaireService.service';


@Component({
  selector: 'app-view-form',
  templateUrl: './view-form.component.html',
  styleUrls: ['./view-form.component.scss']
})
export class ViewFormComponent implements OnInit {

fields:any={};
buttons:any;
nomForm:any;
valueChamps:any={label:"",value:"",valueChp:null}
champs:any={chpId:0,chpClass:"",chpLabel:"",chpNom:"",chpObligatoire:"",chpPlaceholder:"",chpTaille:"",chpType:"",chpRegex:"",chpIcon:"",chFrmId:0,values:[]};
modelFields:Array<field>=[];
  model:any = {
    name:'App name...',
    description:'App Description...',
    theme:{
      bgColor:"ffffff",
      textColor:"555555",
      bannerImage:""
    },
    attributes:this.modelFields
  };
values:Array<value>=[]
value:value={
  label:"",
  value:""
};

  constructor(private formbuild: FormBuilder, private router: Router, private userService: UserService,private formService: FormulaireServiceService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public donnee: any,private notification: NotificationService) {

  }

  ngOnInit() {
  this.nomForm=this.donnee.frmNom
  this.formService.fieldChampsByForm(this.donnee.frmId).subscribe(data=>{
    this.champs = data.data;
    //console.log(this.champs)

    for(let i=0; i<this.champs.length; i++){
      // this.fields={};
      // this.fields.label= this.champs[i].chpLabel
      // this.fields.className=this.champs[i].chpNom
      // this.fields.name=this.champs[i].chpNom
      // this.fields.type=this.champs[i].chpType
      // this.fields.handle=this.champs[i].chpObligatoire
      // this.fields.placeholder=this.champs[i].chpPlaceholder
      // this.fields.regex=this.champs[i].chpRegex
      // this.fields.icon=this.champs[i].chpIcon
      // this.fields._id=this.champs[i].chpId
      if(this.champs[i].chpType=='checkbox' ||this.champs[i].chpType=='select' || this.champs[i].chpType=='radio' || this.champs[i].chpType=='autocomplete'){
        this.values =[]  
        this.formService.fieldValueChamps(this.champs[i].chpId).subscribe(data=>{
            this.valueChamps=data.data;
            this.champs[i].values = this.valueChamps
            // for(let j=0; j<this.valueChamps.length; j++){
            //   this.value={}
            //   this.value.id=this.valueChamps[j].id
            //   this.value.label=this.valueChamps[j].label
            //   this.value.value=this.valueChamps[j].value
            //   this.values.push(this.value)
            // }
            

          },error=>{},()=>{
            
            // this.model.attributes[i].values=this.values
            // this.fields.values=this.values
            // this.model.attributes.push(this.fields)
            // this.fields = {}
            // this.values = []
            // console.log('model +++++++ ',this.model.attributes);
          })
      }else{
        // this.model.attributes.push(this.fields);
        // this.fields={};

      }

      

    }
  },error=>{},()=>{
   console.log('fieldss ++++',this.model.attributes)
  })
  // this.getField()

  }

  getField(){
    this.formService.fieldChampsByForm(this.donnee.frmId).subscribe(data=>{
      this.fields = data.data;
      console.log(this.fields)
    })
  }
  getButton(){
    this.formService.buttonChampsByForm(this.donnee.frmId).subscribe(data=>{
      this.buttons = data.data;
      console.log(this.buttons)
    })
  }
  fermer(){
      this.dialog.closeAll();
  }

}
