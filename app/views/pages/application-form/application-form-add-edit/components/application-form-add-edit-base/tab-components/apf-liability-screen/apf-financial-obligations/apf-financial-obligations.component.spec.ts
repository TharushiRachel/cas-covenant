import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfFinancialObligationsComponent} from './apf-financial-obligations.component';

describe('ApfFinancialObligationsPreviewComponent', () => {
  let component: ApfFinancialObligationsComponent;
  let fixture: ComponentFixture<ApfFinancialObligationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfFinancialObligationsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFinancialObligationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
