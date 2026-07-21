import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpWalletShareComponent } from './fp-wallet-share.component';

describe('FpWalletShareComponent', () => {
  let component: FpWalletShareComponent;
  let fixture: ComponentFixture<FpWalletShareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpWalletShareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpWalletShareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
