import { TestBed } from '@angular/core/testing';

import { DeviationService } from './deviation.service';

describe('DeviationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DeviationService = TestBed.get(DeviationService);
    expect(service).toBeTruthy();
  });
});
