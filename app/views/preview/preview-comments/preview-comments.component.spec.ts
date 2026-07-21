import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewCommentsComponent } from './preview-comments.component';

describe('PreviewCommentsComponent', () => {
  let component: PreviewCommentsComponent;
  let fixture: ComponentFixture<PreviewCommentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewCommentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
