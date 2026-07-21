import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfFacilitiesScreenComponent } from './apf-facilities-screen.component';

describe('ApfFacilitiesScreenComponent', () => {
  let component: ApfFacilitiesScreenComponent;
  let fixture: ComponentFixture<ApfFacilitiesScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfFacilitiesScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfFacilitiesScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
