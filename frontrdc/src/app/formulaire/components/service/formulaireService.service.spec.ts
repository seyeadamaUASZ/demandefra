/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { FormulaireServiceService } from './formulaireService.service';

describe('Service: FormulaireService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FormulaireServiceService]
    });
  });

  it('should ...', inject([FormulaireServiceService], (service: FormulaireServiceService) => {
    expect(service).toBeTruthy();
  }));
});
