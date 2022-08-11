import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QrcodeGenereComponent } from './qrcode-genere.component';

describe('FichierFormComponent', () => {
  let component: QrcodeGenereComponent;
  let fixture: ComponentFixture<QrcodeGenereComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QrcodeGenereComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QrcodeGenereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
