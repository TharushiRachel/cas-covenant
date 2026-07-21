import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadComprehensiveDocumentComponent } from './lead-comprehensive-document.component';

describe('AddCompLeadDocumentComponent', () => {
  let component: LeadComprehensiveDocumentComponent;
  let fixture: ComponentFixture<LeadComprehensiveDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadComprehensiveDocumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadComprehensiveDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
