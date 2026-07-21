import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnApprovedPaperSummaryComponent } from './own-approved-paper-summary.component';

describe('OwnApprovedPaperSummaryComponent', () => {
  let component: OwnApprovedPaperSummaryComponent;
  let fixture: ComponentFixture<OwnApprovedPaperSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OwnApprovedPaperSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnApprovedPaperSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
