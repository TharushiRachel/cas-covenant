import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPaperSearchComponent } from './facility-paper-search.component';

describe('FacilityPaperSearchComponent', () => {
  let component: FacilityPaperSearchComponent;
  let fixture: ComponentFixture<FacilityPaperSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPaperSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPaperSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
