import { TestBed } from '@angular/core/testing';

import { GenerateurSService } from './generateur-s.service';

describe('GenerateurSService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GenerateurSService = TestBed.get(GenerateurSService);
    expect(service).toBeTruthy();
  });
});
