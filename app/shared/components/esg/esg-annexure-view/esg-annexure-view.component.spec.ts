import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EsgAnnexureViewComponent } from './esg-annexure-view.component';

describe('EsgAnnexureViewComponent', () => {
  let component: EsgAnnexureViewComponent;
  let fixture: ComponentFixture<EsgAnnexureViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EsgAnnexureViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EsgAnnexureViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
