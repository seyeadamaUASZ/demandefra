import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutBlockchainComponent } from './ajout-blockchain.component';

describe('AjoutBlockchainComponent', () => {
  let component: AjoutBlockchainComponent;
  let fixture: ComponentFixture<AjoutBlockchainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutBlockchainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutBlockchainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
