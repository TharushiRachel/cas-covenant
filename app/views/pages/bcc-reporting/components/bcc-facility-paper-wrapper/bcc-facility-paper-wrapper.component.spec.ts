import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccFacilityPaperWrapperComponent } from './bcc-facility-paper-wrapper.component';

describe('BccFacilityPaperWrapperComponent', () => {
  let component: BccFacilityPaperWrapperComponent;
  let fixture: ComponentFixture<BccFacilityPaperWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccFacilityPaperWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccFacilityPaperWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
