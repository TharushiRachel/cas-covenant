import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApplicationFormCopyDialogComponent} from './application-form-copy-dialog.component';

describe('ApplicationFormCopyDialogComponent', () => {
  let component: ApplicationFormCopyDialogComponent;
  let fixture: ComponentFixture<ApplicationFormCopyDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormCopyDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormCopyDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
