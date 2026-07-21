import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfEsgScreenComponent } from './apf-esg-screen.component';

describe('ApfEsgScreenComponent', () => {
  let component: ApfEsgScreenComponent;
  let fixture: ComponentFixture<ApfEsgScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfEsgScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfEsgScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
