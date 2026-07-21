import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcTemplateReadViewComponent } from './upc-template-read-view.component';

describe('UpcTemplateReadViewComponent', () => {
  let component: UpcTemplateReadViewComponent;
  let fixture: ComponentFixture<UpcTemplateReadViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcTemplateReadViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcTemplateReadViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
