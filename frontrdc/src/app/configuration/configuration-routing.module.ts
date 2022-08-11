import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConfigurationMainContentComponent } from './components/configuration-main-content/configuration-main-content.component';
import { ConfigurationAppComponent } from './configuration-app.component';

const routes: Routes = [
  {
    path: '', component: ConfigurationAppComponent,
    children: [
      { path: '', component: ConfigurationMainContentComponent },
    ],

  },

];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfigurationRoutingModule { }
