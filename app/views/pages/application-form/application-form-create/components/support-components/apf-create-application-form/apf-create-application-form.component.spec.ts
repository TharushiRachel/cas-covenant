import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfCreateApplicationFormComponent } from './apf-create-application-form.component';

describe('ApfCreateApplicationFormComponent', () => {
  let component: ApfCreateApplicationFormComponent;
  let fixture: ComponentFixture<ApfCreateApplicationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfCreateApplicationFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfCreateApplicationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
