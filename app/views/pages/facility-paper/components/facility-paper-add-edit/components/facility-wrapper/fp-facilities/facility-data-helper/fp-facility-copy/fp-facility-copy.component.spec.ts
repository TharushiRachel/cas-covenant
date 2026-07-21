import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpFacilityCopyComponent } from './fp-facility-copy.component';

describe('FpFacilityCopyComponent', () => {
  let component: FpFacilityCopyComponent;
  let fixture: ComponentFixture<FpFacilityCopyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpFacilityCopyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpFacilityCopyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
