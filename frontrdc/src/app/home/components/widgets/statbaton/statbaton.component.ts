import { Component, OnInit } from '@angular/core';
import * as Highcharts from 'highcharts';
import { Options } from "highcharts";
import HC_exporting from 'highcharts/modules/exporting';
import { Chart } from 'angular-highcharts';
import { ChartsModule } from 'ng2-charts';
import { ChartOptions } from 'chart.js';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { MatDialog } from '@angular/material';
import { VisualiserboxComponent } from '../visualiserbox/visualiserbox.component';
import { WidgetService } from '../../../services/widget.service';
import { UserService } from 'src/app/utilisateur/services/user.service';
@Component({
  selector: 'app-statbaton',
  templateUrl: './statbaton.component.html',
  styleUrls: ['./statbaton.component.scss']
})
export class StatbatonComponent implements OnInit {
  profilId: any = localStorage.getItem('profil');
  owner: any = localStorage.getItem('id');
  profile: any;
  chartOptions11: {};
  Highcharts1222 = Highcharts;
  chart2: any;
  chart3: any;
  chart5: any;
  chart8: any;

  nbrIndusUti: any = {}
  nbrDemandesAut: any = {}
  nbrIndusUtiExport: any
  nbrIndusUtiAnnee: any = {}
  nbrIndusUtiAnnee1: any[]
  showMainContent: Boolean = true;

  barChartColors: any[] = [
    {
      backgroundColor: '#ad1a1a',
    }]

  barChartOptionsAnnee = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  barChartLabelsAnnee = [];
  barChartTypeAnnee = 'bar';
  barChartLegendAnnee = true;
  barChartDataAnnee = [];
  barChartDataChefDivision = [];
  barChartOptionsMonth = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  barChartLabelsMonth = [];
  barChartTypeMonth = 'bar';
  barChartLegendMonth = true;
  barChartDataMonth = [];
  public barChartOptions: ChartOptions = {
    responsive: true,
  };

  barChartLabelsMonths = [];
  barChartTypeMonths = 'bar';
  barChartLegendMonths = true;
  barChartDataMonths = [];

  barChartLabelsMonth1 = [];
  barChartTypeMonth1 = 'bar';
  barChartLegendMonth1 = true;
  barChartDataMonth1 = [];

  barChartDataDivision = [];

  barChartDataDemandeur = [];

  barChartDataLanac = [];

  barChartDataControleur = [];
  constructor(private widgetS: WidgetService, private dialog: MatDialog, private userService: UserService) { }

  ngOnInit() {
    this.getProfile();
    this.drawing();
    this.drawingChefBureau();
  }

  openPDF(): void {
    let DATA = document.getElementById('htmlDataBaton');

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
  ShowHideButton() {
    this.showMainContent = this.showMainContent ? false : true;
  }

  /**************pour le chef de bureau************** */
  drawingChefBureau() {
    this.widgetS.nombreDemandeAutFraByDate(this.profilId).subscribe(res => {
      this.nbrDemandesAut = res.data;
      var count = [];
      var mois = []
      JSON.parse(JSON.stringify(this.nbrDemandesAut), function (key, value) {
        if (typeof (value) != "object") {
          count.push(value);
          mois.push([key])
        }
      });
      this.barChartLabelsMonth = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
      this.barChartTypeMonth = 'bar';
      this.barChartDataMonth = [
        { data: count, label: 'Nombre de demandes traitées' },
      ];
    })

    this.widgetS.nbDemandeTraitees().subscribe(res => {
      this.nbrDemandesAut = res.data;
      var count = [];
      var mois = []
      JSON.parse(JSON.stringify(this.nbrDemandesAut), function (key, value) {
        if (typeof (value) != "object") {
          count.push(value);
          mois.push([key])
        }
      });
      this.barChartLabelsMonth = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
      this.barChartTypeMonth = 'bar';
      this.barChartDataDivision = [
        { data: count, label: 'Nombre de produits rejetées' },
      ];
    })

    this.widgetS.nombreDemandeAutFraByMois(this.owner).subscribe(res => {
      this.nbrDemandesAut = res.data;
      var count = [];
      var mois = []
      JSON.parse(JSON.stringify(this.nbrDemandesAut), function (key, value) {
        if (typeof (value) != "object") {
          count.push(value);
          mois.push([key])
        }
      });
      this.barChartLabelsMonths = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
      this.barChartTypeMonths = 'bar';
      this.barChartDataMonths = [
        { data: count, label: 'Nombre de demandes soumises' },
      ];
    })

    this.widgetS.nombreDemandeAutFraByAnnee().subscribe(res => {
      this.nbrDemandesAut = res.data;
      var count = [];
      var mois = []
      JSON.parse(JSON.stringify(this.nbrDemandesAut), function (key, value) {
        if (typeof (value) != "object") {
          count.push(value);
          mois.push([key])
        }
      });
      this.barChartLabelsMonth1 = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
      this.barChartTypeMonth1 = 'bar';
      this.barChartDataMonth1 = [
        { data: count, label: 'Nombre de demandes à traiter' },
      ];
    })

    this.userService.getUserByUsername(localStorage.getItem('username')).subscribe((res: any) => {
      this.widgetS.statistiqueDemandeur(res.data.utiEmail).subscribe((res: any) => {
        this.nbrDemandesAut = res.data;
        var count = [];
        var mois = []
        JSON.parse(JSON.stringify(this.nbrDemandesAut), function (key, value) {
          if (typeof (value) != "object") {
            count.push(value);
            mois.push([key])
          }
        });
        this.barChartLabelsMonth1 = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
        this.barChartTypeMonth1 = 'bar';
        this.barChartDataDemandeur = [
          { data: count, label: 'Nombre de demandes soumises' },
        ];
      })
    });   
    
    this.widgetS.statistiqueLanac().subscribe((res: any) => {
      this.nbrDemandesAut = res.data;
      var count = [];
      var mois = []
      JSON.parse(JSON.stringify(this.nbrDemandesAut), function (key, value) {
        if (typeof (value) != "object") {
          count.push(value);
          mois.push([key])
        }
      });
      this.barChartLabelsMonth1 = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
      this.barChartTypeMonth1 = 'bar';
      this.barChartDataLanac = [
        { data: count, label: 'Nombre de demandes traitées' },
      ];
    })

    this.widgetS.statistiqueLanac().subscribe((res: any) => {
      this.nbrDemandesAut = res.data;
      var count = [];
      var mois = []
      JSON.parse(JSON.stringify(this.nbrDemandesAut), function (key, value) {
        if (typeof (value) != "object") {
          count.push(value);
          mois.push([key])
        }
      });
      this.barChartLabelsMonth1 = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
      this.barChartTypeMonth1 = 'bar';
      this.barChartDataControleur = [
        { data: count, label: 'Nombre de demandes traitées' },
      ];
    })
  }

  drawing() {
    var rescount = []
    var result = []
    this.widgetS.nbrIndusUtiParDate().subscribe(res => {
      this.nbrIndusUti = res.data;
      var count = [];
      var mois = []
      JSON.parse(JSON.stringify(this.nbrIndusUti), function (key, value) {
        if (typeof (value) != "object") {
          count.push(value); //if you need a value array
          mois.push([key])
        }
      });
      this.barChartLabelsMonth1 = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];
      this.barChartTypeMonth1 = 'bar';
      this.barChartLegendMonth1 = true;
      this.barChartDataMonth1 = [
        { data: count, label: 'Nombre de utilisateurs créés' },
      ];
    })

    this.widgetS.nbrCourbeIndusUtiParAnnee().subscribe(res => {
      this.nbrIndusUtiAnnee = res.data;
      var count = []
      var annee = []
      for (var i = 0; i < this.nbrIndusUtiAnnee.length; i++) {
        count.push(this.nbrIndusUtiAnnee[i]["CountOfNewUsers"]); 
        annee.push(this.nbrIndusUtiAnnee[i]["annee"])
      }
      this.barChartLabelsAnnee = annee;
      this.barChartTypeAnnee = 'bar';
      this.barChartLegendAnnee = true;
      this.barChartDataAnnee = [
        { data: count, label: 'Nombre de utilisateurs créés' },
      ];
    })

    this.chart5 = new Chart({
      chart: {
        type: 'column'
      },
      title: {
        text: 'Nombre de dossiers'
      },
      colors: [
        '#6495ED',
        '#FFA500',
        '#00ff00'
      ],
      xAxis: {
        categories: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
      },
      yAxis: {
        title: {
          text: 'Nombre de dossiers '
        }
      },
      series: [{
        type: undefined,
        name: 'Nombre de dossiers soumis',
        data: [20, 35, 67, 19, 30, 45, 58, 90, 78, 89, 100]
      },
      {
        type: undefined,
        name: 'Nombre de dossiers traités',
        data: [5, 29, 17, 9, 15, 40, 52, 87, 72, 19, 30]
      },
      {
        type: undefined,
        name: 'Nombre de dossiers rejetés',
        data: [3, 20, 10, 6, 11, 35, 47, 80, 62, 10, 27]
      }]

    })

    this.chart8 = new Chart({
      chart: {
        type: 'column'
      },
      title: {
        text: 'Nombre de dossiers'
      },
      colors: [
        '#6495ED',
        '#FFA500',
        '#00ff00'
      ],
      xAxis: {
        categories: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
      },
      yAxis: {
        title: {
          text: 'Nombre de dossiers '
        }
      },
      series: [{
        type: undefined,
        name: 'Nombre de dossiers soumis',
        data: [20, 35, 67, 19, 30, 45, 58, 90, 78, 89, 100]
      },
      {
        type: undefined,
        name: 'Nombre de dossiers traités',
        data: [5, 29, 17, 9, 15, 40, 52, 87, 72, 19, 30]
      },
      {
        type: undefined,
        name: 'Nombre de dossiers rejetés',
        data: [3, 20, 10, 6, 11, 35, 47, 80, 62, 10, 27]
      }]
    })
  }

  plotSimpleBarChart() {
    
  }

  getProfile() {
    this.profile = localStorage.getItem('profileLibelle');
  }

  downloadCanvas(event) {
    var anchor = event.target;
    console.log('anchor ' + anchor)
    let DATA = document.getElementById('htmlDataBaton');
    html2canvas(DATA).then(canvas => {
      let fileWidth = 208;
      let fileHeight = canvas.height * fileWidth / canvas.width;
      const FILEURI = canvas.toDataURL('image/png')
      saveAs(FILEURI, 'utilisateurs.png')
    });
  }

  PrintImage(event) {
    var anchor = event.target;
    let DATA = document.getElementById('htmlDataBaton');
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

  openExcel() {
    this.widgetS.UtiParDateExport().subscribe(res => {
      this.nbrIndusUtiExport = res.data;
      this.widgetS.exportAsExcelFile(this.nbrIndusUtiExport, 'nombre utilisateurs')
    }, err => {
    })
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

