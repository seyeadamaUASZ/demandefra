import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MainContentPaiementComponent } from './main-content-paiement.component';

describe('MainContentPaiementComponent', () => {
  let component: MainContentPaiementComponent;
  let fixture: ComponentFixture<MainContentPaiementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainContentPaiementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainContentPaiementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
