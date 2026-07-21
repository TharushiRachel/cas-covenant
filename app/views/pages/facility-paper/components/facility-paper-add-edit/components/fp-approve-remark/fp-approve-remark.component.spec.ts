import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpApproveRemarkComponent } from './fp-approve-remark.component';

describe('FpApproveRemarkComponent', () => {
  let component: FpApproveRemarkComponent;
  let fixture: ComponentFixture<FpApproveRemarkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpApproveRemarkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpApproveRemarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
