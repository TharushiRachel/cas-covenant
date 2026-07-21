import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EsgViewAttachmentComponent } from './esg-view-attachment.component';

describe('EsgViewAttachmentComponent', () => {
  let component: EsgViewAttachmentComponent;
  let fixture: ComponentFixture<EsgViewAttachmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EsgViewAttachmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EsgViewAttachmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
