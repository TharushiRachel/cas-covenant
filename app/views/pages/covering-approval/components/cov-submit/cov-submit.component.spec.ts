import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovSubmitComponent } from './cov-submit.component';

describe('CovSubmitComponent', () => {
  let component: CovSubmitComponent;
  let fixture: ComponentFixture<CovSubmitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovSubmitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovSubmitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
