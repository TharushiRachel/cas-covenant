import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewScorecardComponent } from './preview-scorecard.component';

describe('PreviewScorecardComponent', () => {
  let component: PreviewScorecardComponent;
  let fixture: ComponentFixture<PreviewScorecardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewScorecardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewScorecardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
