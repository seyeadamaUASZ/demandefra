import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditattrtempwidgetComponent } from './editattrtempwidget.component';

describe('EditattrtempwidgetComponent', () => {
  let component: EditattrtempwidgetComponent;
  let fixture: ComponentFixture<EditattrtempwidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditattrtempwidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditattrtempwidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
