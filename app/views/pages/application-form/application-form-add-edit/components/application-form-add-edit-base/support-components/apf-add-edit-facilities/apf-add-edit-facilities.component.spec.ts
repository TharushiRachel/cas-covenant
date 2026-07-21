import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditFacilitiesComponent } from './apf-add-edit-facilities.component';

describe('ApfAddEditFacilitiesComponent', () => {
  let component: ApfAddEditFacilitiesComponent;
  let fixture: ComponentFixture<ApfAddEditFacilitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditFacilitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditFacilitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
