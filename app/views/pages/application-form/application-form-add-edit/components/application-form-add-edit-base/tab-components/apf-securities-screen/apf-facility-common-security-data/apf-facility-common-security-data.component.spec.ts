import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfFacilityCommonSecurityDataComponent} from './apf-facility-common-security-data.component';

describe('ApfFacilityCommonSecurityDataComponent', () => {
  let component: ApfFacilityCommonSecurityDataComponent;
  let fixture: ComponentFixture<ApfFacilityCommonSecurityDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfFacilityCommonSecurityDataComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilityCommonSecurityDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
