/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PrivilegeSignerService } from './privilegeSigner.service';

describe('Service: PrivilegeSigner', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PrivilegeSignerService]
    });
  });

  it('should ...', inject([PrivilegeSignerService], (service: PrivilegeSignerService) => {
    expect(service).toBeTruthy();
  }));
});
