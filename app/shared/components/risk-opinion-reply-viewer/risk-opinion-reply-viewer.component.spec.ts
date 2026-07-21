import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskOpinionReplyViewerComponent } from './risk-opinion-reply-viewer.component';

describe('RiskOpinionReplyViewerComponent', () => {
  let component: RiskOpinionReplyViewerComponent;
  let fixture: ComponentFixture<RiskOpinionReplyViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiskOpinionReplyViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskOpinionReplyViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
