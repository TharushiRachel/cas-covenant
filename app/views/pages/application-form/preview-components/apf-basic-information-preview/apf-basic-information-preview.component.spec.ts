import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfBasicInformationPreviewComponent } from './apf-basic-information-preview.component';

describe('ApfBasicInformationPreviewComponent', () => {
  let component: ApfBasicInformationPreviewComponent;
  let fixture: ComponentFixture<ApfBasicInformationPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfBasicInformationPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBasicInformationPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
