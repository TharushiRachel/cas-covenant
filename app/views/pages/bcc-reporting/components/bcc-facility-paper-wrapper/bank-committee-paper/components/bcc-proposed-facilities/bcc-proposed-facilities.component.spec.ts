import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccProposedFacilitiesComponent } from './bcc-proposed-facilities.component';

describe('BccProposedFacilitiesComponent', () => {
  let component: BccProposedFacilitiesComponent;
  let fixture: ComponentFixture<BccProposedFacilitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccProposedFacilitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccProposedFacilitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
