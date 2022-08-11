import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Profil } from '../models/profil';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';
@Injectable({
  providedIn: 'root'
})

export class WidgetService {
  api = environment.apii;
  constructor(private http: HttpClient) { }

  listeWidget() {
    return this.http.get<any>(this.api + 'listwidget');
  }

  infoWidget(id: any) {
    return this.http.get<any>(this.api + 'widget', id);
  }

  allWidgetByProfilId(id: any) {
    return this.http.get<any>(this.api + 'listwidgetbyprofilid/' + id);
  }

  listeStatProfil() {
    return this.http.get<any>(this.api + 'widget/getStatProfil');
  }

  listTemplateAttributByWidget(id: any) {
    return this.http.get<any>(this.api + 'attribuertemplwidget/widgettemplatesAttr/' + id);
  }

  checkProfil(id: any) {
    return this.http.get<any>(this.api + 'profil/' + id);
  }

  checkWidgetTemplateAttr(id: any) {
    return this.http.get<any>(this.api + 'attribuertemplwidget/widgettemplatesAttrbyProfile/' + id);
  }

  nbrIndusUtiParDate() {
    return this.http.get<any>(this.api + 'utilisateur/nbrindusutipardate');
  }
  UtiParDateExport() {
    return this.http.get<any>(this.api + 'utilisateur/utipardateexport');
  }


  nbrIndusUtiParProfil() {
    return this.http.get<any>(this.api + 'utilisateur/nbrindusutiparprofil');
  }

  nbrCourbeIndusUtiParProfil() {
    return this.http.get<any>(this.api + 'utilisateur/nbrcourbeindusutiparprofil');
  }

  nbrCourbeIndusUtiParAnnee() {
    return this.http.get<any>(this.api + 'utilisateur/nbrindusutiparannee');
  }
  nbrIndusUtiParProfilParAnnee() {
    return this.http.get<any>(this.api + 'utilisateur/nbrindusutiparprofilparannee');
  }


  public exportAsExcelFile(json: any[], excelFileName: string): void {
    const myworksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
    const myworkbook: XLSX.WorkBook = { Sheets: { 'data': myworksheet }, SheetNames: ['data'] };
    const excelBuffer: any = XLSX.write(myworkbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, excelFileName);
  }

  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {
      type: EXCEL_TYPE
    });
    FileSaver.saveAs(data, fileName + 'Etablissement' + EXCEL_EXTENSION);
  }

 /******************Widget chef bureau*************************************/
  nombreDemandeRenvoyeeChefBureau() {
    return this.http.get(this.api + 'demandeAutFra/nombreDemandeRenvoyeeChefBureau')
  }

  nombreDemandeATraiterChefBureau() {
    return this.http.get(this.api + 'demandeAutFra/nombreDemandeATraiterChefBureau')
  }

  nombreDemandeATraiterChefDivision() {
    return this.http.get(this.api + 'demandeAutFra/nombreDemandeATraiterChefDivision')
  }

  nombreDemandeAutFraByDate(powner){
    return this.http.get<any>(this.api+'demandeautfra/nombredemandeautfrabypowner/'+powner)
  }

  circulaireChefBureau(powner){
    return this.http.get<any>(this.api+'demandeautfra/nombrecirculairechefbureaudivision/'+powner)
  }

  /******************Widget chef division*************************************/
  nombreDemandeATraiter() {
    return this.http.get(this.api + 'demandeautfra/totaldemandeatraiter')
  }

  nombreProduitsAcceptees() {
    return this.http.get(this.api + 'demandeautfra/totalproduitAcceptes')
  }

  nombreProduitsRejetees() {
    return this.http.get(this.api + 'demandeautfra/totalproduitRejetes')
  }

  nombreDemandeTraitees() {
    return this.http.get(this.api + 'demandeautfra/totaldemandetraitees')
  }

  nbDemandeTraitees(){
    return this.http.get<any>(this.api+'demandeautfra/nombredemandebatontraitees')
  }

  circulaireChefDivision(){
    return this.http.get<any>(this.api+'demandeautfra/nombrecirculairechefdivision')
  }

  /******************Widget demandeur*************************************/
  nombreDemandeSoumises(email) {
    return this.http.get(this.api+'demandeAutFra/nombreDemandeSoumises/'+email)
  }

  nombreDemandeEnCours(email) {
    return this.http.get(this.api+'demandeAutFra/nombreDemandeEnCours/'+email)
  }

  statistiqueDemandeur(email) {
    return this.http.get(this.api+"demandeAutFra/statistiqueDemande/"+email);
  }

  circulaireDemandeur(email){
    return this.http.get<any>(this.api+'demandeautfra/nombrecirculaire/'+email)
  }

  statistiqueChefAntenne(owner) {
    return this.http.get(this.api+"demandeAutFra/statistiqueChefAntenne/"+owner);
  }

  nombreDemandeATraiterAnac() {
    return this.http.get(this.api+"demandeAutFra/nombreDemandeATraiterANAC");
  }

  nombreDemandeTraiterAnac() {
    return this.http.get(this.api+"demandeAutFra/nombreDemandeTraiterANAC");
  }

  statistiqueLanac() {
    return this.http.get(this.api+"demandeAutFra/statistiqueLanac");
  }

  nombreDemandeAutFraByMois(owner){
    return this.http.get<any>(this.api+'demandeautfra/nombredemandeautfrabymois/'+owner);
  }

  nombreDemandeAutFraByAnnee(){
    return this.http.get<any>(this.api+'demandeautfra/nombredemandeautfrabyannee');
  }

  circulaireChefAntenne(owner) {
    return this.http.get<any>(this.api+'demandeautfra/nombrecirculaireantenne/'+owner)
  }

  circulaireLaborantin() {
    return this.http.get<any>(this.api+'demandeautfra/nombrecirculairlaborantin');
  }

  /******************Widget controleur*************************************/
  nombreDemandesSoumise() {
    return this.http.get(this.api+'demandeautfra/totaldemandesoumise');
  }

  nombreDemandesTraitees() {
    return this.http.get(this.api+'demandeautfra/totaldemandestraitees');
  }
}
