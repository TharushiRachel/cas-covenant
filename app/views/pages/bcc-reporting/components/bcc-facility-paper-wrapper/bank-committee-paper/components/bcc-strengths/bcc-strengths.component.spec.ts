import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BccStrengthsComponent } from './bcc-strengths.component';

describe('BccStrengthsComponent', () => {
  let component: BccStrengthsComponent;
  let fixture: ComponentFixture<BccStrengthsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BccStrengthsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BccStrengthsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
