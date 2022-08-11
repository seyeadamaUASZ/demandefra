import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Statfolder1Component } from './statfolder1.component';

describe('Statfolder1Component', () => {
  let component: Statfolder1Component;
  let fixture: ComponentFixture<Statfolder1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Statfolder1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Statfolder1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
