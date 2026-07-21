import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentsViewComponent } from './agents-view.component';

describe('AgentsViewComponent', () => {
  let component: AgentsViewComponent;
  let fixture: ComponentFixture<AgentsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgentsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
