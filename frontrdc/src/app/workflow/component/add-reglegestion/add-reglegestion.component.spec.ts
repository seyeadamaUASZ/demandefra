import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddReglegestionComponent } from './add-reglegestion.component';

describe('AddReglegestionComponent', () => {
  let component: AddReglegestionComponent;
  let fixture: ComponentFixture<AddReglegestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddReglegestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddReglegestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
