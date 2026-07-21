import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitteePoolComponent } from './committee-pool.component';

describe('CommitteePoolComponent', () => {
  let component: CommitteePoolComponent;
  let fixture: ComponentFixture<CommitteePoolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommitteePoolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommitteePoolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
