import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeCssComponent } from './change-css.component';

describe('ChangeCssComponent', () => {
  let component: ChangeCssComponent;
  let fixture: ComponentFixture<ChangeCssComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeCssComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeCssComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
