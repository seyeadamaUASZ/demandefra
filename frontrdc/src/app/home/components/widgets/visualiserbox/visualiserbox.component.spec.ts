import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualiserboxComponent } from './visualiserbox.component';

describe('VisualiserboxComponent', () => {
  let component: VisualiserboxComponent;
  let fixture: ComponentFixture<VisualiserboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualiserboxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualiserboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
