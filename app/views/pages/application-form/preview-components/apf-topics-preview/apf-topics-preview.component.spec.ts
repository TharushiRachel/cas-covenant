import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfTopicsPreviewComponent } from './apf-topics-preview.component';

describe('ApfTopicsPreviewComponent', () => {
  let component: ApfTopicsPreviewComponent;
  let fixture: ComponentFixture<ApfTopicsPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfTopicsPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfTopicsPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
