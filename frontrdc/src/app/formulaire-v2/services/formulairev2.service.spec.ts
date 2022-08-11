import { TestBed } from '@angular/core/testing';

import { Formulairev2Service } from './formulairev2.service';

describe('Formulairev2Service', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: Formulairev2Service = TestBed.get(Formulairev2Service);
    expect(service).toBeTruthy();
  });
});
