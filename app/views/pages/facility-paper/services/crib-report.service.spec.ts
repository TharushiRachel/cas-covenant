import { TestBed } from '@angular/core/testing';

import { CribReportService } from './crib-report.service';

describe('CribReportService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CribReportService = TestBed.get(CribReportService);
    expect(service).toBeTruthy();
  });
});
