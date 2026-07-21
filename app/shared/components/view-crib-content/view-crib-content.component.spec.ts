import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCribContentComponent } from './view-crib-content.component';

describe('ViewCribContentComponent', () => {
  let component: ViewCribContentComponent;
  let fixture: ComponentFixture<ViewCribContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewCribContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewCribContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
