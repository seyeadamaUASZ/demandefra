import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatfolderComponent } from './statfolder.component';

describe('StatfolderComponent', () => {
  let component: StatfolderComponent;
  let fixture: ComponentFixture<StatfolderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatfolderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatfolderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
