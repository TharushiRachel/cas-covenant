import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccCostOfFundsComponent } from './bcc-cost-of-funds.component';

describe('BccCostOfFundsComponent', () => {
  let component: BccCostOfFundsComponent;
  let fixture: ComponentFixture<BccCostOfFundsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccCostOfFundsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccCostOfFundsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
