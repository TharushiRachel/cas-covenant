import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CasFacilityPaperListComponent } from './cas-facility-paper-list.component';

describe('CasFacilityPaperListComponent', () => {
  let component: CasFacilityPaperListComponent;
  let fixture: ComponentFixture<CasFacilityPaperListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CasFacilityPaperListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasFacilityPaperListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
