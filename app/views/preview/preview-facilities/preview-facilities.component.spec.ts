import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewFacilitiesComponent } from './preview-facilities.component';

describe('PreviewFacilitiesComponent', () => {
  let component: PreviewFacilitiesComponent;
  let fixture: ComponentFixture<PreviewFacilitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewFacilitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewFacilitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
