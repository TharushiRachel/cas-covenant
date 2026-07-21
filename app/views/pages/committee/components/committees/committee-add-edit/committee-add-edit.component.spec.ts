import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitteeAddEditComponent } from './committee-add-edit.component';

describe('CommitteeAddEditComponent', () => {
  let component: CommitteeAddEditComponent;
  let fixture: ComponentFixture<CommitteeAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommitteeAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommitteeAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
