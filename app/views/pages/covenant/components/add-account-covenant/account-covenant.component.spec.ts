import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountCovenantComponent } from './account-covenant.component';

describe('AccountCovenantComponent', () => {
  let component: AccountCovenantComponent;
  let fixture: ComponentFixture<AccountCovenantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountCovenantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountCovenantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
