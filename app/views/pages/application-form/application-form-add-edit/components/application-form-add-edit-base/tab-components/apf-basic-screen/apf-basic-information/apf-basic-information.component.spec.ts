import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApfBasicInformationComponent} from './apf-basic-information.component';

describe('ApfBasicInformationComponent', () => {
  let component: ApfBasicInformationComponent;
  let fixture: ComponentFixture<ApfBasicInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ApfBasicInformationComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfBasicInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
