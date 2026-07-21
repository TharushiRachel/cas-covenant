import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleNodeHtmlComponent } from './single-node-html.component';

describe('SingleNodeHtmlComponent', () => {
  let component: SingleNodeHtmlComponent;
  let fixture: ComponentFixture<SingleNodeHtmlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SingleNodeHtmlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleNodeHtmlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
