import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpDeviationComponent } from './fp-deviation.component';

describe('FpDeviationComponent', () => {
  let component: FpDeviationComponent;
  let fixture: ComponentFixture<FpDeviationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpDeviationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpDeviationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
