import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppPublishedComponent } from './app-published.component';

describe('AppPublishedComponent', () => {
  let component: AppPublishedComponent;
  let fixture: ComponentFixture<AppPublishedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppPublishedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppPublishedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
