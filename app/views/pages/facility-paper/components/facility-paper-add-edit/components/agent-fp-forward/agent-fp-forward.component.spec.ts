import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentFpForwardComponent } from './agent-fp-forward.component';

describe('AgentFpForwardComponent', () => {
  let component: AgentFpForwardComponent;
  let fixture: ComponentFixture<AgentFpForwardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgentFpForwardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentFpForwardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
