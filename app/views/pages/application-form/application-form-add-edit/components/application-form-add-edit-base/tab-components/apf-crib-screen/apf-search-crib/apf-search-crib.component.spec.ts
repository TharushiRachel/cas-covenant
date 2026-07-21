import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfSearchCribComponent } from './apf-search-crib.component';

describe('ApfSearchCribComponent', () => {
  let component: ApfSearchCribComponent;
  let fixture: ComponentFixture<ApfSearchCribComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfSearchCribComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfSearchCribComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
