import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTraductionComponent } from './edit-traduction.component';

describe('EditTraductionComponent', () => {
  let component: EditTraductionComponent;
  let fixture: ComponentFixture<EditTraductionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditTraductionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTraductionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
