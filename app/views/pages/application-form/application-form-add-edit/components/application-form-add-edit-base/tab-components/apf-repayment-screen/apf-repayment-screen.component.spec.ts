import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfRepaymentScreenComponent } from './apf-repayment-screen.component';

describe('ApfRepaymentScreenComponent', () => {
  let component: ApfRepaymentScreenComponent;
  let fixture: ComponentFixture<ApfRepaymentScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfRepaymentScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfRepaymentScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
