import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CommonForwardComponent} from './common-forward.component';

describe('CommonForwardComponent', () => {
  let component: CommonForwardComponent;
  let fixture: ComponentFixture<CommonForwardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CommonForwardComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommonForwardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
