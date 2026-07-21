import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SdInfoDialogComponent } from './sd-info-dialog.component';

describe('SdInfoDialogComponent', () => {
  let component: SdInfoDialogComponent;
  let fixture: ComponentFixture<SdInfoDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdInfoDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdInfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
