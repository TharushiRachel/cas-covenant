import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpDirectorDetailsComponent } from './fp-director-details.component';

describe('FpDirectorDetailsComponent', () => {
  let component: FpDirectorDetailsComponent;
  let fixture: ComponentFixture<FpDirectorDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpDirectorDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpDirectorDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
