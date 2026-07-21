import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfForwardApplicationFormComponent} from './apf-forward-application-form.component';

describe('ApfForwardApplicationFormComponent', () => {
  let component: ApfForwardApplicationFormComponent;
  let fixture: ComponentFixture<ApfForwardApplicationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfForwardApplicationFormComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfForwardApplicationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
