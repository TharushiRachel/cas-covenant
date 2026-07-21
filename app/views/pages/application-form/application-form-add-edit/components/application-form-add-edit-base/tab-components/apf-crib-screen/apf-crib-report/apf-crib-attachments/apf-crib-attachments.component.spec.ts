import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfCribAttachmentsComponent } from './apf-crib-attachments.component';

describe('ApfCribAttachmentsComponent', () => {
  let component: ApfCribAttachmentsComponent;
  let fixture: ComponentFixture<ApfCribAttachmentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfCribAttachmentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfCribAttachmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
