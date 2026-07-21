import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDelegatedAuthorityAddEditComponent } from './user-delegated-authority-add-edit.component';

describe('UserDelegatedAuthorityAddEditComponent', () => {
  let component: UserDelegatedAuthorityAddEditComponent;
  let fixture: ComponentFixture<UserDelegatedAuthorityAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserDelegatedAuthorityAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDelegatedAuthorityAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
