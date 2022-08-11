import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewListRapportGenComponent } from './view-list-rapport-gen.component';

describe('ViewListRapportGenComponent', () => {
  let component: ViewListRapportGenComponent;
  let fixture: ComponentFixture<ViewListRapportGenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewListRapportGenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewListRapportGenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
