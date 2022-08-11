import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/utilisateur/services/user.service';
import { WidgetService } from '../../../services/widget.service';

@Component({
  selector: 'app-statfolder2',
  templateUrl: './statfolder2.component.html',
  styleUrls: ['./statfolder2.component.scss']
})
export class Statfolder2Component implements OnInit {
  totaldemandesoumise: any = 0;
  totaldemandeencours: any = 0;
  totaldemandeacceptees: any = 0;
  totaldemanderejetees: any = 0;
  user: any;

  constructor(private widgetS:WidgetService, private userService: UserService) { }

  ngOnInit() {    
    this.nombreDemandeSoumise();
    this.nombreDemandeEnCours();
  }

  nombreDemandeSoumise(){
    this.userService.getUserByUsername(localStorage.getItem('username')).subscribe((data: any) => {
      this.widgetS.nombreDemandeSoumises(data.data.utiEmail).subscribe((data:any)=>{
        this.totaldemandesoumise = data.data;
        console.log(this.totaldemandesoumise)
      })
    });    
  }

  nombreDemandeEnCours() {
    this.userService.getUserByUsername(localStorage.getItem('username')).subscribe((data: any) => {
      this.widgetS.nombreDemandeEnCours(data.data.utiEmail).subscribe((data:any)=>{
        this.totaldemandeencours = data.data;
        console.log(this.totaldemandeencours)
      })
    });  
  }
}
