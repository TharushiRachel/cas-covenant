import { TestBed } from '@angular/core/testing';

import { FinacleService } from './finacle.service';

describe('FinacleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FinacleService = TestBed.get(FinacleService);
    expect(service).toBeTruthy();
  });
});
