import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnvironmentalRiskAnnexureComponent } from './environmental-risk-annexure.component';

describe('EnvironmentalRiskAnnexureComponent', () => {
  let component: EnvironmentalRiskAnnexureComponent;
  let fixture: ComponentFixture<EnvironmentalRiskAnnexureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnvironmentalRiskAnnexureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnvironmentalRiskAnnexureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
