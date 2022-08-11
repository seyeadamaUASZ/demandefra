import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutParametreComponent } from './ajout-parametre.component';

describe('AjoutParametreComponent', () => {
  let component: AjoutParametreComponent;
  let fixture: ComponentFixture<AjoutParametreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutParametreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutParametreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
