import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatbatonComponent } from './statbaton.component';

describe('StatbatonComponent', () => {
  let component: StatbatonComponent;
  let fixture: ComponentFixture<StatbatonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatbatonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatbatonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
