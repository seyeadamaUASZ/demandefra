import { TestBed } from '@angular/core/testing';

import { ChefdebureauService } from './chefdebureau.service';

describe('ChefdebureauService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ChefdebureauService = TestBed.get(ChefdebureauService);
    expect(service).toBeTruthy();
  });
});
