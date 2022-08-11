import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTypeNotifComponent } from './edit-type-notif.component';

describe('EditTypeNotifComponent', () => {
  let component: EditTypeNotifComponent;
  let fixture: ComponentFixture<EditTypeNotifComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditTypeNotifComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTypeNotifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
