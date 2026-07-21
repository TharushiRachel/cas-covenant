import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfPersonalInformationAddEditComponent } from './apf-personal-information-add-edit.component';

describe('ApfPersonalInformationAddEditComponent', () => {
  let component: ApfPersonalInformationAddEditComponent;
  let fixture: ComponentFixture<ApfPersonalInformationAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfPersonalInformationAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfPersonalInformationAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
