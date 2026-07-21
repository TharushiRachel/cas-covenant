import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerClarificationViewComponent } from './customer-clarification-view.component';

describe('CustomerClarificationViewComponent', () => {
  let component: CustomerClarificationViewComponent;
  let fixture: ComponentFixture<CustomerClarificationViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerClarificationViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerClarificationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
