import { TestBed } from '@angular/core/testing';

import { CasV1Service } from './cas-v1.service';

describe('CasV1Service', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CasV1Service = TestBed.get(CasV1Service);
    expect(service).toBeTruthy();
  });
});
