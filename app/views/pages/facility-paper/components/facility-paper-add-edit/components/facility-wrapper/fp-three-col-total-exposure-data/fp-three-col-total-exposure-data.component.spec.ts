import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpThreeColTotalExposureDataComponent } from './fp-three-col-total-exposure-data.component';

describe('FpThreeColTotalExposureDataComponent', () => {
  let component: FpThreeColTotalExposureDataComponent;
  let fixture: ComponentFixture<FpThreeColTotalExposureDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpThreeColTotalExposureDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpThreeColTotalExposureDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
