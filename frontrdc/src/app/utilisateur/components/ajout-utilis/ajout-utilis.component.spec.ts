import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutUtilisComponent } from './ajout-utilis.component';

describe('AjoutUtilisComponent', () => {
  let component: AjoutUtilisComponent;
  let fixture: ComponentFixture<AjoutUtilisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutUtilisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutUtilisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
