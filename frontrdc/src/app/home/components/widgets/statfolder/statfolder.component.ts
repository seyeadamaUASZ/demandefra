import { Component, OnInit } from '@angular/core';
import { WidgetService } from '../../../services/widget.service';

@Component({
  selector: 'app-statfolder',
  templateUrl: './statfolder.component.html',
  styleUrls: ['./statfolder.component.scss']
})
export class StatfolderComponent implements OnInit {
  nombreRenvoyee:any=0;
  nombreATraite:any=0;
  nombreTraiterChefDivision = 0;

  constructor(private widgetS:WidgetService) { }

  ngOnInit() {
    this.nombreDemandesRenvoyees()
    this.nombreDemandeATraiter()
    this.nombreDemandeATraiterChefDivision()
  }

  nombreDemandesRenvoyees(){
    this.widgetS.nombreDemandeRenvoyeeChefBureau()
    .subscribe((data:any)=>{
      this.nombreRenvoyee = data.data 
    },err=>{
      console.log(err)
    })
  }

  nombreDemandeATraiter(){
    this.widgetS.nombreDemandeATraiterChefBureau()
    .subscribe((data:any)=>{
      this.nombreATraite = data.data
    },err=>{
      console.log(err)
    })
  }

  nombreDemandeATraiterChefDivision(){
    this.widgetS.nombreDemandeATraiterChefDivision()
    .subscribe((data:any)=>{
      this.nombreTraiterChefDivision = data.data
    },err=>{
      console.log(err)
    })
  }
}
