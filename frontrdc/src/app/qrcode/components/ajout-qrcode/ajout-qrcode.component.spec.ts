import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutQrcodeComponent } from './ajout-qrcode.component';

describe('AjoutQrcodeComponent', () => {
  let component: AjoutQrcodeComponent;
  let fixture: ComponentFixture<AjoutQrcodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutQrcodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutQrcodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
