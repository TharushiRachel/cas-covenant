import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewUpcDocumentComponent } from './preview-upc-document.component';

describe('PreviewUpcDocumentComponent', () => {
  let component: PreviewUpcDocumentComponent;
  let fixture: ComponentFixture<PreviewUpcDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewUpcDocumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewUpcDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
