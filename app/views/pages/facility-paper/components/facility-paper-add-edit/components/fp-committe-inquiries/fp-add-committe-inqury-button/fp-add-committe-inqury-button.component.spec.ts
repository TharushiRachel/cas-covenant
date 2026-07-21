import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpAddCommitteInquryButtonComponent } from './fp-add-committe-inqury-button.component';

describe('FpAddCommitteInquryButtonComponent', () => {
  let component: FpAddCommitteInquryButtonComponent;
  let fixture: ComponentFixture<FpAddCommitteInquryButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpAddCommitteInquryButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpAddCommitteInquryButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
