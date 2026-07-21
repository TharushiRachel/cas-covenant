import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcTemplateHistorySectionComponent } from './upc-template-history-section.component';

describe('UpcTemplateHistorySectionComponent', () => {
  let component: UpcTemplateHistorySectionComponent;
  let fixture: ComponentFixture<UpcTemplateHistorySectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcTemplateHistorySectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcTemplateHistorySectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
