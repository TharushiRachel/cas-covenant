import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityChangesModalComponent } from './facility-changes-modal.component';

describe('FacilityChangesModalComponent', () => {
  let component: FacilityChangesModalComponent;
  let fixture: ComponentFixture<FacilityChangesModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityChangesModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityChangesModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
