import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditJoiningPartnersComponent } from './apf-add-edit-joining-partners.component';

describe('ApfAddEditJoiningPartnersComponent', () => {
  let component: ApfAddEditJoiningPartnersComponent;
  let fixture: ComponentFixture<ApfAddEditJoiningPartnersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditJoiningPartnersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditJoiningPartnersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
