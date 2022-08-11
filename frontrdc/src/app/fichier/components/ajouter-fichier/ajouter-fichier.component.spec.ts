import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjouterFichierComponent } from './ajouter-fichier.component';

describe('AjouterFichierComponent', () => {
  let component: AjouterFichierComponent;
  let fixture: ComponentFixture<AjouterFichierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjouterFichierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjouterFichierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
