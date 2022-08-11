import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaiementRedirectComponent } from './paiement-redirect.component';

describe('PaiementRedirectComponent', () => {
  let component: PaiementRedirectComponent;
  let fixture: ComponentFixture<PaiementRedirectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaiementRedirectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaiementRedirectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
