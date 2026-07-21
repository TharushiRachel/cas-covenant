import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviationTypesComponent } from './deviation-types.component';

describe('DeviationTypesComponent', () => {
  let component: DeviationTypesComponent;
  let fixture: ComponentFixture<DeviationTypesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeviationTypesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviationTypesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
