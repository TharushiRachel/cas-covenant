import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpGroupExposureComponent } from './fp-group-exposure.component';

describe('FpGroupExposureComponent', () => {
  let component: FpGroupExposureComponent;
  let fixture: ComponentFixture<FpGroupExposureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpGroupExposureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpGroupExposureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
