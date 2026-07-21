import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfLpsScreenComponent } from './apf-lps-screen.component';

describe('ApfLpsScreenComponent', () => {
  let component: ApfLpsScreenComponent;
  let fixture: ComponentFixture<ApfLpsScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfLpsScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfLpsScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
