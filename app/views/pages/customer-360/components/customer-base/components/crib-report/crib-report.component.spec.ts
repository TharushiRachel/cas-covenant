import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CribReportComponent } from './crib-report.component';

describe('CribReportComponent', () => {
  let component: CribReportComponent;
  let fixture: ComponentFixture<CribReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CribReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CribReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
