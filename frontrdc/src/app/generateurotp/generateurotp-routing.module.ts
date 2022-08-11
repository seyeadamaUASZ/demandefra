import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainContentComponent } from './components/main-content/main-content.component';
import { GenerateurotpAppComponent } from './generateurotp-app.component';

const routes: Routes = [
  {
    path: '', component: GenerateurotpAppComponent,
    children: [
      { path: '', component: MainContentComponent}
     

    ],

  },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GenerateurotpRoutingModule { }
