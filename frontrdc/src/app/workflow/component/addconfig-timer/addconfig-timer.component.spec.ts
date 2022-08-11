import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddconfigTimerComponent } from './addconfig-timer.component';

describe('AddconfigTimerComponent', () => {
  let component: AddconfigTimerComponent;
  let fixture: ComponentFixture<AddconfigTimerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddconfigTimerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddconfigTimerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
