import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationFormSearchComponent } from './application-form-search.component';

describe('ApplicationFormSearchComponent', () => {
  let component: ApplicationFormSearchComponent;
  let fixture: ComponentFixture<ApplicationFormSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationFormSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationFormSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
