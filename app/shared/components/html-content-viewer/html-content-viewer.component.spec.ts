import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HtmlContentViewerComponent } from './html-content-viewer.component';

describe('HtmlContentViewerComponent', () => {
  let component: HtmlContentViewerComponent;
  let fixture: ComponentFixture<HtmlContentViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HtmlContentViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HtmlContentViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
