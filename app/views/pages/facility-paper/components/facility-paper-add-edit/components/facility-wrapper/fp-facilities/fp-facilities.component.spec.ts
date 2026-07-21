import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpFacilitiesComponent } from './fp-facilities.component';

describe('FpFacilitiesComponent', () => {
  let component: FpFacilitiesComponent;
  let fixture: ComponentFixture<FpFacilitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpFacilitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpFacilitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
