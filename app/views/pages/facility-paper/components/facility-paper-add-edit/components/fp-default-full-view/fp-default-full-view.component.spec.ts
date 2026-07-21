import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpDefaultFullViewComponent } from './fp-default-full-view.component';

describe('FpDefaultFullViewComponent', () => {
  let component: FpDefaultFullViewComponent;
  let fixture: ComponentFixture<FpDefaultFullViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpDefaultFullViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpDefaultFullViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
