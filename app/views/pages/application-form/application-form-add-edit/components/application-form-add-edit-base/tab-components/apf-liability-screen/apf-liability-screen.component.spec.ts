import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfLiabilityScreenComponent } from './apf-liability-screen.component';

describe('ApfLiabilityScreenComponent', () => {
  let component: ApfLiabilityScreenComponent;
  let fixture: ComponentFixture<ApfLiabilityScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfLiabilityScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfLiabilityScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
