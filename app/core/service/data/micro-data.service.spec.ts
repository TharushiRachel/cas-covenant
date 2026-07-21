import { TestBed } from '@angular/core/testing';

import { MicroDataService } from './micro-data.service';

describe('MicroDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MicroDataService = TestBed.get(MicroDataService);
    expect(service).toBeTruthy();
  });
});
