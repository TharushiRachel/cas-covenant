import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpDocumentationComponent } from './fp-documentation.component';

describe('FpDocumentationComponent', () => {
  let component: FpDocumentationComponent;
  let fixture: ComponentFixture<FpDocumentationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpDocumentationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpDocumentationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
