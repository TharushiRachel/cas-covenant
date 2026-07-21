import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditFinancialObligationsComponent } from './apf-add-edit-financial-obligations.component';

describe('ApfAddEditFinancialObligationsComponent', () => {
  let component: ApfAddEditFinancialObligationsComponent;
  let fixture: ComponentFixture<ApfAddEditFinancialObligationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditFinancialObligationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditFinancialObligationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
