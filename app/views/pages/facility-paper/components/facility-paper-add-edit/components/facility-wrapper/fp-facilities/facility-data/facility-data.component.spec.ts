import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityDataComponent } from './facility-data.component';

describe('FacilityDataComponent', () => {
  let component: FacilityDataComponent;
  let fixture: ComponentFixture<FacilityDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
