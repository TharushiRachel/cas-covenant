import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccAttachmentsComponent } from './bcc-attachments.component';

describe('BccAttachmentsComponent', () => {
  let component: BccAttachmentsComponent;
  let fixture: ComponentFixture<BccAttachmentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccAttachmentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccAttachmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
