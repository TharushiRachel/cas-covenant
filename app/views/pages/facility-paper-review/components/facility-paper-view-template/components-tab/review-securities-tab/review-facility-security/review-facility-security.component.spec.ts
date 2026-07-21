import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewFacilitySecurityComponent } from './review-facility-security.component';

describe('ReviewFacilitySecurityComponent', () => {
  let component: ReviewFacilitySecurityComponent;
  let fixture: ComponentFixture<ReviewFacilitySecurityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewFacilitySecurityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewFacilitySecurityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
