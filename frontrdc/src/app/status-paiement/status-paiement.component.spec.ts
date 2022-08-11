import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusPaiementComponent } from './status-paiement.component';

describe('StatusPaiementComponent', () => {
  let component: StatusPaiementComponent;
  let fixture: ComponentFixture<StatusPaiementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatusPaiementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatusPaiementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
