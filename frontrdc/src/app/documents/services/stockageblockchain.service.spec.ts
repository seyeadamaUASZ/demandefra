import { TestBed } from '@angular/core/testing';

import { StockageblockchainService } from './stockageblockchain.service';

describe('StockageblockchainService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StockageblockchainService = TestBed.get(StockageblockchainService);
    expect(service).toBeTruthy();
  });
});
