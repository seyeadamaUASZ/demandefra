import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { ExceptionRoutingModule } from './exception-routing.module';
import { SharedcomponentModule } from '../sharedcomponent/sharedcomponent.module';
import { ExceptionAppComponent } from './exception-app.component';
import { UnauthorizedPageComponent } from './components/unauthorized-page/unauthorized-page.component';

@NgModule({
  declarations: [ExceptionAppComponent, UnauthorizedPageComponent],
  imports: [
    CommonModule,
    ExceptionRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedcomponentModule,
  ]
})
export class ExceptionModule { }
