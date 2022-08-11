import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionLandingPageComponent } from './gestion-landing-page.component';

describe('GestionLandingPageComponent', () => {
  let component: GestionLandingPageComponent;
  let fixture: ComponentFixture<GestionLandingPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestionLandingPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionLandingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
