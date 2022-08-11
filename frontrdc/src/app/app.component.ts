import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BnNgIdleService } from 'bn-ng-idle';
import { AuthService } from './utilisateur/services/auth.service';
import { CustomiconService } from './shared/services/customicon.service';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'indus';
  session;
  tokken;

  ngOnInit() {
    this.session = localStorage.getItem('session');
    this.tokken = localStorage.getItem('token');
  }

  constructor(private bnIdle: BnNgIdleService, private router: Router, public disconnect: AuthService,private dialog:MatDialog, private customiconService: CustomiconService) {
    this.bnIdle.startWatching(1000).subscribe((finCompte) => {
      if (finCompte) {
        this.dialog.closeAll();
         this.router.navigate(['/login']);
          this.disconnect.deconnecter(this.session).subscribe(data => { });
          localStorage.removeItem('token');
      }
    });

    this.customiconService.init();
  }
}


