import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFacilityComponent } from './apf-facility.component';

describe('ApfFacilityComponent', () => {
  let component: ApfFacilityComponent;
  let fixture: ComponentFixture<ApfFacilityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFacilityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
