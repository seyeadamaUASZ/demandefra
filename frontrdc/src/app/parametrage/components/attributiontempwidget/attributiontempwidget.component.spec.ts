import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttributiontempwidgetComponent } from './attributiontempwidget.component';

describe('AttributiontempwidgetComponent', () => {
  let component: AttributiontempwidgetComponent;
  let fixture: ComponentFixture<AttributiontempwidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttributiontempwidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributiontempwidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
