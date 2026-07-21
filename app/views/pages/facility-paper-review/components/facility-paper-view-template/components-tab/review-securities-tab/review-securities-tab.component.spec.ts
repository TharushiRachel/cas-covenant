import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewSecuritiesTabComponent } from './review-securities-tab.component';

describe('ReviewSecuritiesTabComponent', () => {
  let component: ReviewSecuritiesTabComponent;
  let fixture: ComponentFixture<ReviewSecuritiesTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewSecuritiesTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewSecuritiesTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
