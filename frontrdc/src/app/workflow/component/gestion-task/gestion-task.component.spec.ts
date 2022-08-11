import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionTaskComponent } from './gestion-task.component';

describe('GestionTaskComponent', () => {
  let component: GestionTaskComponent;
  let fixture: ComponentFixture<GestionTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestionTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
