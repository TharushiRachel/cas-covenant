import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FpEditorComponent } from './fp-editor.component';

describe('FpEditorComponent', () => {
  let component: FpEditorComponent;
  let fixture: ComponentFixture<FpEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FpEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FpEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
