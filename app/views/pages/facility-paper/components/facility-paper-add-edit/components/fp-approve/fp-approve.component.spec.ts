import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpApproveComponent } from './fp-approve.component';

describe('FpApproveComponent', () => {
  let component: FpApproveComponent;
  let fixture: ComponentFixture<FpApproveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpApproveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpApproveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
