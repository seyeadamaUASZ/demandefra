import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersBarreComponent } from './users-barre.component';

describe('UsersBarreComponent', () => {
  let component: UsersBarreComponent;
  let fixture: ComponentFixture<UsersBarreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersBarreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersBarreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
