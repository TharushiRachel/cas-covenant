import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityWrapperV1Component } from './security-wrapper-v1.component';

describe('SecurityWrapperV1Component', () => {
  let component: SecurityWrapperV1Component;
  let fixture: ComponentFixture<SecurityWrapperV1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecurityWrapperV1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecurityWrapperV1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
