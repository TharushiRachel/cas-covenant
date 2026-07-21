import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccRecommendedOptionsComponent } from './bcc-recommended-options.component';

describe('BccRecommendedOptionsComponent', () => {
  let component: BccRecommendedOptionsComponent;
  let fixture: ComponentFixture<BccRecommendedOptionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccRecommendedOptionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccRecommendedOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
