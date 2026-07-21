import { TestBed } from '@angular/core/testing';

import { GuaranteeVolumeService } from './guarantee-volume.service';

describe('GuaranteeVolumeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GuaranteeVolumeService = TestBed.get(GuaranteeVolumeService);
    expect(service).toBeTruthy();
  });
});
