import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperDateCountLabelComponent } from './facility-paper-upc-label.component';

describe('FacilityPaperUPCLabelComponent', () => {
  let component: FacilityPaperUPCLabelComponent;
  let fixture: ComponentFixture<FacilityPaperUPCLabelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperUPCLabelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperUPCLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
