import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerCovenantComponent } from './customer-covenant.component';

describe('CustomerCovenantComponent', () => {
  let component: CustomerCovenantComponent;
  let fixture: ComponentFixture<CustomerCovenantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerCovenantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerCovenantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
