import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualisergraphComponent } from './visualisergraph.component';

describe('VisualisergraphComponent', () => {
  let component: VisualisergraphComponent;
  let fixture: ComponentFixture<VisualisergraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualisergraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualisergraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
