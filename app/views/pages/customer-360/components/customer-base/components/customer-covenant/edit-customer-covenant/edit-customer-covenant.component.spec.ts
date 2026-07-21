import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCustomerCovenantComponent } from './edit-customer-covenant.component';

describe('EditCustomerCovenantComponent', () => {
  let component: EditCustomerCovenantComponent;
  let fixture: ComponentFixture<EditCustomerCovenantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditCustomerCovenantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCustomerCovenantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
