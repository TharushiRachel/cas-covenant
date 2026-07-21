import { TestBed } from '@angular/core/testing';

import { AdvanceAnalyticsService } from './advance-analytics.service';

describe('AdvanceAnalyticsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AdvanceAnalyticsService = TestBed.get(AdvanceAnalyticsService);
    expect(service).toBeTruthy();
  });
});
