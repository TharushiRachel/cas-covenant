import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfDirectorDetailsPreviewComponent} from './apf-director-details-preview.component';

describe('ApfDirectorDetailsPreviewComponent', () => {
  let component: ApfDirectorDetailsPreviewComponent;
  let fixture: ComponentFixture<ApfDirectorDetailsPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfDirectorDetailsPreviewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfDirectorDetailsPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
