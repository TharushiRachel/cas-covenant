import { TestBed } from '@angular/core/testing';

import { EnvironmentalRiskService } from './environmental-risk.service';

describe('EnvironmentalRiskService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EnvironmentalRiskService = TestBed.get(EnvironmentalRiskService);
    expect(service).toBeTruthy();
  });
});
