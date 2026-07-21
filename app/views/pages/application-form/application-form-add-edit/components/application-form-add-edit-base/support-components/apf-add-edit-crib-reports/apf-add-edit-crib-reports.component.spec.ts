import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditCribReportsComponent } from './apf-add-edit-crib-reports.component';

describe('ApfAddEditCribReportsComponent', () => {
  let component: ApfAddEditCribReportsComponent;
  let fixture: ComponentFixture<ApfAddEditCribReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditCribReportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditCribReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
