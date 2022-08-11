import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FichierExportformComponent } from './fichier-exportform.component';

describe('FichierFormComponent', () => {
  let component: FichierExportformComponent;
  let fixture: ComponentFixture<FichierExportformComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FichierExportformComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FichierExportformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
