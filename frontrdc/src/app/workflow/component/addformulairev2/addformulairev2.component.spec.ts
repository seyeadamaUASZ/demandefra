import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Addformulairev2Component } from './addformulairev2.component';

describe('Addformulairev2Component', () => {
  let component: Addformulairev2Component;
  let fixture: ComponentFixture<Addformulairev2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Addformulairev2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Addformulairev2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
