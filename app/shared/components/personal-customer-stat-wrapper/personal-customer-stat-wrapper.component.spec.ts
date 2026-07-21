import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalCustomerStatWrapperComponent } from './personal-customer-stat-wrapper.component';

describe('PersonalCustomerStatWrapperComponent', () => {
  let component: PersonalCustomerStatWrapperComponent;
  let fixture: ComponentFixture<PersonalCustomerStatWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalCustomerStatWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalCustomerStatWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
