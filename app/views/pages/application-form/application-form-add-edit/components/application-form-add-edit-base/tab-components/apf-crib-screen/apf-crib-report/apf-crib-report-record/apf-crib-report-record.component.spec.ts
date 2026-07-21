import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfCribReportRecordComponent } from './apf-crib-report-record.component';

describe('ApfCribReportRecordComponent', () => {
  let component: ApfCribReportRecordComponent;
  let fixture: ComponentFixture<ApfCribReportRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfCribReportRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfCribReportRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
