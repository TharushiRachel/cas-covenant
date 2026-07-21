import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddComparableStatementComponent } from './add-comparable-statement.component';

describe('AddComparableStatementComponent', () => {
  let component: AddComparableStatementComponent;
  let fixture: ComponentFixture<AddComparableStatementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddComparableStatementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddComparableStatementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
