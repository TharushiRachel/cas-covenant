import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpReturnComponent } from './fp-return.component';

describe('FpReturnComponent', () => {
  let component: FpReturnComponent;
  let fixture: ComponentFixture<FpReturnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpReturnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpReturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
