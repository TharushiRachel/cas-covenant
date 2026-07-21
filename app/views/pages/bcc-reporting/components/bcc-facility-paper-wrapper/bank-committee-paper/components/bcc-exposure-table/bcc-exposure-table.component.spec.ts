import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccExposureTableComponent } from './bcc-exposure-table.component';

describe('BccExposureTableComponent', () => {
  let component: BccExposureTableComponent;
  let fixture: ComponentFixture<BccExposureTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccExposureTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccExposureTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
