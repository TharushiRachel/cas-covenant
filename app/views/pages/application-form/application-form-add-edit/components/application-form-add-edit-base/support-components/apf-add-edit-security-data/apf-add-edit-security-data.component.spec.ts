import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfAddEditSecurityDataComponent} from './apf-add-edit-security-data.component';

describe('ApfAddEditSecurityDataComponent', () => {
  let component: ApfAddEditSecurityDataComponent;
  let fixture: ComponentFixture<ApfAddEditSecurityDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfAddEditSecurityDataComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditSecurityDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
