import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinacleFacilityDetailsComponent } from './finacle-facility-details.component';

describe('FinacleFacilityDetailsComponent', () => {
  let component: FinacleFacilityDetailsComponent;
  let fixture: ComponentFixture<FinacleFacilityDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinacleFacilityDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinacleFacilityDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
