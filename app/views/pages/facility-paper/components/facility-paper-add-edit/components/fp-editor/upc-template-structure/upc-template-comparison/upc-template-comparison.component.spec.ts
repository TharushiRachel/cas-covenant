import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UPCTemplateComparisonComponent } from './upc-template-comparison.component';

describe('UPCTemplateComparisonComponent', () => {
  let component: UPCTemplateComparisonComponent;
  let fixture: ComponentFixture<UPCTemplateComparisonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UPCTemplateComparisonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UPCTemplateComparisonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
