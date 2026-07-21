import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadDocumentComponent } from './lead-document.component';

describe('LeadDocumentComponent', () => {
  let component: LeadDocumentComponent;
  let fixture: ComponentFixture<LeadDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadDocumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
