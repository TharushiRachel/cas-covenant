import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowFullNodeHtmlComponent } from './show-full-node-html.component';

describe('ShowFullNodeHtmlComponent', () => {
  let component: ShowFullNodeHtmlComponent;
  let fixture: ComponentFixture<ShowFullNodeHtmlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowFullNodeHtmlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowFullNodeHtmlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
