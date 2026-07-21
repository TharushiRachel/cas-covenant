import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfStatusHistoryPreviewComponent } from './apf-status-history-preview.component';

describe('ApfStatusHistoryPreviewComponent', () => {
  let component: ApfStatusHistoryPreviewComponent;
  let fixture: ComponentFixture<ApfStatusHistoryPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfStatusHistoryPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfStatusHistoryPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
