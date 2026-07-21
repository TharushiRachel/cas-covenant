import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpTotalExposureDataComponent } from './fp-total-exposure-data.component';

describe('FpTotalExposureDataComponent', () => {
  let component: FpTotalExposureDataComponent;
  let fixture: ComponentFixture<FpTotalExposureDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpTotalExposureDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpTotalExposureDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
