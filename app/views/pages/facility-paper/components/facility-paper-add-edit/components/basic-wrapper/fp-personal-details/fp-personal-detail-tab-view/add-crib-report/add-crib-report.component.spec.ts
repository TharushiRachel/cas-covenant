import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCribReportComponent } from './add-crib-report.component';

describe('AddCribReportComponent', () => {
  let component: AddCribReportComponent;
  let fixture: ComponentFixture<AddCribReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCribReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCribReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
