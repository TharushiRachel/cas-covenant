import { TestBed } from '@angular/core/testing';

import { EnvironmentalRiskAnnexureService } from './environmental-risk-annexure.service';

describe('EnvironmentalRiskAnnexureService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EnvironmentalRiskAnnexureService = TestBed.get(EnvironmentalRiskAnnexureService);
    expect(service).toBeTruthy();
  });
});
