import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccExistingFacilitiesComponent } from './bcc-existing-facilities.component';

describe('BccExistingFacilitiesComponent', () => {
  let component: BccExistingFacilitiesComponent;
  let fixture: ComponentFixture<BccExistingFacilitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccExistingFacilitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccExistingFacilitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
