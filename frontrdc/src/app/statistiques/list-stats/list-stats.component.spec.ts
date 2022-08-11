import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStatsComponent } from './list-stats.component';

describe('ListStatsComponent', () => {
  let component: ListStatsComponent;
  let fixture: ComponentFixture<ListStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
