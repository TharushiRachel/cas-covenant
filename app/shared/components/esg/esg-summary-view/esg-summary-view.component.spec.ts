import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EsgSummaryViewComponent } from './esg-summary-view.component';

describe('EsgSummaryViewComponent', () => {
  let component: EsgSummaryViewComponent;
  let fixture: ComponentFixture<EsgSummaryViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EsgSummaryViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EsgSummaryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
