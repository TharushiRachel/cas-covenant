import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchFacilityPaperComponent } from './search-facility-paper.component';

describe('SearchFacilityPaperComponent', () => {
  let component: SearchFacilityPaperComponent;
  let fixture: ComponentFixture<SearchFacilityPaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchFacilityPaperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchFacilityPaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
