import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCommentComponent } from './fp-comment.component';

describe('FpCommentComponent', () => {
  let component: FpCommentComponent;
  let fixture: ComponentFixture<FpCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
