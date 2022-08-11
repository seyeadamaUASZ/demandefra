import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EtapeCreationAppComponent } from './etape-creation-app.component';

describe('EtapeCreationAppComponent', () => {
  let component: EtapeCreationAppComponent;
  let fixture: ComponentFixture<EtapeCreationAppComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EtapeCreationAppComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EtapeCreationAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
