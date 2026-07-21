import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfNextPrevButtonComponent } from './apf-next-prev-button.component';

describe('ApfNextPrevButtonComponent', () => {
  let component: ApfNextPrevButtonComponent;
  let fixture: ComponentFixture<ApfNextPrevButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfNextPrevButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfNextPrevButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
