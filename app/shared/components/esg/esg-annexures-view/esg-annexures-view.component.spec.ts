import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EsgAnnexuresViewComponent } from './esg-annexures-view.component';

describe('EsgAnnexuresViewComponent', () => {
  let component: EsgAnnexuresViewComponent;
  let fixture: ComponentFixture<EsgAnnexuresViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EsgAnnexuresViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EsgAnnexuresViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
