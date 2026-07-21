import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpKalyptoDetailComponent } from './fp-kalypto-detail.component';

describe('FpKalyptoDetailComponent', () => {
  let component: FpKalyptoDetailComponent;
  let fixture: ComponentFixture<FpKalyptoDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpKalyptoDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpKalyptoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
