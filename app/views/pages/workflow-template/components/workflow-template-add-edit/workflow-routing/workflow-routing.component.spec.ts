import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowRoutingComponent } from './workflow-routing.component';

describe('WorkflowRoutingComponent', () => {
  let component: WorkflowRoutingComponent;
  let fixture: ComponentFixture<WorkflowRoutingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkflowRoutingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkflowRoutingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
