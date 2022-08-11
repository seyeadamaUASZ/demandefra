import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttriSousMenuComponent } from './attri-sous-menu.component';

describe('AllocateroleComponent', () => {
  let component: AttriSousMenuComponent;
  let fixture: ComponentFixture<AttriSousMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttriSousMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttriSousMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
