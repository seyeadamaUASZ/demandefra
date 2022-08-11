import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditworkflowtaskComponent } from './editworkflowtask.component';

describe('EditworkflowtaskComponent', () => {
  let component: EditworkflowtaskComponent;
  let fixture: ComponentFixture<EditworkflowtaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditworkflowtaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditworkflowtaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
