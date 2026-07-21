import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowTemplateAddEditComponent } from './workflow-template-add-edit.component';

describe('WorkflowTemplateAddEditComponent', () => {
  let component: WorkflowTemplateAddEditComponent;
  let fixture: ComponentFixture<WorkflowTemplateAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkflowTemplateAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkflowTemplateAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
