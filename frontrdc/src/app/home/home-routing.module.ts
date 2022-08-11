import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeAppComponent } from './home-app.component';
import { HomeComponent } from './components/home.component';

const routes: Routes = [
  {
    path: '', component: HomeAppComponent,
    children: [     
      { path: '', component: HomeComponent }
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
