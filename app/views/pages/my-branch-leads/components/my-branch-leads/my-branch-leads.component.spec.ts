import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyBranchLeadsComponent } from './my-branch-leads.component';

describe('MyBranchLeadsComponent', () => {
  let component: MyBranchLeadsComponent;
  let fixture: ComponentFixture<MyBranchLeadsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyBranchLeadsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyBranchLeadsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
