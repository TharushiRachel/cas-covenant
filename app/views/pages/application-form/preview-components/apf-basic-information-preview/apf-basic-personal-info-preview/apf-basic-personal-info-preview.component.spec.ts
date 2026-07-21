import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfBasicPersonalInfoPreviewComponent } from './apf-basic-personal-info-preview.component';

describe('ApfBasicPersonalInfoPreviewComponent', () => {
  let component: ApfBasicPersonalInfoPreviewComponent;
  let fixture: ComponentFixture<ApfBasicPersonalInfoPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfBasicPersonalInfoPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBasicPersonalInfoPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
