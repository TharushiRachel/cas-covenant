import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SdCovenantModalComponent } from './sd-covenant-modal.component';

describe('SdCovenantModalComponent', () => {
  let component: SdCovenantModalComponent;
  let fixture: ComponentFixture<SdCovenantModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SdCovenantModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SdCovenantModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
