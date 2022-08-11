import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddconfigWidgetComponent } from './addconfig-widget.component';

describe('AddconfigWidgetComponent', () => {
  let component: AddconfigWidgetComponent;
  let fixture: ComponentFixture<AddconfigWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddconfigWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddconfigWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
