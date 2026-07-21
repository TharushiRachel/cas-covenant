import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CaRequestApprovalComponent } from './ca-request-approval.component';

describe('CaRequestApprovalComponent', () => {
  let component: CaRequestApprovalComponent;
  let fixture: ComponentFixture<CaRequestApprovalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaRequestApprovalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaRequestApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
