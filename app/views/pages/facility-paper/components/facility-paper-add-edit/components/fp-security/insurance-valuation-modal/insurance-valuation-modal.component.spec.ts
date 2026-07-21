import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceValuationModalComponent } from './insurance-valuation-modal.component';

describe('InsuranceValuationModalComponent', () => {
  let component: InsuranceValuationModalComponent;
  let fixture: ComponentFixture<InsuranceValuationModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceValuationModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceValuationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
