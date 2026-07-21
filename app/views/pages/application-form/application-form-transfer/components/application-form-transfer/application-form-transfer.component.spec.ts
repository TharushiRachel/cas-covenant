import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationFormTransferComponent } from './application-form-transfer.component';

describe('ApplicationFormTransferComponent', () => {
  let component: ApplicationFormTransferComponent;
  let fixture: ComponentFixture<ApplicationFormTransferComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormTransferComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormTransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
