import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditOwnershipDetailsComponent } from './apf-add-edit-ownership-details.component';

describe('ApfAddEditOwnershipDetailsComponent', () => {
  let component: ApfAddEditOwnershipDetailsComponent;
  let fixture: ComponentFixture<ApfAddEditOwnershipDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditOwnershipDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditOwnershipDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
