import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfBasicCorporateInfoPreviewComponent } from './apf-basic-corporate-info-preview.component';

describe('ApfBasicCorporateInfoPreviewComponent', () => {
  let component: ApfBasicCorporateInfoPreviewComponent;
  let fixture: ComponentFixture<ApfBasicCorporateInfoPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfBasicCorporateInfoPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBasicCorporateInfoPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
