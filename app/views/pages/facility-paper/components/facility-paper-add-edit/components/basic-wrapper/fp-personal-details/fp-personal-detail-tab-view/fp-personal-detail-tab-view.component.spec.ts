import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpPersonalDetailTabViewComponent } from './fp-personal-detail-tab-view.component';

describe('FpPersonalDetailTabViewComponent', () => {
  let component: FpPersonalDetailTabViewComponent;
  let fixture: ComponentFixture<FpPersonalDetailTabViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpPersonalDetailTabViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpPersonalDetailTabViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
