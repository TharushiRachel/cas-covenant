import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFacilitiesCommonSecuritiesComponent } from './apf-facilities-common-securities.component';

describe('ApfFacilitiesCommonSecuritiesComponent', () => {
  let component: ApfFacilitiesCommonSecuritiesComponent;
  let fixture: ComponentFixture<ApfFacilitiesCommonSecuritiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFacilitiesCommonSecuritiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilitiesCommonSecuritiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
