import { Component, OnInit } from '@angular/core';
import { WidgetService } from '../../../services/widget.service';

@Component({
  selector: 'app-statfolder1',
  templateUrl: './statfolder1.component.html',
  styleUrls: ['./statfolder1.component.scss']
})
export class Statfolder1Component implements OnInit {
  totaldemandeatraiter:any = 0;
  totalproduitsacceptees:any = 0;
  totalproduitsrejetees:any = 0;
  totaldemandetraitees:any = 0;

  constructor(private widgetS:WidgetService) { }

  ngOnInit() {
    this.nombreDemandesATraiter();
    this.nombreProduitsAcceptees();
    this.nombreProduitRejetees();
    this.nombreDemandeTraitees();
  }

  nombreDemandesATraiter(){
    this.widgetS.nombreDemandeATraiter()
    .subscribe((data:any)=>{
      this.totaldemandeatraiter = data.data;
    },err=>{
    })
  }

  nombreProduitsAcceptees(){
    this.widgetS.nombreProduitsAcceptees()
    .subscribe((data:any)=>{
      this.totalproduitsacceptees = data.data;
    },err=>{
    })
  }

  nombreProduitRejetees(){
    this.widgetS.nombreProduitsRejetees()
    .subscribe((data:any)=>{
      this.totalproduitsrejetees = data.data;
    },err=>{
    })
  }

  nombreDemandeTraitees(){
    this.widgetS.nombreDemandeTraitees()
    .subscribe((data:any)=>{
      this.totaldemandetraitees = data.data;
    },err=>{
    })
  }
}
