import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditDocumentComponent } from './add-edit-document.component';

describe('AddEditDocumentComponent', () => {
  let component: AddEditDocumentComponent;
  let fixture: ComponentFixture<AddEditDocumentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditDocumentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
