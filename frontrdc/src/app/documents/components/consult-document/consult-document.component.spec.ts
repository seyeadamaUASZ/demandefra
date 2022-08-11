import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultDocumentComponent } from './consult-document.component';

describe('ConsultDocumentComponent', () => {
  let component: ConsultDocumentComponent;
  let fixture: ComponentFixture<ConsultDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultDocumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
