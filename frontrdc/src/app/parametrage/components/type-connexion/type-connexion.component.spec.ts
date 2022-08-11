import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeConnexionComponent } from './type-connexion.component';

describe('TypeConnexionComponent', () => {
  let component: TypeConnexionComponent;
  let fixture: ComponentFixture<TypeConnexionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TypeConnexionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeConnexionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
