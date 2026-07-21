import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadHistoryListComponent } from './lead-history-list.component';

describe('LeadHistoryListComponent', () => {
  let component: LeadHistoryListComponent;
  let fixture: ComponentFixture<LeadHistoryListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadHistoryListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadHistoryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
