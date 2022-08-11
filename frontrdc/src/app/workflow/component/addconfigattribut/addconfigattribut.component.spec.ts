import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddconfigattributComponent } from './addconfigattribut.component';

describe('AddconfigattributComponent', () => {
  let component: AddconfigattributComponent;
  let fixture: ComponentFixture<AddconfigattributComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddconfigattributComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddconfigattributComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
