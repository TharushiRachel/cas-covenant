import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationFormCopyBaseComponent } from './application-form-copy-base.component';

describe('ApplicationFormCopyBaseComponent', () => {
  let component: ApplicationFormCopyBaseComponent;
  let fixture: ComponentFixture<ApplicationFormCopyBaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormCopyBaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormCopyBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
