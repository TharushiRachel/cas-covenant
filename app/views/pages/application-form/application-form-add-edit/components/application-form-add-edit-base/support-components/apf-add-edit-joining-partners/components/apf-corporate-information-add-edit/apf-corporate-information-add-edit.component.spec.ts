import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfCorporateInformationAddEditComponent } from './apf-corporate-information-add-edit.component';

describe('ApfCorporateInformationAddEditComponent', () => {
  let component: ApfCorporateInformationAddEditComponent;
  let fixture: ComponentFixture<ApfCorporateInformationAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfCorporateInformationAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfCorporateInformationAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
