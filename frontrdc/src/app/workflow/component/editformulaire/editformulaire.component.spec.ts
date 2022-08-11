import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditformulaireComponent } from './editformulaire.component';

describe('EditformulaireComponent', () => {
  let component: EditformulaireComponent;
  let fixture: ComponentFixture<EditformulaireComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditformulaireComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditformulaireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
