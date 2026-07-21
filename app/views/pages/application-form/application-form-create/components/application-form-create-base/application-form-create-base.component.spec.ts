import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationFormCreateBaseComponent } from './application-form-create-base.component';

describe('ApplicationFormCreateBaseComponent', () => {
  let component: ApplicationFormCreateBaseComponent;
  let fixture: ComponentFixture<ApplicationFormCreateBaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormCreateBaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormCreateBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
