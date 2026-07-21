import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateOutstandingDateComponent } from './update-outstanding-date.component';

describe('UpdateOutstandingDateComponent', () => {
  let component: UpdateOutstandingDateComponent;
  let fixture: ComponentFixture<UpdateOutstandingDateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateOutstandingDateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateOutstandingDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
