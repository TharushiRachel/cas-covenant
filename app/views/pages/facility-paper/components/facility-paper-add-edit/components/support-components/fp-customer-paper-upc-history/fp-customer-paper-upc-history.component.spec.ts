import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCustomerPaperUpcHistoryComponent } from './fp-customer-paper-upc-history.component';

describe('FpCustomerPaperUpcHistoryComponent', () => {
  let component: FpCustomerPaperUpcHistoryComponent;
  let fixture: ComponentFixture<FpCustomerPaperUpcHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCustomerPaperUpcHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCustomerPaperUpcHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
