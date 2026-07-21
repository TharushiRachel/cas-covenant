import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovAccountStatisticsComponent } from './cov-account-statistics.component';

describe('CovAccountStatisticsComponent', () => {
  let component: CovAccountStatisticsComponent;
  let fixture: ComponentFixture<CovAccountStatisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovAccountStatisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovAccountStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
