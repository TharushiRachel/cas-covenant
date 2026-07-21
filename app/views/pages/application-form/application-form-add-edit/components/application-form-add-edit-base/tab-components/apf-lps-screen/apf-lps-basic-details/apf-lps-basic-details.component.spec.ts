import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfLpsBasicDetailsComponent } from './apf-lps-basic-details.component';

describe('ApfLpsBasicDetailsComponent', () => {
  let component: ApfLpsBasicDetailsComponent;
  let fixture: ComponentFixture<ApfLpsBasicDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfLpsBasicDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfLpsBasicDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
