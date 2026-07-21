import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnvironmentalRiskComponent } from './environmental-risk.component';

describe('EnvironmentalRiskComponent', () => {
  let component: EnvironmentalRiskComponent;
  let fixture: ComponentFixture<EnvironmentalRiskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnvironmentalRiskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnvironmentalRiskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
