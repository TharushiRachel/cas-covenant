import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OtherFacilityInformationComponent } from './other-facility-information.component';

describe('OtherFacilityInformationComponent', () => {
  let component: OtherFacilityInformationComponent;
  let fixture: ComponentFixture<OtherFacilityInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OtherFacilityInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OtherFacilityInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
