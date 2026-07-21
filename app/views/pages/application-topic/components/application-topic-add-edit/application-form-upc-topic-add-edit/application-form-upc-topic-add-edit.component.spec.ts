import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationFormUpcTopicAddEditComponent } from './application-form-upc-topic-add-edit.component';

describe('ApplicationFormUpcTopicAddEditComponent', () => {
  let component: ApplicationFormUpcTopicAddEditComponent;
  let fixture: ComponentFixture<ApplicationFormUpcTopicAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormUpcTopicAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormUpcTopicAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
