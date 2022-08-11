import { TestBed } from '@angular/core/testing';

import { FonctionnaliteService } from './fonctionnalite.service';

describe('FonctionnaliteService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FonctionnaliteService = TestBed.get(FonctionnaliteService);
    expect(service).toBeTruthy();
  });
});
