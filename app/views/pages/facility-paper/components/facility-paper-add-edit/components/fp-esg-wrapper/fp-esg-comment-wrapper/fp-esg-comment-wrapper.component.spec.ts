import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpEsgCommentWrapperComponent } from './fp-esg-comment-wrapper.component';

describe('FpEsgCommentWrapperComponent', () => {
  let component: FpEsgCommentWrapperComponent;
  let fixture: ComponentFixture<FpEsgCommentWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpEsgCommentWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpEsgCommentWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
