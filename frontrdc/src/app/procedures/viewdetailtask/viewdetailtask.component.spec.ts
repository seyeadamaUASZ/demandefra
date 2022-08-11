import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewdetailtaskComponent } from './viewdetailtask.component';

describe('ViewworkflowComponent', () => {
  let component: ViewdetailtaskComponent;
  let fixture: ComponentFixture<ViewdetailtaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewdetailtaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewdetailtaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
