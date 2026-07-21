import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FullPaperWrapperV1Component } from './full-paper-wrapper-v1.component';

describe('FullPaperWrapperV1Component', () => {
  let component: FullPaperWrapperV1Component;
  let fixture: ComponentFixture<FullPaperWrapperV1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FullPaperWrapperV1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FullPaperWrapperV1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
