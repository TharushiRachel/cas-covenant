import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpEsgWrapperComponent } from './fp-esg-wrapper.component';

describe('FpEsgWrapperComponent', () => {
  let component: FpEsgWrapperComponent;
  let fixture: ComponentFixture<FpEsgWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpEsgWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpEsgWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
