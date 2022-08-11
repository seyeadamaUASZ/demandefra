import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { AntenneService } from 'src/app/antenne/service/antenne.service';
import { CategorieService } from 'src/app/gestioncategorie/service/categorie.service';
import { RegionService } from 'src/app/region/service/region.service';

@Component({
  selector: 'app-filterpopup',
  templateUrl: './filterpopup.component.html',
  styleUrls: ['./filterpopup.component.scss']
})
export class FilterpopupComponent implements OnInit {

  search = {
    categorie: '',
    raisonsociale: '',
    region:'',
    antenneRegionaleDepartementale:'',
    statutJuridique:'',
    status:''
  }

  reset(){
    this.search = {
      categorie: '',
      raisonsociale: '',
      region:'',
      antenneRegionaleDepartementale:'',
      statutJuridique:'',
      status:''
    }
  }

  categories:any;
  raisonsociales:any;
  regions:any;

  antenneRegionaleDepartementales:any;
  statutJuridiques:any;

  statuts:any = [
    {
      id:6,
      nom:"Autorisé"
    },
    {
      id:10,
      nom:"Refusé"
    }
  ];

  constructor(private categorieService: CategorieService,private regionService:RegionService
    ,private dialogRef: MatDialogRef<FilterpopupComponent>,
    @Inject(MAT_DIALOG_DATA) private data:any,
    private antenneService:AntenneService) { }

    getCategorie(){
      this.categorieService.allCategories().subscribe((resp:any)=>{
        this.categories = resp.data;
      });
    }
    getRegion(){
      this.regionService.getAllRegion().subscribe((resp:any)=>{
        this.regions= resp.data;
      })
    }

    getAntennes(){
      this.antenneService.getAllAntenne().subscribe((resp:any)=>{
        this.antenneRegionaleDepartementales = resp.data;
      })
    }
    ngOnInit() {
      this.getCategorie();
      this.getRegion();
      this.getAntennes();
      this.search = this.data;
    }

    validerFilter(){
      this.dialogRef.close({search:this.search,status:true})
    }

    comparewith(a:any,b:any){
      return a.id == b.id;
    }
}
