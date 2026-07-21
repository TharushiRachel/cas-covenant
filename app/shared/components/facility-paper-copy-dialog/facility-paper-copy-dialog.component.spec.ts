import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperCopyDialogComponent } from './facility-paper-copy-dialog.component';

describe('FacilityPaperCopyDialogComponent', () => {
  let component: FacilityPaperCopyDialogComponent;
  let fixture: ComponentFixture<FacilityPaperCopyDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperCopyDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperCopyDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
