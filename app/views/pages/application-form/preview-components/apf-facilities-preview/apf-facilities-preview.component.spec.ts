import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFacilitiesPreviewComponent } from './apf-facilities-preview.component';

describe('ApfFacilitiesPreviewComponent', () => {
  let component: ApfFacilitiesPreviewComponent;
  let fixture: ComponentFixture<ApfFacilitiesPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFacilitiesPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilitiesPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
