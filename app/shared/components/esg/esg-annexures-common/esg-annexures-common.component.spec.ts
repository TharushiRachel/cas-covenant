import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EsgAnnexuresCommonComponent } from './esg-annexures-common.component';

describe('EsgAnnexuresCommonComponent', () => {
  let component: EsgAnnexuresCommonComponent;
  let fixture: ComponentFixture<EsgAnnexuresCommonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EsgAnnexuresCommonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EsgAnnexuresCommonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
