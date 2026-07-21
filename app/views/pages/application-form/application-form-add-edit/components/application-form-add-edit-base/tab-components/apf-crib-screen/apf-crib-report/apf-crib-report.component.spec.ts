import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfCribReportComponent } from './apf-crib-report.component';

describe('ApfCribReportComponent', () => {
  let component: ApfCribReportComponent;
  let fixture: ComponentFixture<ApfCribReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfCribReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfCribReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
