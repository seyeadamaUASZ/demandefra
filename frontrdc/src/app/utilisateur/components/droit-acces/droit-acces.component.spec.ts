import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DroitAccesComponent } from './droit-acces.component';

describe('DroitAccesComponent', () => {
  let component: DroitAccesComponent;
  let fixture: ComponentFixture<DroitAccesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DroitAccesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DroitAccesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
