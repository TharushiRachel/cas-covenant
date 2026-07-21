import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewCustomerCribDetailComponent } from './preview-customer-crib-detail.component';

describe('PreviewCustomerCribDetailComponent', () => {
  let component: PreviewCustomerCribDetailComponent;
  let fixture: ComponentFixture<PreviewCustomerCribDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewCustomerCribDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewCustomerCribDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
