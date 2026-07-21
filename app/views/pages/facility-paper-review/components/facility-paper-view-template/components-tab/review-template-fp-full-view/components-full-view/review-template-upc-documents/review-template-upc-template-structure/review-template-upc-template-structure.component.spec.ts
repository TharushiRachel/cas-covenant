import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewTemplateUpcTemplateStructureComponent } from './review-template-upc-template-structure.component';

describe('ReviewTemplateUpcTemplateStructureComponent', () => {
  let component: ReviewTemplateUpcTemplateStructureComponent;
  let fixture: ComponentFixture<ReviewTemplateUpcTemplateStructureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewTemplateUpcTemplateStructureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewTemplateUpcTemplateStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
