import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditWalletShareComponent } from './add-edit-wallet-share.component';

describe('AddEditWalletShareComponent', () => {
  let component: AddEditWalletShareComponent;
  let fixture: ComponentFixture<AddEditWalletShareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditWalletShareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditWalletShareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
