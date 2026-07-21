import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpPersonalDetailsComponent } from './fp-personal-details.component';

describe('FpPersonalDetailsComponent', () => {
  let component: FpPersonalDetailsComponent;
  let fixture: ComponentFixture<FpPersonalDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpPersonalDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpPersonalDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
