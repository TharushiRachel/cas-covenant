import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExternalLeadCloseComponent } from './external-lead-close.component';

describe('ExternalLeadCloseComponent', () => {
  let component: ExternalLeadCloseComponent;
  let fixture: ComponentFixture<ExternalLeadCloseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExternalLeadCloseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExternalLeadCloseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
