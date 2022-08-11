import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ChartsModule } from 'ng2-charts';
import { HomeRoutingModule } from './home-routing.module';
import { HomeAppComponent } from './home-app.component';
import { HomeComponent } from './components/home.component';
import { MatExpansionModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { UsersBarreComponent } from './components/widgets/users-barre/users-barre.component';
import { StatbatonComponent } from './components/widgets/statbaton/statbaton.component';
import { AppstatcirculaireComponent } from './components/widgets/appstatcirculaire/appstatcirculaire.component';
import {ChartModule,HIGHCHARTS_MODULES} from 'angular-highcharts';
import { StatfolderComponent } from './components/widgets/statfolder/statfolder.component';
import { Statfolder1Component } from './components/widgets/statfolder1/statfolder1.component';
import * as more from 'highcharts/highcharts-more.src';
import * as exporting from 'highcharts/modules/exporting.src';
import * as exportData from 'highcharts/modules/export-data.src';
import * as HighchartSankey from "highcharts/modules/sankey.src";
import * as HighchartsWheel from "highcharts/modules/dependency-wheel.src";
import * as highcharts3D from 'highcharts/highcharts-3d.src'; 
import { VisualiserboxComponent } from './components/widgets/visualiserbox/visualiserbox.component';
import { SharedcomponentModule } from 'src/app/sharedcomponent/sharedcomponent.module';
import { ApplicationRoutingModule } from 'src/app/application/application-routing.module';
import { MaterialModule } from 'src/app/shared/material.module';
import { Statfolder2Component } from './components/widgets/statfolder2/statfolder2.component';
import { Statfolder3Component } from './components/widgets/statfolder3/statfolder3.component';
import { Statfolder4Component } from './components/widgets/statfolder4/statfolder4.component';
import { Statfolder5Component } from './components/widgets/statfolder5/statfolder5.component';
@NgModule({
  declarations: [HomeAppComponent, HomeComponent, UsersBarreComponent, StatbatonComponent, AppstatcirculaireComponent, StatfolderComponent, Statfolder1Component, Statfolder2Component, Statfolder3Component, Statfolder4Component, Statfolder5Component, VisualiserboxComponent],
  imports: [
    CommonModule,
    ChartsModule,
    HomeRoutingModule,
    SharedcomponentModule,
    ApplicationRoutingModule,
    MatExpansionModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
    ChartModule
  ],schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  providers: [DatePipe,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
    { provide: HIGHCHARTS_MODULES, useFactory: () => [ more,HighchartSankey,HighchartsWheel,highcharts3D,exporting,exportData ] } // add as factory to your providers
  ],
})
export class HomeModule { }
