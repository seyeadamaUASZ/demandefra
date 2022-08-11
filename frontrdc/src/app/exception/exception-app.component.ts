import { Component, OnInit } from '@angular/core';
import { MatIconRegistry } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'exception-app',
  template: `
  <app-sidenav></app-sidenav>
  `,
  styles: []
})
export class ExceptionAppComponent implements OnInit {

  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIconSet(sanitizer.bypassSecurityTrustResourceUrl('./assets/avatars.svg'));
   }

  ngOnInit() {
  }

}
