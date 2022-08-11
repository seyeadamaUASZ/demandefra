import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChoixFoncComponent } from './choix-fonc.component';

describe('ChoixFoncComponent', () => {
  let component: ChoixFoncComponent;
  let fixture: ComponentFixture<ChoixFoncComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChoixFoncComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChoixFoncComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
