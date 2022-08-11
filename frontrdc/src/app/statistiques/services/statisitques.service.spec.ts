import { TestBed } from '@angular/core/testing';

import { StatisitquesService } from './statisitques.service';

describe('StatisitquesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StatisitquesService = TestBed.get(StatisitquesService);
    expect(service).toBeTruthy();
  });
});
