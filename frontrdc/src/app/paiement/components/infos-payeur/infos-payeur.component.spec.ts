import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfosPayeurComponent } from './infos-payeur.component';

describe('InfosPayeurComponent', () => {
  let component: InfosPayeurComponent;
  let fixture: ComponentFixture<InfosPayeurComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfosPayeurComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfosPayeurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
