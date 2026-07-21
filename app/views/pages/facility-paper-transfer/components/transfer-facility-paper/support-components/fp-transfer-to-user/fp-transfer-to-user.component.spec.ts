import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpTransferToUserComponent } from './fp-transfer-to-user.component';

describe('FpTransferToUserComponent', () => {
  let component: FpTransferToUserComponent;
  let fixture: ComponentFixture<FpTransferToUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpTransferToUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpTransferToUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
