import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityDataDocUploadComponent } from './facility-data-doc-upload.component';

describe('FacilityDataDocUploadComponent', () => {
  let component: FacilityDataDocUploadComponent;
  let fixture: ComponentFixture<FacilityDataDocUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityDataDocUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityDataDocUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
