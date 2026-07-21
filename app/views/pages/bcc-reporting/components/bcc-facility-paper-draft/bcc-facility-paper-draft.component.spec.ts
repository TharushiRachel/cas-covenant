import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccFacilityPaperDraftComponent } from './bcc-facility-paper-draft.component';

describe('BccFacilityPaperDraftComponent', () => {
  let component: BccFacilityPaperDraftComponent;
  let fixture: ComponentFixture<BccFacilityPaperDraftComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccFacilityPaperDraftComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccFacilityPaperDraftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
