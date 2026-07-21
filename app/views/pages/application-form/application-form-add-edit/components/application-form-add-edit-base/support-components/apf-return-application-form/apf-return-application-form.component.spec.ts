import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfReturnApplicationFormComponent} from './apf-return-application-form.component';

describe('ApfReturnApplicationFormComponent', () => {
  let component: ApfReturnApplicationFormComponent;
  let fixture: ComponentFixture<ApfReturnApplicationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfReturnApplicationFormComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfReturnApplicationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
