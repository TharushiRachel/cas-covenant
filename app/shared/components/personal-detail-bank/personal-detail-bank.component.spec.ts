import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDetailBankComponent } from './personal-detail-bank.component';

describe('PersonalDetailBankComponent', () => {
  let component: PersonalDetailBankComponent;
  let fixture: ComponentFixture<PersonalDetailBankComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalDetailBankComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDetailBankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
