import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddtemplatewidgetComponent } from './addtemplatewidget.component';

describe('AddtemplatewidgetComponent', () => {
  let component: AddtemplatewidgetComponent;
  let fixture: ComponentFixture<AddtemplatewidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddtemplatewidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddtemplatewidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
