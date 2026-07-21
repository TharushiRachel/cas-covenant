import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpCompanyRoaComponent } from './fp-company-roa.component';

describe('FpCompanyRoaComponent', () => {
  let component: FpCompanyRoaComponent;
  let fixture: ComponentFixture<FpCompanyRoaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpCompanyRoaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpCompanyRoaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
