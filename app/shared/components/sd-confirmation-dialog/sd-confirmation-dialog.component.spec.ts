import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SdConfirmationDialogComponent } from './sd-confirmation-dialog.component';

describe('SdConfirmationDialogComponent', () => {
  let component: SdConfirmationDialogComponent;
  let fixture: ComponentFixture<SdConfirmationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdConfirmationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
