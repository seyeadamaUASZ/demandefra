import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigurationMainContentComponent } from './configuration-main-content.component';

describe('ConfigurationMainContentComponent', () => {
  let component: ConfigurationMainContentComponent;
  let fixture: ComponentFixture<ConfigurationMainContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigurationMainContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigurationMainContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
