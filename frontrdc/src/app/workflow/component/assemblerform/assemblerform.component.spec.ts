import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssemblerformComponent } from './assemblerform.component';

describe('AssemblerformComponent', () => {
  let component: AssemblerformComponent;
  let fixture: ComponentFixture<AssemblerformComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssemblerformComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssemblerformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
