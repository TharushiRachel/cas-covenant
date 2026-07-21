import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CasPrintButtonComponent } from './cas-print-button.component';

describe('CasPrintButtonComponent', () => {
  let component: CasPrintButtonComponent;
  let fixture: ComponentFixture<CasPrintButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CasPrintButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasPrintButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
