import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFinacleCustomerContactAddEditComponent } from './apf-finacle-customer-contact-add-edit.component';

describe('ApfFinacleCustomerContactAddEditComponent', () => {
  let component: ApfFinacleCustomerContactAddEditComponent;
  let fixture: ComponentFixture<ApfFinacleCustomerContactAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFinacleCustomerContactAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFinacleCustomerContactAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
