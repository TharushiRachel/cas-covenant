import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityPapersComponent } from './facility-papers.component';

describe('FacilityPapersComponent', () => {
  let component: FacilityPapersComponent;
  let fixture: ComponentFixture<FacilityPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
