import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CorporateInformationFormComponent} from './corporate-information-form.component';

describe('CorporateInformationFormComponent', () => {
  let component: CorporateInformationFormComponent;
  let fixture: ComponentFixture<CorporateInformationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CorporateInformationFormComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CorporateInformationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
