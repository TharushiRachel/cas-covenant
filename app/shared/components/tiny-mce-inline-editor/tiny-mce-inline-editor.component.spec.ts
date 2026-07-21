import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TinyMceInlineEditorComponent } from './tiny-mce-inline-editor.component';

describe('TinyMceInlineEditorComponent', () => {
  let component: TinyMceInlineEditorComponent;
  let fixture: ComponentFixture<TinyMceInlineEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TinyMceInlineEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TinyMceInlineEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
