import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationTopicConfigAddEditComponent } from './application-topic-config-add-edit.component';

describe('ApplicationTopicConfigAddEditComponent', () => {
  let component: ApplicationTopicConfigAddEditComponent;
  let fixture: ComponentFixture<ApplicationTopicConfigAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationTopicConfigAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationTopicConfigAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
