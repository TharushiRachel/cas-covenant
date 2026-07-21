import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskConfirmationDialogComponent } from './risk-confirmation-dialog.component';

describe('RiskConfirmationDialogComponent', () => {
  let component: RiskConfirmationDialogComponent;
  let fixture: ComponentFixture<RiskConfirmationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiskConfirmationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
