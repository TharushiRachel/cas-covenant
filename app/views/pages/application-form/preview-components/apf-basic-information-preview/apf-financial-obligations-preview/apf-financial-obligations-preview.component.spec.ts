import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFinancialObligationsPreviewComponent } from './apf-financial-obligations-preview.component';

describe('ApfFinancialObligationsPreviewComponent', () => {
  let component: ApfFinancialObligationsPreviewComponent;
  let fixture: ComponentFixture<ApfFinancialObligationsPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFinancialObligationsPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFinancialObligationsPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
