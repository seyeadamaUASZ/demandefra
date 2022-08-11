import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestconfigurationComponent } from './testconfiguration.component';

describe('TestconfigurationComponent', () => {
  let component: TestconfigurationComponent;
  let fixture: ComponentFixture<TestconfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestconfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestconfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
