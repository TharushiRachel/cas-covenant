import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovAdvancesDetailsComponent } from './cov-advances-details.component';

describe('CovAdvancesDetailsComponent', () => {
  let component: CovAdvancesDetailsComponent;
  let fixture: ComponentFixture<CovAdvancesDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovAdvancesDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovAdvancesDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
