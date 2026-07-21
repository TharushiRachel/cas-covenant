import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewTemplateFpFullViewComponent } from './review-template-fp-full-view.component';

describe('ReviewTemplateFpFullViewComponent', () => {
  let component: ReviewTemplateFpFullViewComponent;
  let fixture: ComponentFixture<ReviewTemplateFpFullViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewTemplateFpFullViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewTemplateFpFullViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
