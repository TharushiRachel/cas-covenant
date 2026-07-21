import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CountBoxComponent } from './lead-count-box.component';

describe('LeadCountBoxComponent', () => {
  let component: LeadCountBoxComponent;
  let fixture: ComponentFixture<LeadCountBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeadCountBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeadCountBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
