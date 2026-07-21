import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAddEditDocumentsComponent } from './apf-add-edit-documents.component';

describe('ApfAddEditDocumentsComponent', () => {
  let component: ApfAddEditDocumentsComponent;
  let fixture: ComponentFixture<ApfAddEditDocumentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAddEditDocumentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
