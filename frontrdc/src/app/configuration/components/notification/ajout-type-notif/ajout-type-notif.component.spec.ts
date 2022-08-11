import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutTypeNotifComponent } from './ajout-type-notif.component';

describe('AjoutTypeNotifComponent', () => {
  let component: AjoutTypeNotifComponent;
  let fixture: ComponentFixture<AjoutTypeNotifComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutTypeNotifComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutTypeNotifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
