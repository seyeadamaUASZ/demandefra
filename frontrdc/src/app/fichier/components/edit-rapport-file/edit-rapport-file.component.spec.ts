import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditRapportFileComponent } from './edit-rapport-file.component';

describe('EditFichierComponent', () => {
  let component: EditRapportFileComponent;
  let fixture: ComponentFixture<EditRapportFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditRapportFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditRapportFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
