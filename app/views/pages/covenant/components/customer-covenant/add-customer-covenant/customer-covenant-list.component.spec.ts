import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerCovenantListComponent } from './customer-covenant-list.component';

describe('CustomerCovenantListComponent', () => {
  let component: CustomerCovenantListComponent;
  let fixture: ComponentFixture<CustomerCovenantListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerCovenantListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerCovenantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
