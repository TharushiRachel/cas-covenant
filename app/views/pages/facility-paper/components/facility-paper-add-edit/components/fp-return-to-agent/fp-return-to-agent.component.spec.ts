import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpReturnToAgentComponent } from './fp-return-to-agent.component';

describe('FpReturnToAgentComponent', () => {
  let component: FpReturnToAgentComponent;
  let fixture: ComponentFixture<FpReturnToAgentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpReturnToAgentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpReturnToAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
