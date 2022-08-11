import { TestBed } from '@angular/core/testing';

import { MonCompteService } from './mon-compte.service';

describe('MonCompteService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MonCompteService = TestBed.get(MonCompteService);
    expect(service).toBeTruthy();
  });
});
