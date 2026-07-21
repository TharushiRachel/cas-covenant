import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditFacilityTypeAddEditComponent } from './credit-facility-type-add-edit.component';

describe('CreditFacilityTypeAddEditComponent', () => {
  let component: CreditFacilityTypeAddEditComponent;
  let fixture: ComponentFixture<CreditFacilityTypeAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditFacilityTypeAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditFacilityTypeAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
