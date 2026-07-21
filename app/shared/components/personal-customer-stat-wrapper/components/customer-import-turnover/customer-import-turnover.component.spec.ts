import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerImportTurnoverComponent } from './customer-import-turnover.component';

describe('CustomerImportTurnoverComponent', () => {
  let component: CustomerImportTurnoverComponent;
  let fixture: ComponentFixture<CustomerImportTurnoverComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerImportTurnoverComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerImportTurnoverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
