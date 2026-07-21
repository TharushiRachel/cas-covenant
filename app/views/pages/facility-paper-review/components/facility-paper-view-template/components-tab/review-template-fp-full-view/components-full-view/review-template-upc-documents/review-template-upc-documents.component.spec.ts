import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewTemplateUpcDocumentsComponent } from './review-template-upc-documents.component';

describe('ReviewTemplateUpcDocumentsComponent', () => {
  let component: ReviewTemplateUpcDocumentsComponent;
  let fixture: ComponentFixture<ReviewTemplateUpcDocumentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewTemplateUpcDocumentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewTemplateUpcDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
