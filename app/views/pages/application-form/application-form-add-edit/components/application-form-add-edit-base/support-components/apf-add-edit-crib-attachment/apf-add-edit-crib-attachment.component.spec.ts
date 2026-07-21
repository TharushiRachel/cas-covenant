import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfAddEditCribAttachmentComponent} from './apf-add-edit-crib-attachment.component';

describe('ApfAddEditCribAttachmentComponent', () => {
  let component: ApfAddEditCribAttachmentComponent;
  let fixture: ComponentFixture<ApfAddEditCribAttachmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfAddEditCribAttachmentComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAddEditCribAttachmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
