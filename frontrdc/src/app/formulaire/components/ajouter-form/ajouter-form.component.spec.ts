import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjouterFormComponent } from './ajouter-form.component';

describe('AjouterFormComponent', () => {
  let component: AjouterFormComponent;
  let fixture: ComponentFixture<AjouterFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjouterFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjouterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
