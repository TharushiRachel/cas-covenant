import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { V1DashboardComponent } from './v1-dashboard.component';

describe('V1DashboardComponent', () => {
  let component: V1DashboardComponent;
  let fixture: ComponentFixture<V1DashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ V1DashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(V1DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
