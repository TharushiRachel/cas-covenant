import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDetailFacilityComponent } from './personal-detail-facility.component';

describe('PersonalDetailFacilityComponent', () => {
  let component: PersonalDetailFacilityComponent;
  let fixture: ComponentFixture<PersonalDetailFacilityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalDetailFacilityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDetailFacilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
