import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CftInterestRateComponent } from './cft-interest-rate.component';

describe('CftInterestRateComponent', () => {
  let component: CftInterestRateComponent;
  let fixture: ComponentFixture<CftInterestRateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CftInterestRateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CftInterestRateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
