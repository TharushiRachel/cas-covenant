import { TestBed } from '@angular/core/testing';

import { ApplicationFormSearchService } from './application-form-search.service';

describe('ApplicationFormSearchService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ApplicationFormSearchService = TestBed.get(ApplicationFormSearchService);
    expect(service).toBeTruthy();
  });
});
