import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MainContentWorkflowComponent } from './main-content-workflow.component';

describe('MainContentWorkflowComponent', () => {
  let component: MainContentWorkflowComponent;
  let fixture: ComponentFixture<MainContentWorkflowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainContentWorkflowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainContentWorkflowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
