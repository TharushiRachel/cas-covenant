import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditFacilityTemplateAddEditComponent } from './credit-facility-template-add-edit.component';

describe('CreditFacilityTemplateAddEditComponent', () => {
  let component: CreditFacilityTemplateAddEditComponent;
  let fixture: ComponentFixture<CreditFacilityTemplateAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditFacilityTemplateAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditFacilityTemplateAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
