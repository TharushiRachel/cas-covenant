import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperDateCountLabelComponent } from './facility-paper-date-count-label.component';

describe('FacilityPaperDateCountLabelComponent', () => {
  let component: FacilityPaperDateCountLabelComponent;
  let fixture: ComponentFixture<FacilityPaperDateCountLabelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperDateCountLabelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperDateCountLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
