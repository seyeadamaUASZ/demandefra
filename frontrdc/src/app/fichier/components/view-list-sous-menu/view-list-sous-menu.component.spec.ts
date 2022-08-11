import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewListSousMenuComponent } from './view-list-sous-menu.component';

describe('ViewListSousMenuComponent', () => {
  let component: ViewListSousMenuComponent;
  let fixture: ComponentFixture<ViewListSousMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewListSousMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewListSousMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
