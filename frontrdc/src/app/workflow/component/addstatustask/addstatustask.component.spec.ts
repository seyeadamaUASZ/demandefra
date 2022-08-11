import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddstatustaskComponent } from './addstatustask.component';

describe('AddstatustaskComponent', () => {
  let component: AddstatustaskComponent;
  let fixture: ComponentFixture<AddstatustaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddstatustaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddstatustaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
