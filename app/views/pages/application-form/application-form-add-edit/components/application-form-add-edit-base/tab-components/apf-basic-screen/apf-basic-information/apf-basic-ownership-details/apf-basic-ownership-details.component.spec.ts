import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfBasicOwnershipDetailsComponent } from './apf-basic-ownership-details.component';

describe('ApfBasicOwnershipDetailsComponent', () => {
  let component: ApfBasicOwnershipDetailsComponent;
  let fixture: ComponentFixture<ApfBasicOwnershipDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfBasicOwnershipDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBasicOwnershipDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
