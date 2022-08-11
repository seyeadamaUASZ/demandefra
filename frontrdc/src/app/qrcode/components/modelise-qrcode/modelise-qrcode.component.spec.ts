import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {ModeliseQrcodeComponent } from './modelise-qrcode.component';

describe('EditFormComponent', () => {
  let component: ModeliseQrcodeComponent;
  let fixture: ComponentFixture<ModeliseQrcodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModeliseQrcodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeliseQrcodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
