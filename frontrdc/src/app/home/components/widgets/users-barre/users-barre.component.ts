import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-users-barre',
  templateUrl: './users-barre.component.html',
  styleUrls: ['./users-barre.component.scss']
})
export class UsersBarreComponent implements OnInit {
  chartOptions = {
    responsive: true
  };
  chartData2 = [
    { data: [3, 16, 2, 13,6, 8, 7,13, 16, 12,11,14], label: 'Utilisateurs' },
    { data: [2, 14, 10, 4, 5, 10, 3,3, 6, 6,2,9], label: 'Connectés' },
  ];
  legend = true;
  chartLabels2 = ['January', 'February', 'Mars', 'April','Mai','Juin','Juillet','Aout','Septembre','Octobre','Novembre','Décembre'];
  translatedChartLabels = []

  constructor(public translate:TranslateService) { }

  ngOnInit() {
      this.translate.get(this.chartLabels2).subscribe(translations => {
              /* translations is now an object with { 
               "key1": "translated value", 
               "key1": "translated value" } 
               and needs to be converted to an array again. */
              // console.log(translations);
              console.log("-----------"+translations);
              //this.translatedChartLabels = translations;
             this.translatedChartLabels = Object.values(translations)
           //  console.log( this.translatedChartLabels);

      });
  }

  onChartClick($event){

  }

}
