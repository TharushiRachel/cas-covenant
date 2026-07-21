import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfBasicBusinessInfoPreviewComponent } from './apf-basic-business-info-preview.component';

describe('ApfBasicBusinessInfoPreviewComponent', () => {
  let component: ApfBasicBusinessInfoPreviewComponent;
  let fixture: ComponentFixture<ApfBasicBusinessInfoPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfBasicBusinessInfoPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBasicBusinessInfoPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
