import { TestBed } from '@angular/core/testing';

import { OperateurPaiementService } from './operateur-paiement.service';

describe('OperateurPaiementService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OperateurPaiementService = TestBed.get(OperateurPaiementService);
    expect(service).toBeTruthy();
  });
});
