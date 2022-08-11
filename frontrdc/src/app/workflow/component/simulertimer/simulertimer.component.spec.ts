import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulertimerComponent } from './simulertimer.component';

describe('SimulertimerComponent', () => {
  let component: SimulertimerComponent;
  let fixture: ComponentFixture<SimulertimerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SimulertimerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulertimerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
