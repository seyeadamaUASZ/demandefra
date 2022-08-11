import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MainContentUtilComponent } from './main-content-util.component';

describe('MainContentUtilComponent', () => {
  let component: MainContentUtilComponent;
  let fixture: ComponentFixture<MainContentUtilComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainContentUtilComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainContentUtilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
