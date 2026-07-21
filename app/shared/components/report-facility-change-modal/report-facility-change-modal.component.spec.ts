import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportFacilityChangeModalComponent } from './report-facility-change-modal.component';

describe('ReportFacilityChangeModalComponent', () => {
  let component: ReportFacilityChangeModalComponent;
  let fixture: ComponentFixture<ReportFacilityChangeModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportFacilityChangeModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportFacilityChangeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
