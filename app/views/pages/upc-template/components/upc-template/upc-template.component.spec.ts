import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcTemplateComponent } from './upc-template.component';

describe('UpcTemplateComponent', () => {
  let component: UpcTemplateComponent;
  let fixture: ComponentFixture<UpcTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
