import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttachmentDetailModalComponent } from './attachment-detail-modal.component';

describe('AttachmentDetailModalComponent', () => {
  let component: AttachmentDetailModalComponent;
  let fixture: ComponentFixture<AttachmentDetailModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttachmentDetailModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttachmentDetailModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
