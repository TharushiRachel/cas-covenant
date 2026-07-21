import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfCommentsScreenComponent } from './apf-comments-screen.component';

describe('ApfCommentsScreenComponent', () => {
  let component: ApfCommentsScreenComponent;
  let fixture: ComponentFixture<ApfCommentsScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfCommentsScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfCommentsScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
