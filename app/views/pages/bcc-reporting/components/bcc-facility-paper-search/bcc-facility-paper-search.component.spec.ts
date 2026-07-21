import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccFacilityPaperSearchComponent } from './bcc-facility-paper-search.component';

describe('BccFacilityPaperSearchComponent', () => {
  let component: BccFacilityPaperSearchComponent;
  let fixture: ComponentFixture<BccFacilityPaperSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccFacilityPaperSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccFacilityPaperSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
