import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RapportSousMenuComponent } from './rapport-sous-menu.component';

describe('RapportSousMenuComponent', () => {
  let component: RapportSousMenuComponent;
  let fixture: ComponentFixture<RapportSousMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RapportSousMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RapportSousMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
