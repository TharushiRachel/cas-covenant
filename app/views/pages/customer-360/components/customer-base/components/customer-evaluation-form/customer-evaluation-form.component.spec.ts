import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerEvaluationFormComponent } from './customer-evaluation-form.component';

describe('CustomerEvaluationFormComponent', () => {
  let component: CustomerEvaluationFormComponent;
  let fixture: ComponentFixture<CustomerEvaluationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerEvaluationFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerEvaluationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
