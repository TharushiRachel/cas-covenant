import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewFpAboutComponent } from './preview-fp-about.component';

describe('PreviewFpAboutComponent', () => {
  let component: PreviewFpAboutComponent;
  let fixture: ComponentFixture<PreviewFpAboutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewFpAboutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewFpAboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
