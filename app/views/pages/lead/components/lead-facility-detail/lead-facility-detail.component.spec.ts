import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadFacilityDetailComponent } from './lead-facility-detail.component';

describe('LeadFacilityDetailComponent', () => {
  let component: LeadFacilityDetailComponent;
  let fixture: ComponentFixture<LeadFacilityDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadFacilityDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadFacilityDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
