import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CftVitalQuestionComponent} from './cft-vital-question.component';

describe('CftVitalQuestionComponent', () => {
  let component: CftVitalQuestionComponent;
  let fixture: ComponentFixture<CftVitalQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CftVitalQuestionComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CftVitalQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
