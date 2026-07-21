import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBccPdfComponent } from './view-bcc-pdf.component';

describe('ViewBccPdfComponent', () => {
  let component: ViewBccPdfComponent;
  let fixture: ComponentFixture<ViewBccPdfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewBccPdfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewBccPdfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
