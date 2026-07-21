import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditFacilityDocumentComponent } from './apf-add-edit-facility-document.component';

describe('ApfAddEditFacilityDocumentComponent', () => {
  let component: ApfAddEditFacilityDocumentComponent;
  let fixture: ComponentFixture<ApfAddEditFacilityDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditFacilityDocumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditFacilityDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
