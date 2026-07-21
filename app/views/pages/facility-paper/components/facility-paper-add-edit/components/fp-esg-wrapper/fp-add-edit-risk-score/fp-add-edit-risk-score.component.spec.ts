import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpAddEditRiskScoreComponent } from './fp-add-edit-risk-score.component';

describe('FpAddEditRiskScoreComponent', () => {
  let component: FpAddEditRiskScoreComponent;
  let fixture: ComponentFixture<FpAddEditRiskScoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpAddEditRiskScoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpAddEditRiskScoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
