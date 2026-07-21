import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationFormAddEditBaseComponent } from './application-form-add-edit-base.component';

describe('ApplicationFormAddEditBaseComponent', () => {
  let component: ApplicationFormAddEditBaseComponent;
  let fixture: ComponentFixture<ApplicationFormAddEditBaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormAddEditBaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormAddEditBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
