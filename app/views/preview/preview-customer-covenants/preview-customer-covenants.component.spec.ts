import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewCustomerCovenantsComponent } from './preview-customer-covenants.component';

describe('PreviewCustomerCovenantsComponent', () => {
  let component: PreviewCustomerCovenantsComponent;
  let fixture: ComponentFixture<PreviewCustomerCovenantsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewCustomerCovenantsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewCustomerCovenantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
