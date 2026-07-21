import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {BccFacilityCommonSecurityComponent} from './bcc-facility-common-security.component';

describe('BccFacilityCommonSecurityComponent', () => {
  let component: BccFacilityCommonSecurityComponent;
  let fixture: ComponentFixture<BccFacilityCommonSecurityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BccFacilityCommonSecurityComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccFacilityCommonSecurityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
