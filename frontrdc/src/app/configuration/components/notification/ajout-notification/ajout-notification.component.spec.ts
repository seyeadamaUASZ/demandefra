import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutNotificationComponent } from './ajout-notification.component';

describe('AjoutNotificationComponent', () => {
  let component: AjoutNotificationComponent;
  let fixture: ComponentFixture<AjoutNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
