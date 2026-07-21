import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDelegatedAuthoritiesComponent } from './user-delegated-authorities.component';

describe('UserDelegatedAuthoritiesComponent', () => {
  let component: UserDelegatedAuthoritiesComponent;
  let fixture: ComponentFixture<UserDelegatedAuthoritiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserDelegatedAuthoritiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDelegatedAuthoritiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
