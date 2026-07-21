import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfSecuritiesRowPreviewComponent } from './apf-securities-row-preview.component';

describe('ApfSecuritiesRowPreviewComponent', () => {
  let component: ApfSecuritiesRowPreviewComponent;
  let fixture: ComponentFixture<ApfSecuritiesRowPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfSecuritiesRowPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfSecuritiesRowPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
