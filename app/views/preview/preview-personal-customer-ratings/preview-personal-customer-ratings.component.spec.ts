import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewPersonalCustomerRatingsComponent } from './preview-personal-customer-ratings.component';

describe('PreviewPersonalCustomerRatingsComponent', () => {
  let component: PreviewPersonalCustomerRatingsComponent;
  let fixture: ComponentFixture<PersonalCustomerRatingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewPersonalCustomerRatingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewPersonalCustomerRatingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
