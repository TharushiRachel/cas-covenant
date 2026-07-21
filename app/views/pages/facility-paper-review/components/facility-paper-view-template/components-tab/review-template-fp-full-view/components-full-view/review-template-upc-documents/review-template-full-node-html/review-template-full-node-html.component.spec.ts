import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewTemplateFullNodeHtmlComponent } from './review-template-full-node-html.component';

describe('ReviewTemplateFullNodeHtmlComponent', () => {
  let component: ReviewTemplateFullNodeHtmlComponent;
  let fixture: ComponentFixture<ReviewTemplateFullNodeHtmlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewTemplateFullNodeHtmlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewTemplateFullNodeHtmlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
