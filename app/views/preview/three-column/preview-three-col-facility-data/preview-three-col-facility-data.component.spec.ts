import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewThreeColFacilityDataComponent } from './preview-three-col-facility-data.component';

describe('PreviewThreeColFacilityDataComponent', () => {
  let component: PreviewThreeColFacilityDataComponent;
  let fixture: ComponentFixture<PreviewThreeColFacilityDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewThreeColFacilityDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewThreeColFacilityDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
