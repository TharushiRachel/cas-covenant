import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewRiskCommentsComponent } from './preview-risk-comments.component';

describe('PreviewRiskCommentsComponent', () => {
  let component: PreviewRiskCommentsComponent;
  let fixture: ComponentFixture<PreviewRiskCommentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewRiskCommentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewRiskCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
