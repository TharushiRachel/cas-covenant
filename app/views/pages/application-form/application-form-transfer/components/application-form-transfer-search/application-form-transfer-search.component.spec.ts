import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationFormTransferSearchComponent } from './application-form-transfer-search.component';

describe('ApplicationFormTransferSearchComponent', () => {
  let component: ApplicationFormTransferSearchComponent;
  let fixture: ComponentFixture<ApplicationFormTransferSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormTransferSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormTransferSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
