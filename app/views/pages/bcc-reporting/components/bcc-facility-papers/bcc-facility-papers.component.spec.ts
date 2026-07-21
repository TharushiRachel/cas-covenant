import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccFacilityPapersComponent } from './bcc-facility-papers.component';

describe('BccFacilityPapersComponent', () => {
  let component: BccFacilityPapersComponent;
  let fixture: ComponentFixture<BccFacilityPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccFacilityPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccFacilityPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
