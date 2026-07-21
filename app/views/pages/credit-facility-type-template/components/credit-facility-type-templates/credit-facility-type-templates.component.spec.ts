import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditFacilityTypeTemplatesComponent } from './credit-facility-type-templates.component';

describe('CreditFacilityTypeTemplatesComponent', () => {
  let component: CreditFacilityTypeTemplatesComponent;
  let fixture: ComponentFixture<CreditFacilityTypeTemplatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditFacilityTypeTemplatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditFacilityTypeTemplatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
