import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpDocumentationNewComponent } from './fp-documentation-new.component';

describe('FpDocumentationNewComponent', () => {
  let component: FpDocumentationNewComponent;
  let fixture: ComponentFixture<FpDocumentationNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpDocumentationNewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpDocumentationNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
