import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpViewDasComponent } from './fp-view-das.component';

describe('FpViewDasComponent', () => {
  let component: FpViewDasComponent;
  let fixture: ComponentFixture<FpViewDasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpViewDasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpViewDasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
