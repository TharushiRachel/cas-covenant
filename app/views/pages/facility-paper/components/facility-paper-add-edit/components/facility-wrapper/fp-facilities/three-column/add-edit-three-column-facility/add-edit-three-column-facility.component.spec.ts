import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditThreeColumnFacilityComponent } from './add-edit-three-column-facility.component';

describe('AddEditThreeColumnFacilityComponent', () => {
  let component: AddEditThreeColumnFacilityComponent;
  let fixture: ComponentFixture<AddEditThreeColumnFacilityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditThreeColumnFacilityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditThreeColumnFacilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
