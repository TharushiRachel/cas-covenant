import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviationsComponent } from './deviations.component';

describe('DeviationsComponent', () => {
  let component: DeviationsComponent;
  let fixture: ComponentFixture<DeviationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeviationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
