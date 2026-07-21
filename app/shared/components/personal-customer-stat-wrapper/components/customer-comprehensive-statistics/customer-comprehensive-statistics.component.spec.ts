import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerComprehensiveStatisticsComponent } from './customer-comprehensive-statistics.component';

describe('CustomerComprehensiveStatisticsComponent', () => {
  let component: CustomerComprehensiveStatisticsComponent;
  let fixture: ComponentFixture<CustomerComprehensiveStatisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerComprehensiveStatisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerComprehensiveStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
