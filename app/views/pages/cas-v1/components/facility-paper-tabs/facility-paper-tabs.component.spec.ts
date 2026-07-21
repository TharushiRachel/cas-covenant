import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperTabsComponent } from './facility-paper-tabs.component';

describe('FacilityPaperTabsComponent', () => {
  let component: FacilityPaperTabsComponent;
  let fixture: ComponentFixture<FacilityPaperTabsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperTabsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
