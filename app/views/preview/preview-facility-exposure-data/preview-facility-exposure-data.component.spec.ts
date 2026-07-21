import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewFacilityExposureDataComponent } from './preview-facility-exposure-data.component';

describe('PreviewFacilityExposureDataComponent', () => {
  let component: PreviewFacilityExposureDataComponent;
  let fixture: ComponentFixture<PreviewFacilityExposureDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewFacilityExposureDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewFacilityExposureDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
