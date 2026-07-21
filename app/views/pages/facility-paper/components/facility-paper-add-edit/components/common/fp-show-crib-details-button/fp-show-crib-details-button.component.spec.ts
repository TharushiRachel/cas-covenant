import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpShowCribDetailsButtonComponent } from './fp-show-crib-details-button.component';

describe('FpShowCribDetailsButtonComponent', () => {
  let component: FpShowCribDetailsButtonComponent;
  let fixture: ComponentFixture<FpShowCribDetailsButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpShowCribDetailsButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpShowCribDetailsButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
