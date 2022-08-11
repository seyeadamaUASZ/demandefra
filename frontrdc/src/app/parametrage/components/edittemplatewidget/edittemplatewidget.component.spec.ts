import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EdittemplatewidgetComponent } from './edittemplatewidget.component';

describe('EdittemplatewidgetComponent', () => {
  let component: EdittemplatewidgetComponent;
  let fixture: ComponentFixture<EdittemplatewidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EdittemplatewidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EdittemplatewidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
