import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPaperModalComponent } from './view-paper-modal.component';

describe('ViewPaperModalComponent', () => {
  let component: ViewPaperModalComponent;
  let fixture: ComponentFixture<ViewPaperModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewPaperModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewPaperModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
