import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditRiskScoreComponent } from './apf-add-edit-risk-score.component';

describe('ApfAddEditRiskScoreComponent', () => {
  let component: ApfAddEditRiskScoreComponent;
  let fixture: ComponentFixture<ApfAddEditRiskScoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditRiskScoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditRiskScoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
