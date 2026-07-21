import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CaDetailsCommentComponent } from './ca-details-comment.component';

describe('CaDetailsCommentComponent', () => {
  let component: CaDetailsCommentComponent;
  let fixture: ComponentFixture<CaDetailsCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaDetailsCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaDetailsCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
