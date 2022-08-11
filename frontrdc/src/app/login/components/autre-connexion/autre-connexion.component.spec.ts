import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AutreConnexionComponent } from './autre-connexion.component';

describe('AutreConnexionComponent', () => {
  let component: AutreConnexionComponent;
  let fixture: ComponentFixture<AutreConnexionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AutreConnexionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AutreConnexionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
