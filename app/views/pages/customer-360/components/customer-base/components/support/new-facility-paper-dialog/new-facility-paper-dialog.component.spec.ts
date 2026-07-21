import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewFacilityPaperDialogComponent } from './new-facility-paper-dialog.component';

describe('NewFacilityPaperDialogComponent', () => {
  let component: NewFacilityPaperDialogComponent;
  let fixture: ComponentFixture<NewFacilityPaperDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewFacilityPaperDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewFacilityPaperDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
