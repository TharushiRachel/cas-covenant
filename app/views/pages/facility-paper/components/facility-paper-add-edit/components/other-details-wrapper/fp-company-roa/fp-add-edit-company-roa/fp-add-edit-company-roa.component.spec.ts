import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpAddEditCompanyRoaComponent } from './fp-add-edit-company-roa.component';

describe('FpAddEditCompanyRoaComponent', () => {
  let component: FpAddEditCompanyRoaComponent;
  let fixture: ComponentFixture<FpAddEditCompanyRoaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpAddEditCompanyRoaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpAddEditCompanyRoaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
