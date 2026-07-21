import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCoveringApprovalComponent } from './create-covering-approval.component';

describe('CreateCoveringApprovalComponent', () => {
  let component: CreateCoveringApprovalComponent;
  let fixture: ComponentFixture<CreateCoveringApprovalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateCoveringApprovalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateCoveringApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
