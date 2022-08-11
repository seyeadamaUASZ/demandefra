import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { WidgetService } from '../../../services/widget.service';

@Component({
  selector: 'app-statfolder5',
  templateUrl: './statfolder5.component.html',
  styleUrls: ['./statfolder5.component.scss']
})
export class Statfolder5Component implements OnInit {
  totaldemandesoumise: any = 0;
  totaldemandestraitees: any = 0;
  user: any;

  constructor(private widgetS:WidgetService, private userService: UserService) { }

  ngOnInit() {
    this.nombreDemandeSoumise();
    this.nombreDemandeTraitees();
  }

  nombreDemandeSoumise(){
    this.widgetS.nombreDemandesSoumise().subscribe((data:any)=>{
      this.totaldemandesoumise = data.data;
    })
  }

  nombreDemandeTraitees() {
    this.widgetS.nombreDemandesTraitees().subscribe((data:any)=>{
      this.totaldemandestraitees = data.data;
    })
  }
}
