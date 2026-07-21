import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UPCTemplateCommentSectionComponent } from './upc-template-comment-section.component';

describe('UpcTemplateHistoryCommentComponent', () => {
  let component: UPCTemplateCommentSectionComponent;
  let fixture: ComponentFixture<UPCTemplateCommentSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UPCTemplateCommentSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UPCTemplateCommentSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
