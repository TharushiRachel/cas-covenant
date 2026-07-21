import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmeAnswersComponent } from './sme-answers.component';

describe('SmeAnswersComponent', () => {
  let component: SmeAnswersComponent;
  let fixture: ComponentFixture<SmeAnswersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmeAnswersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmeAnswersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
