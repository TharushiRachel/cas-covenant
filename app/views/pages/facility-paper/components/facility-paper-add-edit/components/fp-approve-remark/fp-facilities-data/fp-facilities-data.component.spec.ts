import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpFacilitiesDataComponent } from './fp-facilities-data.component';

describe('FpFacilitiesDataComponent', () => {
  let component: FpFacilitiesDataComponent;
  let fixture: ComponentFixture<FpFacilitiesDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpFacilitiesDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpFacilitiesDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
