import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailUtilisComponent } from './detail-utilis.component';

describe('DetailUtilisComponent', () => {
  let component: DetailUtilisComponent;
  let fixture: ComponentFixture<DetailUtilisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailUtilisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailUtilisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
