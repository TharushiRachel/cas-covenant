import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfApplicationFormsComponent } from './apf-application-forms.component';

describe('ApfApplicationFormsComponent', () => {
  let component: ApfApplicationFormsComponent;
  let fixture: ComponentFixture<ApfApplicationFormsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfApplicationFormsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfApplicationFormsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
