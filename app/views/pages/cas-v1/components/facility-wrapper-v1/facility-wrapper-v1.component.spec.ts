import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityWrapperV1Component } from './facility-wrapper-v1.component';

describe('FacilityWrapperV1Component', () => {
  let component: FacilityWrapperV1Component;
  let fixture: ComponentFixture<FacilityWrapperV1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityWrapperV1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityWrapperV1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
