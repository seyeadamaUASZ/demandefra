import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionWidgetComponent } from './gestion-widget.component';

describe('GestionWidgetComponent', () => {
  let component: GestionWidgetComponent;
  let fixture: ComponentFixture<GestionWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestionWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
