import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerExportTurnoverComponent } from './customer-export-turnover.component';

describe('CustomerExportTurnoverComponent', () => {
  let component: CustomerExportTurnoverComponent;
  let fixture: ComponentFixture<CustomerExportTurnoverComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerExportTurnoverComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerExportTurnoverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
