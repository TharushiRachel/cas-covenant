import { TestBed } from '@angular/core/testing';

import { SectionSubSectionService } from './section-sub-section.service';

describe('SectionSubSectionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SectionSubSectionService = TestBed.get(SectionSubSectionService);
    expect(service).toBeTruthy();
  });
});
