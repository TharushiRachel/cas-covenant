import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BranchApplicationFormComponent } from './branch-application-form.component';

describe('BranchApplicationFormComponent', () => {
  let component: BranchApplicationFormComponent;
  let fixture: ComponentFixture<BranchApplicationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BranchApplicationFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BranchApplicationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
