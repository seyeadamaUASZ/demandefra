import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppstatcirculaireComponent } from './appstatcirculaire.component';

describe('AppstatcirculaireComponent', () => {
  let component: AppstatcirculaireComponent;
  let fixture: ComponentFixture<AppstatcirculaireComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppstatcirculaireComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppstatcirculaireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
