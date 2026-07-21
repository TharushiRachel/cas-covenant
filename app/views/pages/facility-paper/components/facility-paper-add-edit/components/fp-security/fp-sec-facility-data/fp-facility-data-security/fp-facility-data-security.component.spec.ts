import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpFacilityDataSecurityComponent } from './fp-facility-data-security.component';

describe('FpFacilityDataSecurityComponent', () => {
  let component: FpFacilityDataSecurityComponent;
  let fixture: ComponentFixture<FpFacilityDataSecurityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpFacilityDataSecurityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpFacilityDataSecurityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
