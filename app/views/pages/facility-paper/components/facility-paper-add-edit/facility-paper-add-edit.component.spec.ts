import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperAddEditComponent } from './facility-paper-add-edit.component';

describe('FacilityPaperAddEditComponent', () => {
  let component: FacilityPaperAddEditComponent;
  let fixture: ComponentFixture<FacilityPaperAddEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperAddEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperAddEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
