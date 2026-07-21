import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ThreeColumnFacilityDataComponent } from './three-column-facility-data.component';

describe('ThreeColumnFacilityDataComponent', () => {
  let component: ThreeColumnFacilityDataComponent;
  let fixture: ComponentFixture<ThreeColumnFacilityDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ThreeColumnFacilityDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ThreeColumnFacilityDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
