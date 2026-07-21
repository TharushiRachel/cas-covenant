import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerGuaranteeVolumesComponent } from './customer-guarantee-volumes.component';

describe('CustomerGuaranteeVolumesComponent', () => {
  let component: CustomerGuaranteeVolumesComponent;
  let fixture: ComponentFixture<CustomerGuaranteeVolumesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerGuaranteeVolumesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerGuaranteeVolumesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
