import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitteePoolAddEditComponent } from './committee-pool-add-edit.component';

describe('CommitteePoolAddEditComponent', () => {
  let component: CommitteePoolAddEditComponent;
  let fixture: ComponentFixture<CommitteePoolAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommitteePoolAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommitteePoolAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
