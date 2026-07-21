import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfBusinessInformationAddEditComponent } from './apf-business-information-add-edit.component';

describe('ApfBusinessInformationAddEditComponent', () => {
  let component: ApfBusinessInformationAddEditComponent;
  let fixture: ComponentFixture<ApfBusinessInformationAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfBusinessInformationAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBusinessInformationAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
