import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupeserviceComponent } from './groupeservice.component';

describe('GroupeserviceComponent', () => {
  let component: GroupeserviceComponent;
  let fixture: ComponentFixture<GroupeserviceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupeserviceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupeserviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
