import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfCribScreenComponent } from './apf-crib-screen.component';

describe('ApfCribScreenComponent', () => {
  let component: ApfCribScreenComponent;
  let fixture: ComponentFixture<ApfCribScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfCribScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfCribScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
