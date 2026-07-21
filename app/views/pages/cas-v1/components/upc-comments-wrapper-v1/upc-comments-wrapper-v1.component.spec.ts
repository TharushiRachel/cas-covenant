import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcCommentsWrapperV1Component } from './upc-comments-wrapper-v1.component';

describe('UpcCommentsWrapperV1Component', () => {
  let component: UpcCommentsWrapperV1Component;
  let fixture: ComponentFixture<UpcCommentsWrapperV1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcCommentsWrapperV1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcCommentsWrapperV1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
