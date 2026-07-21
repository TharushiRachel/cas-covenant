import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddTopicsComponent } from './apf-add-topics.component';

describe('ApfAddTopicsComponent', () => {
  let component: ApfAddTopicsComponent;
  let fixture: ComponentFixture<ApfAddTopicsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddTopicsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddTopicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
