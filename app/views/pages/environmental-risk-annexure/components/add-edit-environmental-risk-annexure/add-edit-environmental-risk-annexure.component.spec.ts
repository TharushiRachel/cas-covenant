import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditEnvironmentalRiskAnnexureComponent } from './add-edit-environmental-risk-annexure.component';

describe('AddEditEnvironmentalRiskAnnexureComponent', () => {
  let component: AddEditEnvironmentalRiskAnnexureComponent;
  let fixture: ComponentFixture<AddEditEnvironmentalRiskAnnexureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditEnvironmentalRiskAnnexureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditEnvironmentalRiskAnnexureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
