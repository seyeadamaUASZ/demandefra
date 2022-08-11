import { Component, OnInit } from '@angular/core';
import { WidgetService } from '../../../services/widget.service';

@Component({
  selector: 'app-statfolder3',
  templateUrl: './statfolder3.component.html',
  styleUrls: ['./statfolder3.component.scss']
})
export class Statfolder3Component implements OnInit {
  totaldemandeatraiter:any = 0;
  totaldemandetraitees:any = 0;

  constructor(private widgetS:WidgetService) { }

  ngOnInit() {
    this.nombreDemandesATraiter();
    this.nombreDemandeTraitees();
  }

  nombreDemandesATraiter(){
    this.widgetS.nombreDemandeATraiterAnac().subscribe((data: any) => {
      this.totaldemandeatraiter = data.data;
    })
  }

  nombreDemandeTraitees(){
    this.widgetS.nombreDemandeTraiterAnac().subscribe((data: any) => {
      this.totaldemandetraitees = data.data;
    })
  }
}
