import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowHtmlModalComponent } from './show-html-modal.component';

describe('ShowHtmlModalComponent', () => {
  let component: ShowHtmlModalComponent;
  let fixture: ComponentFixture<ShowHtmlModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowHtmlModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowHtmlModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
