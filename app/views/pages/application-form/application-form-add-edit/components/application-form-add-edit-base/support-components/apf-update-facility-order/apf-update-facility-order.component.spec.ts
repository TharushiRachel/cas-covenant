import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfUpdateFacilityOrderComponent } from './apf-update-facility-order.component';

describe('ApfUpdateFacilityOrderComponent', () => {
  let component: ApfUpdateFacilityOrderComponent;
  let fixture: ComponentFixture<ApfUpdateFacilityOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfUpdateFacilityOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfUpdateFacilityOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
