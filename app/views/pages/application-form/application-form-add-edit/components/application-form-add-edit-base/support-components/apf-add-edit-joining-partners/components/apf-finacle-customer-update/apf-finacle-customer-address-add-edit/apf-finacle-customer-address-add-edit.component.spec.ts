import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFinacleCustomerAddressAddEditComponent } from './apf-finacle-customer-address-add-edit.component';

describe('ApfFinacleCustomerAddressAddEditComponent', () => {
  let component: ApfFinacleCustomerAddressAddEditComponent;
  let fixture: ComponentFixture<ApfFinacleCustomerAddressAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFinacleCustomerAddressAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFinacleCustomerAddressAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
