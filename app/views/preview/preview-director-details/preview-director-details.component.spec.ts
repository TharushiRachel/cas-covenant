import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewDirectorDetailsComponent } from './preview-director-details.component';

describe('PreviewDirectorDetailsComponent', () => {
  let component: PreviewDirectorDetailsComponent;
  let fixture: ComponentFixture<PreviewDirectorDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewDirectorDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewDirectorDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
