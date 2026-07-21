import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfFacilitySecurityDataComponent} from './apf-facility-security-data.component';

describe('ApfFacilitySecurityDataComponent', () => {
  let component: ApfFacilitySecurityDataComponent;
  let fixture: ComponentFixture<ApfFacilitySecurityDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfFacilitySecurityDataComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilitySecurityDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
