import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditUtilisComponent } from './edit-utilis.component';

describe('EditUtilisComponent', () => {
  let component: EditUtilisComponent;
  let fixture: ComponentFixture<EditUtilisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditUtilisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditUtilisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
