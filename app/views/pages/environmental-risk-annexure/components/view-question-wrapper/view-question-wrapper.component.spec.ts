import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewQuestionWrapperComponent } from './view-question-wrapper.component';

describe('ViewQuestionWrapperComponent', () => {
  let component: ViewQuestionWrapperComponent;
  let fixture: ComponentFixture<ViewQuestionWrapperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewQuestionWrapperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewQuestionWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
