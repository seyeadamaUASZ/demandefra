import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutAppComponent } from './ajout-app.component';

describe('AjoutAppComponent', () => {
  let component: AjoutAppComponent;
  let fixture: ComponentFixture<AjoutAppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutAppComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
