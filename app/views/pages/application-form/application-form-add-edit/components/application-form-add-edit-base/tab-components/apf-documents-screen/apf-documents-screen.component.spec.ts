import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfDocumentsScreenComponent } from './apf-documents-screen.component';

describe('ApfDocumentsScreenComponent', () => {
  let component: ApfDocumentsScreenComponent;
  let fixture: ComponentFixture<ApfDocumentsScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfDocumentsScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfDocumentsScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
