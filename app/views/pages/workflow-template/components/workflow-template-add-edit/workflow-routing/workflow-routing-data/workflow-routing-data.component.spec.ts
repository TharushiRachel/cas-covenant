import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowRoutingDataComponent } from './workflow-routing-data.component';

describe('WorkflowRoutingDataComponent', () => {
  let component: WorkflowRoutingDataComponent;
  let fixture: ComponentFixture<WorkflowRoutingDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkflowRoutingDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkflowRoutingDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
