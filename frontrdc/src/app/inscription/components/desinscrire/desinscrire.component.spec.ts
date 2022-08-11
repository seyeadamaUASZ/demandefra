import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DesinscrireComponent } from './desinscrire.component';

describe('DesinscrireComponent', () => {
  let component: DesinscrireComponent;
  let fixture: ComponentFixture<DesinscrireComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DesinscrireComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DesinscrireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
