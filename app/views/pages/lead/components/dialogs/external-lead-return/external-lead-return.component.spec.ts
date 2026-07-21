import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExternalLeadReturnComponent } from './external-lead-return.component';

describe('ExternalLeadReturnComponent', () => {
  let component: ExternalLeadReturnComponent;
  let fixture: ComponentFixture<ExternalLeadReturnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExternalLeadReturnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExternalLeadReturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
