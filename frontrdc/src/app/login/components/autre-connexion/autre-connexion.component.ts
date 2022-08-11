import { Component, OnInit } from '@angular/core';
import { ParametreService } from 'src/app/parametrage/services/parametre.service';

@Component({
  selector: 'app-autre-connexion',
  templateUrl: './autre-connexion.component.html',
  styleUrls: ['./autre-connexion.component.scss']
})
export class AutreConnexionComponent implements OnInit {

  constructor(private parametreService:ParametreService) { }

  appLogo;

  appName;
  imageAccueil;
  isFaceActive;
  isBioActive;
  typeConnexion;
  ngOnInit() {
    this.imageAccueil=localStorage.getItem('imageConnexion');
    this.appName = localStorage.getItem("appName");
    this.appLogo = localStorage.getItem("logo");
    
    this.getTypeConnexion();
  }
  getTypeConnexion() {
    this.parametreService.getTypeConnexion().subscribe(data => {
      this.typeConnexion = data.data;
      this.isBioActive=data.data[1].typIsActive;
      this.isFaceActive=data.data[0].typIsActive;
      
    });
  }


}
