import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccRiskRatingYearComponent } from './bcc-risk-rating-year.component';

describe('BccRiskRatingYearComponent', () => {
  let component: BccRiskRatingYearComponent;
  let fixture: ComponentFixture<BccRiskRatingYearComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccRiskRatingYearComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccRiskRatingYearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
