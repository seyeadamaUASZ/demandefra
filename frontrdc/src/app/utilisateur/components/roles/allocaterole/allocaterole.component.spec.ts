import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllocateroleComponent } from './allocaterole.component';

describe('AllocateroleComponent', () => {
  let component: AllocateroleComponent;
  let fixture: ComponentFixture<AllocateroleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllocateroleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllocateroleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
