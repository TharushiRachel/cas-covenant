import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationTopicAddEditComponent } from './application-topic-add-edit.component';

describe('ApplicationTopicAddEditComponent', () => {
  let component: ApplicationTopicAddEditComponent;
  let fixture: ComponentFixture<ApplicationTopicAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationTopicAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationTopicAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
