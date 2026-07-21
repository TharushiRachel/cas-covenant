import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewExistingCovenantsComponent } from './preview-existing-covenants.component';

describe('PreviewExistingCovenantsComponent', () => {
  let component: PreviewExistingCovenantsComponent;
  let fixture: ComponentFixture<PreviewExistingCovenantsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewExistingCovenantsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewExistingCovenantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
