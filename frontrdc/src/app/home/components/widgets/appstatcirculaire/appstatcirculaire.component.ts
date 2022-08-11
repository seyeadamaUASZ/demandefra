import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';
import { Options } from "highcharts";
import { WidgetService } from 'src/app/home/services/widget.service';
import { Chart } from 'angular-highcharts';
import { Color, Label, SingleDataSet } from 'ng2-charts';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { VisualiserboxComponent } from '../visualiserbox/visualiserbox.component';
import { MatDialog } from '@angular/material';
import { ChartOptions, ChartType } from 'chart.js';
import { UserService } from 'src/app/utilisateur/services/user.service';
@Component({
  selector: 'app-appstatcirculaire',
  templateUrl: './appstatcirculaire.component.html',
  styleUrls: ['./appstatcirculaire.component.scss']
})
export class AppstatcirculaireComponent implements OnInit {
  profilId: any = localStorage.getItem('profil');
  owner: any = localStorage.getItem('id');
  profile: any;
  chart3: any;
  chart6: any;
  chart9: any;
  nbrCirculaire: any = {}
  nbrIndusUtiPro: any = {}
  nbrCirculaireAnt: any = {}
  constjson: any = []
  pielabelindus: any = []
  piedonneeindus: any = []
  pieChartLabels = [];
  pieChartData = [];
  pieChartType = 'pie';
  pieChartColors: Array<any> = [{
    backgroundColor: ['#fc5858', '#19d863', '#fdf57d', '#7a1f32', '#29a3c2'],
    borderColor: ['rgba(252, 235, 89, 0.2)', 'rgba(77, 152, 202, 0.2)', 'rgba(241, 107, 119, 0.2)']
  }];
  pieChartOptions: ChartOptions = {
    responsive: true,
  };
  pieChartPlugins = [];
  public pieChartLegend = true;

  pieChartOptionsMesRec: ChartOptions;
  pieChartLabelsMesRec: Label[];
  pieChartDataCircleControleur: [SingleDataSet];
  pieChartDataCircleChefAntenne: [SingleDataSet];
  pieChartDataCircleChefBureau: [SingleDataSet];
  pieChartDataCircleChefDivision: [SingleDataSet];
  pieChartDataCircleLaborantin: [SingleDataSet];
  pieChartDataCircleDemandeur: [SingleDataSet];
  pieChartTypeMesRec: ChartType;
  pieChartLegendMesRec: boolean;
  pielabelindusRec: any = []
  piedonneeindusRec: any = []
  pielabelindusMesRec: any = []
  piedonneeindusMesRec: any = []

  /*++++++++++antenne+++++++++++*/ 
  pieChartTypeAnt: ChartType;
  pieChartLegendMesAnt: boolean;
  pieChartOptionsAnt: ChartOptions;
  pieChartLabelsAnt: Label[];
  pielabelindusAnt: any = []
  piedonneeindusAnt: any = []
  /*++++++++++bureau+++++++++++*/ 
  pieChartTypeBur: ChartType;
  pieChartLegendBur: boolean;
  pieChartOptionsBur: ChartOptions;
  pieChartLabelsBur: Label[];
  pielabelindusBur: any = []
  piedonneeindusBur: any = []
  /*++++++++++division+++++++++++*/ 
  pieChartTypeDiv: ChartType;
  pieChartLegendDiv: boolean;
  pieChartOptionsDiv: ChartOptions;
  pieChartLabelsDiv: Label[];
  pielabelindusDiv: any = []
  piedonneeindusDiv: any = []
  /*++++++++++lanac+++++++++++*/ 
  pieChartTypeLanac: ChartType;
  pieChartLegendLanac: boolean;
  pieChartOptionsLanac: ChartOptions;
  pieChartLabelsLanac: Label[];
  pielabelindusLanac: any = []
  piedonneeindusLanac: any = []
   /*++++++++++controleur+++++++++++*/ 
   pieChartTypeCon: ChartType;
   pieChartLegendCon: boolean;
   pieChartOptionsCon: ChartOptions;
   pieChartLabelsCon: Label[];
   pielabelindusCon: any = []
   piedonneeindusCon: any = []
  /*++++++++++demandeur+++++++++++*/ 
  pieChartTypeDem: ChartType;
  pieChartLegendDem: boolean;
  pieChartOptionsDem: ChartOptions;
  pieChartLabelsDem: Label[];
  pielabelindusDem: any = []
  piedonneeindusDem: any = []

  constructor(private widgetS: WidgetService, private dialog: MatDialog, private userService: UserService) { }

  ngOnInit() {
    this.getProfile();
    this.drawing();
    this.drawingChefBureau();
  }

  openPDF(): void {
    let DATA = document.getElementById('htmlDataCircle');

    html2canvas(DATA).then(canvas => {

      let fileWidth = 208;
      let fileHeight = canvas.height * fileWidth / canvas.width;

      const FILEURI = canvas.toDataURL('image/png')
      let PDF = new jsPDF('p', 'mm', 'a4');
      let position = 0;
      PDF.addImage(FILEURI, 'PNG', 0, position, fileWidth, fileHeight)

      PDF.save('dashbord-data.pdf');
    });
  }

  drawingChefBureau() {
    this.widgetS.circulaireChefBureau(this.profilId).subscribe(res => {
      this.nbrCirculaire = res.data;
      for (var i = 0; i < this.nbrCirculaire.length; i++) {

        this.constjson.push({
          name: this.nbrCirculaire[i].status_name,
          y: this.nbrCirculaire[i]["COUNT(demande_aut_fra.status)"],
          sliced: true,
          selected: true
        })
        this.piedonneeindusBur.push(this.nbrCirculaire[i]["COUNT(demande_aut_fra.status)"])
        this.pielabelindusBur.push(this.nbrCirculaire[i].status_name)
      }

      this.pieChartLabelsBur = this.pielabelindusBur;
      for(var i = 0; i < this.pieChartLabelsBur.length; i++) {
        if(this.pieChartLabelsBur[i] == 'encourschefdivision') {
          this.pieChartLabelsBur[i] = 'Demandes en cours';
        } else if(this.pieChartLabelsBur[i] == 'autdefinitive') {
          this.pieChartLabelsBur[i] = 'Autorisations FRA définitives';
        } else if(this.pieChartLabelsBur[i] == 'autprivisoire') {
          this.pieChartLabelsBur[i] = 'Autorisations FRA provisoires';
        } else if(this.pieChartLabelsBur[i] == 'terminer') {
          this.pieChartLabelsBur[i] = 'Demandes terminées';
        } else if(this.pieChartLabelsBur[i] == 'rejet') {
          this.pieChartLabelsBur[i] = 'Demandes rejetées';
        } else if(this.pieChartLabelsBur[i] == 'renvoie chef division') {
          this.pieChartLabelsBur[i] = 'Demandes renvoyées';
        }
      }
      this.pieChartDataCircleChefBureau = this.piedonneeindusBur;
      this.pieChartTypeBur = 'pie';
    });

    this.widgetS.circulaireChefDivision().subscribe(res => {
      this.nbrCirculaire = res.data; 
      for (var i = 0; i < this.nbrCirculaire.length; i++) {

        this.constjson.push({
          name: this.nbrCirculaire[i].status_name,
          y: this.nbrCirculaire[i]["COUNT(produits.status)"],
          sliced: true,
          selected: true
        })
        this.piedonneeindusDiv.push(this.nbrCirculaire[i]["COUNT(produits.status)"])
        this.pielabelindusDiv.push(this.nbrCirculaire[i].status_name)

      }

      this.pieChartLabelsDiv = this.pielabelindusDiv;
      for(var i = 0; i < this.pieChartLabelsDiv.length; i++) {
        if(this.pieChartLabelsDiv[i] == 'autdefinitive') {
          this.pieChartLabelsDiv[i] = 'Autorisation FRA Définitive';
        } else if(this.pieChartLabelsDiv[i] == 'rejet') {
          this.pieChartLabelsDiv[i] = 'Demandes rejetées';
        } else if(this.pieChartLabelsDiv[i] == 'enattente') {
          this.pieChartLabelsDiv[i] = 'Demandes en attente';
        } else if(this.pieChartLabelsDiv[i] == 'analysetermine') {
          this.pieChartLabelsDiv[i] = 'Produits analysés';
        } else if(this.pieChartLabelsDiv[i] == 'retirerAutDefinitive') {
          this.pieChartLabelsDiv[i] = 'Autorisations FRA retirées';
        }
      }
      this.pieChartDataCircleChefDivision = this.piedonneeindusDiv;
      this.pieChartTypeDiv = 'pie';
    });

    this.widgetS.circulaireChefAntenne(this.owner).subscribe(res => {
      this.nbrCirculaireAnt = res.data;
      for (var i = 0; i < this.nbrCirculaireAnt.length; i++) {

        this.constjson.push({
          name: this.nbrCirculaireAnt[i].status_name,
          y: this.nbrCirculaireAnt[i]["COUNT(demande_aut_fra.status)"],
          sliced: true,
          selected: true
        })
        this.piedonneeindusAnt.push(this.nbrCirculaireAnt[i]["COUNT(demande_aut_fra.status)"])
        this.pielabelindusAnt.push(this.nbrCirculaireAnt[i].status_name)
      }

      this.pieChartLabelsAnt = this.pielabelindusAnt;
      for(var i = 0; i < this.pieChartLabelsAnt.length; i++) {
        if(this.pieChartLabelsAnt[i] == 'autdefinitive') {
          this.pieChartLabelsAnt[i] = 'Autorisation FRA Définitive';
        } else if(this.pieChartLabelsAnt[i] == 'rejet') {
          this.pieChartLabelsAnt[i] = 'Demandes rejetées';
        } else if(this.pieChartLabelsAnt[i] == 'enbrouillon') {
          this.pieChartLabelsAnt[i] = 'Demandes en brouillons';
        } else if(this.pieChartLabelsAnt[i] == 'terminer') {
          this.pieChartLabelsAnt[i] = 'Demandes terminées';
        } else if(this.pieChartLabelsAnt[i] == 'apayer') {
          this.pieChartLabelsAnt[i] = 'Demandes à payer';
        } else if(this.pieChartLabelsAnt[i] == 'encourschefdivision') {
          this.pieChartLabelsAnt[i] = 'Demandes en cours';
        } else if(this.pieChartLabelsAnt[i] == 'autprivisoire') {
          this.pieChartLabelsAnt[i] = 'Autorisations FRA provisoires';
        }
      }
      this.pieChartDataCircleChefAntenne = this.piedonneeindusAnt;
      this.pieChartTypeAnt = 'pie';
    });

    this.widgetS.circulaireLaborantin().subscribe(res => {
      this.nbrCirculaire = res.data;
      for (var i = 0; i < this.nbrCirculaire.length; i++) {

        this.constjson.push({
          name: this.nbrCirculaire[i].status_name,
          y: this.nbrCirculaire[i]["COUNT(demande_aut_fra.status)"],
          sliced: true,
          selected: true
        })
        this.piedonneeindusLanac.push(this.nbrCirculaire[i]["COUNT(demande_aut_fra.status)"])
        this.pielabelindusLanac.push(this.nbrCirculaire[i].status_name)

      }

      this.pieChartLabelsLanac = this.pielabelindusLanac;
      for(var i = 0; i < this.pieChartLabelsLanac.length; i++) {
        if(this.pieChartLabelsLanac[i] == 'autdefinitive') {
          this.pieChartLabelsLanac[i] = 'Autorisations FRA définitives';
        } else if(this.pieChartLabelsLanac[i] == 'autprivisoire') {
          this.pieChartLabelsLanac[i] = 'Autorisations FRA provisoires';
        } else if(this.pieChartLabelsLanac[i] == 'terminer') {
          this.pieChartLabelsLanac[i] = 'Demandes terminées';
        } 
      }
      this.pieChartDataCircleLaborantin = this.piedonneeindusLanac;
      this.pieChartTypeLanac = 'pie';
    });

    this.widgetS.circulaireLaborantin().subscribe(res => {
      this.nbrCirculaire = res.data;
      for (var i = 0; i < this.nbrCirculaire.length; i++) {

        this.constjson.push({
          name: this.nbrCirculaire[i].status_name,
          y: this.nbrCirculaire[i]["COUNT(demande_aut_fra.status)"],
          sliced: true,
          selected: true
        })
        this.piedonneeindusCon.push(this.nbrCirculaire[i]["COUNT(demande_aut_fra.status)"])
        this.pielabelindusCon.push(this.nbrCirculaire[i].status_name)
      }

      this.pieChartLabelsCon = this.pielabelindusCon;
      for(var i = 0; i < this.pieChartLabelsCon.length; i++) {
        if(this.pieChartLabelsCon[i] == 'autdefinitive') {
          this.pieChartLabelsCon[i] = 'Autorisations FRA définitives';
        } else if(this.pieChartLabelsCon[i] == 'autprivisoire') {
          this.pieChartLabelsCon[i] = 'Autorisations FRA provisoires';
        } else if(this.pieChartLabelsCon[i] == 'terminer') {
          this.pieChartLabelsCon[i] = 'Demandes terminées';
        } 
      }
      this.pieChartDataCircleControleur = this.piedonneeindusCon;
      this.pieChartTypeCon = 'pie';
    });

    this.userService.getUserByUsername(localStorage.getItem('username')).subscribe((data: any) => {
      this.widgetS.circulaireDemandeur(data.data.utiEmail).subscribe(res => {
        this.nbrCirculaire = res.data;
        for (var i = 0; i < this.nbrCirculaire.length; i++) {
  
          this.constjson.push({
            name: this.nbrCirculaire[i].status_name,
            y: this.nbrCirculaire[i]["COUNT(demande_aut_fra.status)"],
            sliced: true,
            selected: true
          })
          this.piedonneeindusDem.push(this.nbrCirculaire[i]["COUNT(demande_aut_fra.status)"])
          this.pielabelindusDem.push(this.nbrCirculaire[i].status_name)  
        }
  
        this.pieChartLabelsDem = this.pielabelindusDem;
        for(var i = 0; i < this.pieChartLabelsDem.length; i++) {
          if(this.pieChartLabelsDem[i] == 'autdefinitive') {
            this.pieChartLabelsDem[i] = 'Autorisation FRA Définitive';
          } else if(this.pieChartLabelsDem[i] == 'terminer') {
            this.pieChartLabelsDem[i] = 'Demandes terminées';
          }
        }
        this.pieChartDataCircleDemandeur = this.piedonneeindusDem;        
        this.pieChartTypeDem = 'pie';
      });
    });    
  }

  drawing() {
    this.widgetS.nbrIndusUtiParProfil().subscribe(res => {
      this.nbrIndusUtiPro = res.data;
      for (var i = 0; i < this.nbrIndusUtiPro.length; i++) {
        this.constjson.push({
          name: this.nbrIndusUtiPro[i].pro_libelle,
          y: this.nbrIndusUtiPro[i]["COUNT(td_utilisateur.uti_id)"],
          sliced: true,
          selected: true
        })
        this.piedonneeindus.push(this.nbrIndusUtiPro[i]["COUNT(td_utilisateur.uti_id)"])
        this.pielabelindus.push(this.nbrIndusUtiPro[i].pro_libelle)
      }
      this.pieChartLabels = this.pielabelindus;
      this.pieChartData = this.piedonneeindus;
      this.pieChartType = 'pie';
    });

    this.chart6 = new Chart({
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
      },
      title: {
        text: 'Nombre de dossiers'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.y} </b>'
      },
      accessibility: {
        point: {
          valueSuffix: ''
        }
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.y} '
          }
        }
      },
      series: [{
        name: 'Brands',
        type: undefined,
        colorByPoint: true,
        data: [{
          name: 'Soumises',
          y: 61,
          sliced: true,
          selected: true
        }, {
          name: 'Traitées',
          y: 11
        }, {
          name: 'Rejetées',
          y: 8
        }]
      }]
    })

    this.chart9 = new Chart({
      chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
      },
      title: {
        text: 'Nombre de dossiers'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.y} </b>'
      },
      accessibility: {
        point: {
          valueSuffix: ''
        }
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.y} '
          }
        }
      },
      series: [{
        name: 'Brands',
        type: undefined,
        colorByPoint: true,
        data: [{
          name: 'Soumises',
          y: 61,
          sliced: true,
          selected: true
        }, {
          name: 'Traitées',
          y: 11
        }, {
          name: 'Rejetées',
          y: 8
        }]
      }]
    })

  }

  saveAs(uri, filename) {
    var link = document.createElement('a');
    if (typeof link.download === 'string') {
      link.href = uri;
      link.download = filename;

      //Firefox requires the link to be in the body
      document.body.appendChild(link);

      //simulate click
      link.click();

      //remove the link when done
      document.body.removeChild(link);
    } else {
      window.open(uri);
    }
  }

  getProfile() {
    this.profile = localStorage.getItem('profileLibelle');
  }

  openExcel() {
    this.widgetS.nbrIndusUtiParProfil().subscribe(res => {
      this.nbrIndusUtiPro = res.data;
      this.widgetS.exportAsExcelFile(this.nbrIndusUtiPro, 'nombre utilisateurs par profil')
    }, err => {

    })
  }

  downloadCanvas(event) {

    let DATA = document.getElementById('htmlDataCircle');

    html2canvas(DATA).then(canvas => {

      let fileWidth = 208;
      let fileHeight = canvas.height * fileWidth / canvas.width;

      const FILEURI = canvas.toDataURL('image/png')
      saveAs(FILEURI, 'utilisateurs.png');
    });

  }

  PrintImage(event) {
    let DATA = document.getElementById('htmlDataCircle');
    html2canvas(DATA).then(canvas => {
      let fileWidth = 208;
      let fileHeight = canvas.height * fileWidth / canvas.width;

      const FILEURI = canvas.toDataURL('image/png')
      var windowContent = '<!DOCTYPE html>';
      windowContent += '<html>';
      windowContent += '<head><title>Print canvas</title></head>';
      windowContent += '<body>';
      windowContent += '<img src="' + FILEURI + '">';
      windowContent += '</body>';
      windowContent += '</html>';

      const printWin = window.open('', '', 'width=' + screen.availWidth + ',height=' + screen.availHeight);
      printWin.document.open();
      printWin.document.write(windowContent);

      printWin.document.addEventListener('load', function () {
        printWin.focus();
        printWin.print();
        printWin.document.close();
        printWin.close();
      }, true);
    });
  }

  visualiserBox(parambox): void {
    const dialog = this.dialog.open(VisualiserboxComponent, {
      disableClose: true,
      width: '900px',
      data: parambox
    }).afterClosed().subscribe(result => {
      this.getProfile();
    });

  }

}
