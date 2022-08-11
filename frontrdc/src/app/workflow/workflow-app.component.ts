import { Component, OnInit } from '@angular/core';
import { MatIconRegistry } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-workflow-app',
  template: `
  <app-sidenav></app-sidenav>
  `,
  styles: []
})
export class WorkflowAppComponent implements OnInit {

  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIconSet(sanitizer.bypassSecurityTrustResourceUrl('./assets/avatars.svg'));
   }

  ngOnInit() {
  }

}
