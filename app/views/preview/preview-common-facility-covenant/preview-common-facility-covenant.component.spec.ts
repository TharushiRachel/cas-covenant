import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewCommonFacilityCovenantComponent } from './preview-common-facility-covenant.component';

describe('PreviewCommonFacilityCovenantComponent', () => {
  let component: PreviewCommonFacilityCovenantComponent;
  let fixture: ComponentFixture<PreviewCommonFacilityCovenantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewCommonFacilityCovenantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewCommonFacilityCovenantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
