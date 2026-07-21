import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDetailObFacilityComponent } from './personal-detail-ob-facility.component';

describe('PersonalDetailObFacilityComponent', () => {
  let component: PersonalDetailObFacilityComponent;
  let fixture: ComponentFixture<PersonalDetailObFacilityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalDetailObFacilityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDetailObFacilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
