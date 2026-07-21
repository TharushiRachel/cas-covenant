import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewSecuritySummaryComponent } from './preview-security-summary.component';

describe('PreviewSecuritySummaryComponent', () => {
  let component: PreviewSecuritySummaryComponent;
  let fixture: ComponentFixture<PreviewSecuritySummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewSecuritySummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewSecuritySummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
