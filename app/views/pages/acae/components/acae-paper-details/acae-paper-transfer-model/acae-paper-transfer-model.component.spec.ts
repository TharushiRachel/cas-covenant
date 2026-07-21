import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ACAEPaperTransferModelComponent } from './acae-paper-transfer-model.component';

describe('ACAEDetailsEditComponent', () => {
  let component: ACAEPaperTransferModelComponent;
  let fixture: ComponentFixture<ACAEPaperTransferModelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ACAEPaperTransferModelComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ACAEPaperTransferModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
