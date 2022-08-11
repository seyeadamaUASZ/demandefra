/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { SignatureDocumentService } from './signatureDocument.service';

describe('Service: SignatureDocument', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SignatureDocumentService]
    });
  });

  it('should ...', inject([SignatureDocumentService], (service: SignatureDocumentService) => {
    expect(service).toBeTruthy();
  }));
});
