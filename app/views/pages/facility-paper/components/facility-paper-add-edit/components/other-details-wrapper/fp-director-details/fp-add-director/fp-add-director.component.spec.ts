import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpAddDirectorComponent } from './fp-add-director.component';

describe('FpAddDirectorComponent', () => {
  let component: FpAddDirectorComponent;
  let fixture: ComponentFixture<FpAddDirectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpAddDirectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpAddDirectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
