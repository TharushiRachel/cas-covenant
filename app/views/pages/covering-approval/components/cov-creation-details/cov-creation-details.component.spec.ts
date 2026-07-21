import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovCreationDetailsComponent } from './cov-creation-details.component';

describe('CovCreationDetailsComponent', () => {
  let component: CovCreationDetailsComponent;
  let fixture: ComponentFixture<CovCreationDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovCreationDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovCreationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
