import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfBasicScreenComponent} from './apf-basic-screen.component';

describe('ApfBasicScreenComponent', () => {
  let component: ApfBasicScreenComponent;
  let fixture: ComponentFixture<ApfBasicScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfBasicScreenComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBasicScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
