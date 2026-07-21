import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCovenantAccountDetailsComponent } from './view-covenant-account-details.component';

describe('ViewCovenantAccountDetailsComponent', () => {
  let component: ViewCovenantAccountDetailsComponent;
  let fixture: ComponentFixture<ViewCovenantAccountDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewCovenantAccountDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewCovenantAccountDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
