import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EsgAnnexureSelectorsCommonComponent } from './esg-annexure-selectors-common.component';

describe('EsgAnnexureSelectorsCommonComponent', () => {
  let component: EsgAnnexureSelectorsCommonComponent;
  let fixture: ComponentFixture<EsgAnnexureSelectorsCommonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EsgAnnexureSelectorsCommonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EsgAnnexureSelectorsCommonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
