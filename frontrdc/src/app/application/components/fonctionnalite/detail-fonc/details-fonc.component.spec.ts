import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsFoncComponent } from './details-fonc.component';

describe('DetailsFoncComponent', () => {
  let component: DetailsFoncComponent;
  let fixture: ComponentFixture<DetailsFoncComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailsFoncComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailsFoncComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
