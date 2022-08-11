import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutFoncComponent } from './ajout-fonc.component';

describe('AjoutFoncComponent', () => {
  let component: AjoutFoncComponent;
  let fixture: ComponentFixture<AjoutFoncComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutFoncComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutFoncComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
