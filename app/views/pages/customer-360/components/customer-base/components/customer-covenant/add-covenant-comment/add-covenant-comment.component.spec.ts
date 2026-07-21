import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCovenantCommentComponent } from './add-covenant-comment.component';

describe('AddCovenantCommentComponent', () => {
  let component: AddCovenantCommentComponent;
  let fixture: ComponentFixture<AddCovenantCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCovenantCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCovenantCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
