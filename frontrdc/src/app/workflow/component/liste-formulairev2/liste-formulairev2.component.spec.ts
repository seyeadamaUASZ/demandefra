import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeFormulairev2Component } from './liste-formulairev2.component';

describe('ListeFormulairev2Component', () => {
  let component: ListeFormulairev2Component;
  let fixture: ComponentFixture<ListeFormulairev2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListeFormulairev2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListeFormulairev2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
