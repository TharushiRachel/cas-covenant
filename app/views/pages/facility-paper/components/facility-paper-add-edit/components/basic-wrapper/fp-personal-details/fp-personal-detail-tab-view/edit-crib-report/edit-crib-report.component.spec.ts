import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCribReportComponent } from './edit-crib-report.component';

describe('EditCribReportComponent', () => {
  let component: EditCribReportComponent;
  let fixture: ComponentFixture<EditCribReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditCribReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCribReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
