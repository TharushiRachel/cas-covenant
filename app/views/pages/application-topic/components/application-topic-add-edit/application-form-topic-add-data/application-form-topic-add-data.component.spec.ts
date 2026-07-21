import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationFormTopicAddDataComponent } from './application-form-topic-add-data.component';

describe('ApplicationFormTopicAddDataComponent', () => {
  let component: ApplicationFormTopicAddDataComponent;
  let fixture: ComponentFixture<ApplicationFormTopicAddDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormTopicAddDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormTopicAddDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
