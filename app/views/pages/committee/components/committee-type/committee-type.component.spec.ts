import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitteeTypeComponent } from './committee-type.component';

describe('CommitteeTypeComponent', () => {
  let component: CommitteeTypeComponent;
  let fixture: ComponentFixture<CommitteeTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommitteeTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommitteeTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
