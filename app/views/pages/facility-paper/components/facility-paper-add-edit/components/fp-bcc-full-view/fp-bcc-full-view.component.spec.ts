import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpBccFullViewComponent } from './fp-bcc-full-view.component';

describe('FpBccFullViewComponent', () => {
  let component: FpBccFullViewComponent;
  let fixture: ComponentFixture<FpBccFullViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpBccFullViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpBccFullViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
