import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CovReturnUserSelectionComponent } from './cov-return-user-selection.component';

describe('CovReturnUserSelectionComponent', () => {
  let component: CovReturnUserSelectionComponent;
  let fixture: ComponentFixture<CovReturnUserSelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CovReturnUserSelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CovReturnUserSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
