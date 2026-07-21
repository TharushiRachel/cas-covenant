import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomFacilityInformationComponent } from './custom-facility-information.component';

describe('CustomFacilityInformationComponent', () => {
  let component: CustomFacilityInformationComponent;
  let fixture: ComponentFixture<CustomFacilityInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomFacilityInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomFacilityInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
