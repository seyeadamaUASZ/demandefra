import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeFormulaireComponent } from './liste-formulaire.component';

describe('ListeFormulaireComponent', () => {
  let component: ListeFormulaireComponent;
  let fixture: ComponentFixture<ListeFormulaireComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListeFormulaireComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeFormulaireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
