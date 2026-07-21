import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DigitalApplicationViewComponent } from './digital-application-view.component';

describe('DigitalApplicationViewComponent', () => {
  let component: DigitalApplicationViewComponent;
  let fixture: ComponentFixture<DigitalApplicationViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DigitalApplicationViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DigitalApplicationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
