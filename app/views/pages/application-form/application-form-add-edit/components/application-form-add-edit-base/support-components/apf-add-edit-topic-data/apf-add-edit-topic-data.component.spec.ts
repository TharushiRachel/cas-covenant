import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditTopicDataComponent } from './apf-add-edit-topic-data.component';

describe('ApfAddEditTopicDataComponent', () => {
  let component: ApfAddEditTopicDataComponent;
  let fixture: ComponentFixture<ApfAddEditTopicDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditTopicDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditTopicDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
