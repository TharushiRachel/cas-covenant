import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditFacilityTypesComponent } from './credit-facility-types.component';

describe('CreditFacilityTypesComponent', () => {
  let component: CreditFacilityTypesComponent;
  let fixture: ComponentFixture<CreditFacilityTypesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditFacilityTypesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditFacilityTypesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
