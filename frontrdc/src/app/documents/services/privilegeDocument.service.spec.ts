/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PrivilegeDocumentService } from './privilegeDocument.service';

describe('Service: PrivilegeDocument', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PrivilegeDocumentService]
    });
  });

  it('should ...', inject([PrivilegeDocumentService], (service: PrivilegeDocumentService) => {
    expect(service).toBeTruthy();
  }));
});
