import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfTopicViewComponent } from './apf-topic-view.component';

describe('ApfTopicViewComponent', () => {
  let component: ApfTopicViewComponent;
  let fixture: ComponentFixture<ApfTopicViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfTopicViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfTopicViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
