import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewFacilityDataComponent } from './preview-facility-data.component';

describe('PreviewFacilityDataComponent', () => {
  let component: PreviewFacilityDataComponent;
  let fixture: ComponentFixture<PreviewFacilityDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewFacilityDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewFacilityDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
