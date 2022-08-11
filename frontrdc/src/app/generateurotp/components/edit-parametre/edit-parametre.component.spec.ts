import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditParametreComponent } from './edit-parametre.component';

describe('EditParametreComponent', () => {
  let component: EditParametreComponent;
  let fixture: ComponentFixture<EditParametreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditParametreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditParametreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
