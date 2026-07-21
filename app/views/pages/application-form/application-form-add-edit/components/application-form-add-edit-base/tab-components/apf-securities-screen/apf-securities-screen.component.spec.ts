import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfSecuritiesScreenComponent } from './apf-securities-screen.component';

describe('ApfSecuritiesScreenComponent', () => {
  let component: ApfSecuritiesScreenComponent;
  let fixture: ComponentFixture<ApfSecuritiesScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfSecuritiesScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfSecuritiesScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
