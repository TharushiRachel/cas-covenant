import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFacilityDocumentComponent } from './apf-facility-document.component';

describe('ApfFacilityDocumentComponent', () => {
  let component: ApfFacilityDocumentComponent;
  let fixture: ComponentFixture<ApfFacilityDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFacilityDocumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilityDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
