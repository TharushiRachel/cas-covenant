import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionLogViewComponent } from './action-log-view.component';

describe('ActionLogViewComponent', () => {
  let component: ActionLogViewComponent;
  let fixture: ComponentFixture<ActionLogViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActionLogViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActionLogViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
