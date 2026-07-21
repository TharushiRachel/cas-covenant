import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupportDocumentAddEditComponent } from './support-document-add-edit.component';

describe('SupportDocumentAddEditComponent', () => {
  let component: SupportDocumentAddEditComponent;
  let fixture: ComponentFixture<SupportDocumentAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupportDocumentAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupportDocumentAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
