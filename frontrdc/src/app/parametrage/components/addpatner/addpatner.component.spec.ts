import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddpatnerComponent } from './addpatner.component';

describe('AddpatnerComponent', () => {
  let component: AddpatnerComponent;
  let fixture: ComponentFixture<AddpatnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddpatnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddpatnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
