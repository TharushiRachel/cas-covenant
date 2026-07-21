import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovDepositsDetailsComponent } from './cov-deposits-details.component';

describe('CovDepositsDetailsComponent', () => {
  let component: CovDepositsDetailsComponent;
  let fixture: ComponentFixture<CovDepositsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovDepositsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovDepositsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
