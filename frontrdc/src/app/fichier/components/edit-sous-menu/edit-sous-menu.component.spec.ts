import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSousMenuComponent } from './edit-sous-menu.component';

describe('AllocateroleComponent', () => {
  let component: EditSousMenuComponent;
  let fixture: ComponentFixture<EditSousMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditSousMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSousMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
