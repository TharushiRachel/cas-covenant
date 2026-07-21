import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalCustomerRatingsComponent } from './personal-customer-ratings.component';

describe('PersonalCustomerRatingsComponent', () => {
  let component: PersonalCustomerRatingsComponent;
  let fixture: ComponentFixture<PersonalCustomerRatingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalCustomerRatingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalCustomerRatingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
