import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovAccountsDetailsComponent } from './cov-accounts-details.component';

describe('CovAccountsDetailsComponent', () => {
  let component: CovAccountsDetailsComponent;
  let fixture: ComponentFixture<CovAccountsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovAccountsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovAccountsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
