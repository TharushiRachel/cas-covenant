import { TestBed } from '@angular/core/testing';

import { SectionSubSectionAddEditService } from './section-sub-section-add-edit.service';

describe('SectionSubSectionAddEditService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SectionSubSectionAddEditService = TestBed.get(SectionSubSectionAddEditService);
    expect(service).toBeTruthy();
  });
});
