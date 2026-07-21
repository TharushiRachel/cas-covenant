import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DigitalApplicationModalComponent } from './digital-application-modal.component';

describe('DigitalApplicationModalComponent', () => {
  let component: DigitalApplicationModalComponent;
  let fixture: ComponentFixture<DigitalApplicationModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DigitalApplicationModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DigitalApplicationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
