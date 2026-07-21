import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ExternalLeadDeclineComponent} from './external-lead-decline.component';

describe('ExternalLeadDeclineComponent', () => {
  let component: ExternalLeadDeclineComponent;
  let fixture: ComponentFixture<ExternalLeadDeclineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ExternalLeadDeclineComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExternalLeadDeclineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
