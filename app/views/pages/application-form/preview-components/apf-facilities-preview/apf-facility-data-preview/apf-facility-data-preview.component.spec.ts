import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFacilityDataPreviewComponent } from './apf-facility-data-preview.component';

describe('ApfFacilityDataPreviewComponent', () => {
  let component: ApfFacilityDataPreviewComponent;
  let fixture: ComponentFixture<ApfFacilityDataPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFacilityDataPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilityDataPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
