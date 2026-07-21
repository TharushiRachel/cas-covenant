import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFinacleCustomerUpdateComponent } from './apf-finacle-customer-update.component';

describe('ApfFinacleCustomerUpdateComponent', () => {
  let component: ApfFinacleCustomerUpdateComponent;
  let fixture: ComponentFixture<ApfFinacleCustomerUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFinacleCustomerUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFinacleCustomerUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
