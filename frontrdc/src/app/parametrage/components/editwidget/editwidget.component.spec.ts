import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditwidgetComponent } from './editwidget.component';

describe('EditwidgetComponent', () => {
  let component: EditwidgetComponent;
  let fixture: ComponentFixture<EditwidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditwidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditwidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
