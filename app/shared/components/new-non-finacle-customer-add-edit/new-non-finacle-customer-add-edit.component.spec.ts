import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewNonFinacleCustomerAddEditComponent } from './new-non-finacle-customer-add-edit.component';

describe('NewNonFinacleCustomerAddEditComponent', () => {
  let component: NewNonFinacleCustomerAddEditComponent;
  let fixture: ComponentFixture<NewNonFinacleCustomerAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewNonFinacleCustomerAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewNonFinacleCustomerAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
