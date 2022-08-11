import { Component, OnInit } from '@angular/core';
import { MatIconRegistry } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'paiement-app',
  template: `
  <app-sidenav></app-sidenav>
  `,
  styles: []
})
export class PaiementAppComponent implements OnInit {

  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIconSet(sanitizer.bypassSecurityTrustResourceUrl('./assets/avatars.svg'));
   }

  ngOnInit() {
  }

}
