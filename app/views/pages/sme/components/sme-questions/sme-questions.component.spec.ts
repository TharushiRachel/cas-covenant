import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmeQuestionsComponent } from './sme-questions.component';

describe('SmeQuestionsComponent', () => {
  let component: SmeQuestionsComponent;
  let fixture: ComponentFixture<SmeQuestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmeQuestionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmeQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
