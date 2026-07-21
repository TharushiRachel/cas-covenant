import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TokenTimeOutNotificationComponent} from './token-time-out-notification.component';

describe('TokenTimeOutNotificationComponent', () => {
  let component: TokenTimeOutNotificationComponent;
  let fixture: ComponentFixture<TokenTimeOutNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TokenTimeOutNotificationComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TokenTimeOutNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
