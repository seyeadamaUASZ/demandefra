import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifyBlockchainComponent } from './verify-blockchain.component';

describe('VerifyBlockchainComponent', () => {
  let component: VerifyBlockchainComponent;
  let fixture: ComponentFixture<VerifyBlockchainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerifyBlockchainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerifyBlockchainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
