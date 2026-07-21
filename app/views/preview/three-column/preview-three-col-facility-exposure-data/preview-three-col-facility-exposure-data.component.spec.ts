import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewThreeColFacilityExposureDataComponent } from './preview-three-col-facility-exposure-data.component';

describe('PreviewThreeColFacilityExposureDataComponent', () => {
  let component: PreviewThreeColFacilityExposureDataComponent;
  let fixture: ComponentFixture<PreviewThreeColFacilityExposureDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewThreeColFacilityExposureDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewThreeColFacilityExposureDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
