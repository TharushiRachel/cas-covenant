import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccJustificationComponent } from './bcc-justification.component';

describe('BccJustificationComponent', () => {
  let component: BccJustificationComponent;
  let fixture: ComponentFixture<BccJustificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccJustificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccJustificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
