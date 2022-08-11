import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutSousMenuComponent } from './ajout-sous-menu.component';

describe('AllocateroleComponent', () => {
  let component: AjoutSousMenuComponent;
  let fixture: ComponentFixture<AjoutSousMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutSousMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutSousMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
