import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCriterePwdComponent } from './edit-critere-pwd.component';

describe('EditCriterePwdComponent', () => {
  let component: EditCriterePwdComponent;
  let fixture: ComponentFixture<EditCriterePwdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditCriterePwdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCriterePwdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
