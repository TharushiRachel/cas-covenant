import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditCustomFacilityDialogComponent } from './add-edit-custom-facility-dialog.component';

describe('AddEditCustomFacilityDialogComponent', () => {
  let component: AddEditCustomFacilityDialogComponent;
  let fixture: ComponentFixture<AddEditCustomFacilityDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditCustomFacilityDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditCustomFacilityDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
