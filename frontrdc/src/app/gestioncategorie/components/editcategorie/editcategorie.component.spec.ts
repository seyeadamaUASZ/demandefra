import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditcategorieComponent } from './editcategorie.component';

describe('EditcategorieComponent', () => {
  let component: EditcategorieComponent;
  let fixture: ComponentFixture<EditcategorieComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditcategorieComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditcategorieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
