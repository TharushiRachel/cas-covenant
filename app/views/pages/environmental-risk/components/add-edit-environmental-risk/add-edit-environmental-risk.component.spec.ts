import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditEnvironmentalRiskComponent } from './add-edit-environmental-risk.component';

describe('AddEditEnvironmentalRiskComponent', () => {
  let component: AddEditEnvironmentalRiskComponent;
  let fixture: ComponentFixture<AddEditEnvironmentalRiskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditEnvironmentalRiskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditEnvironmentalRiskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
