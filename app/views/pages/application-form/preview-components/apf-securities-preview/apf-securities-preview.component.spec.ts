import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfSecuritiesPreviewComponent } from './apf-securities-preview.component';

describe('ApfSecuritiesPreviewComponent', () => {
  let component: ApfSecuritiesPreviewComponent;
  let fixture: ComponentFixture<ApfSecuritiesPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfSecuritiesPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfSecuritiesPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
