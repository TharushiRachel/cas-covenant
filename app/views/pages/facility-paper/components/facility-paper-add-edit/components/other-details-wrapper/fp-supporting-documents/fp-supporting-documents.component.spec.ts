import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpSupportingDocumentsComponent } from './fp-supporting-documents.component';

describe('FpSupportingDocumentsComponent', () => {
  let component: FpSupportingDocumentsComponent;
  let fixture: ComponentFixture<FpSupportingDocumentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpSupportingDocumentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpSupportingDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
