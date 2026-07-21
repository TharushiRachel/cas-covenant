import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonReleaseComponent } from './common-release.component';

describe('CommonReleaseComponent', () => {
  let component: CommonReleaseComponent;
  let fixture: ComponentFixture<CommonReleaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommonReleaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommonReleaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
