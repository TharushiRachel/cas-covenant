import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditAnswerComponent } from './add-edit-answer.component';

describe('AddEditAnswerComponent', () => {
  let component: AddEditAnswerComponent;
  let fixture: ComponentFixture<AddEditAnswerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditAnswerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditAnswerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
