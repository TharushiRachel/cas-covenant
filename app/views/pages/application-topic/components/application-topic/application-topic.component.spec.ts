import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationTopicComponent } from './application-topic.component';

describe('ApplicationTopicComponent', () => {
  let component: ApplicationTopicComponent;
  let fixture: ComponentFixture<ApplicationTopicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationTopicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationTopicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
