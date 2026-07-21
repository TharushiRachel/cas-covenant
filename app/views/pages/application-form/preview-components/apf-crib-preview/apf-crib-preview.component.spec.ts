import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfCribPreviewComponent } from './apf-crib-preview.component';

describe('ApfCribPreviewComponent', () => {
  let component: ApfCribPreviewComponent;
  let fixture: ComponentFixture<ApfCribPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfCribPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfCribPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
