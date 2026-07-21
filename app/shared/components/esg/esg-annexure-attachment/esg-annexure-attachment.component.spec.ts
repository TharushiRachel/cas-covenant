import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EsgAnnexureAttachmentComponent } from './esg-annexure-attachment.component';

describe('EsgAnnexureAttachmentComponent', () => {
  let component: EsgAnnexureAttachmentComponent;
  let fixture: ComponentFixture<EsgAnnexureAttachmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EsgAnnexureAttachmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EsgAnnexureAttachmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
