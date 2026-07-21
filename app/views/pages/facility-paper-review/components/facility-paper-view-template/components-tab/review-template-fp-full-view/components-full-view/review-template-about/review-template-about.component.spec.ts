import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewTemplateAboutComponent } from './review-template-about.component';

describe('ReviewTemplateAboutComponent', () => {
  let component: ReviewTemplateAboutComponent;
  let fixture: ComponentFixture<ReviewTemplateAboutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewTemplateAboutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewTemplateAboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
