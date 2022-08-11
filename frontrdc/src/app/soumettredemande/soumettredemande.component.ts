import { Component, OnInit } from '@angular/core';
import { MatIconRegistry } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { StyleManagerService } from '../shared/style-manager.service';

@Component({
  selector: 'app-soumettredemande',
  template: `<app-sidenav></app-sidenav>`,
  styles: []
})
export class SoumettredemandeComponent implements OnInit {

  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIconSet(sanitizer.bypassSecurityTrustResourceUrl('assets/avatars.svg'));
  }  

  ngOnInit() {}
}
