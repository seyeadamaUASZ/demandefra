import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MainContentFormComponent } from './main-content-form.component';

describe('MainContentFormComponent', () => {
  let component: MainContentFormComponent;
  let fixture: ComponentFixture<MainContentFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainContentFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainContentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
