import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';

@Injectable({
  providedIn: 'root'
})
export class StatisitquesService {
  api = environment.apii;
  constructor(private http:HttpClient) { }

  getSearch(search){
    return this.http.post(`${this.api}demandeautfra/statistiques`,search);
  }

  public exportAsExcelFile(json: any[], excelFileName: string): void {
    
    let arrayObjJson = [];
    json.forEach((ob:any) => {
      let objJson = {
        CATEGORIE: ob.categorie.nom,
        RAISON_SOCIALE:ob.demandeautfra.raisonsociale,
        REGION:ob.demandeautfra.region.nomRegion,
        ANTENNE:ob.demandeautfra.antenneRegionaleDepartementale.nomAntenne,
        STATUT_JURIDIQUE:ob.demandeautfra.statutJuridique,
        STATUT_PRODUIT:ob.status==6?'Autorisé':'Refusé',
        NATURE:ob.nature,
        EMBALLAGE:ob.typeemballage,
        MARQUE:ob.marque,
        CONTENANCE:ob.contenance,
        DESCRIPTION_ETIQUETTE:ob.descriptionEtiquette,
        NUMERO_FRA:ob.autFra
      }
      arrayObjJson.push(objJson)
    });
     
    
    const myworksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(arrayObjJson);
    const myworkbook: XLSX.WorkBook = { Sheets: { 'data': myworksheet }, SheetNames: ['data'] };
    const excelBuffer: any = XLSX.write(myworkbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, excelFileName);
  }
  
  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {
      type: EXCEL_TYPE
    });
    FileSaver.saveAs(data, fileName + 'statistiques'+ EXCEL_EXTENSION);
  }
}
