import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonAttendComponent } from './common-attend.component';

describe('CommonAttendComponent', () => {
  let component: CommonAttendComponent;
  let fixture: ComponentFixture<CommonAttendComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommonAttendComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommonAttendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
