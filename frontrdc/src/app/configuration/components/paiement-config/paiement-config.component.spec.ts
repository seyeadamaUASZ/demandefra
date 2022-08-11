import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaiementConfigComponent } from './paiement-config.component';

describe('PaiementConfigComponent', () => {
  let component: PaiementConfigComponent;
  let fixture: ComponentFixture<PaiementConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaiementConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaiementConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
