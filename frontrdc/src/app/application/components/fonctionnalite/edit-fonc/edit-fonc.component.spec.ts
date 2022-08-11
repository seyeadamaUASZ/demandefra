import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditFoncComponent } from './edit-fonc.component';

describe('EditFoncComponent', () => {
  let component: EditFoncComponent;
  let fixture: ComponentFixture<EditFoncComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditFoncComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditFoncComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
