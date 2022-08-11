import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditconfigattributComponent } from './editconfigattribut.component';

describe('EditconfigattributComponent', () => {
  let component: EditconfigattributComponent;
  let fixture: ComponentFixture<EditconfigattributComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditconfigattributComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditconfigattributComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
