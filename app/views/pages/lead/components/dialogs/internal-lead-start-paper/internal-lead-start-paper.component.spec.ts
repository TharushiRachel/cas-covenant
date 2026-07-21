import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InternalLeadStartPaperComponent } from './internal-lead-start-paper.component';

describe('InternalLeadStartPaperComponent', () => {
  let component: InternalLeadStartPaperComponent;
  let fixture: ComponentFixture<InternalLeadStartPaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InternalLeadStartPaperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InternalLeadStartPaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
