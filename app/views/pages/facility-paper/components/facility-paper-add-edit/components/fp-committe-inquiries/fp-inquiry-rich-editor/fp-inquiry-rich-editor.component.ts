import { AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';

declare var tinymce: any;

@Component({
  selector: 'app-fp-inquiry-rich-editor',
  templateUrl: './fp-inquiry-rich-editor.component.html',
  styleUrls: ['./fp-inquiry-rich-editor.component.scss']
})
export class FpInquiryRichEditorComponent implements OnInit, AfterViewInit, OnDestroy {
  @Input() html: string = '';
  @Output() contentChange = new EventEmitter<string>();

   editorId = 'fp-inquiry-editor-' + Math.floor(Math.random() * 100000);

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    const that = this;
    tinymce.remove(`#${this.editorId}`);

    tinymce.init({
      selector: `#${this.editorId}`,
      browser_spellcheck: true,
      menubar: false,
      height: 400,
      plugins: 'lists link emoticons',
      toolbar: 'undo redo | bold italic underline | bullist numlist | link emoticons',
      setup(editor: any) {
        editor.on('init', () => {
          editor.setContent(that.html || '');
        });
        editor.on('keyup change', () => {
          that.contentChange.emit(editor.getContent());
        });
      },
      content_style: 'body { font-family: Roboto, Arial, sans-serif; font-size: 14px; }'
    });
  }

  ngOnDestroy(): void {
    tinymce.remove(`#${this.editorId}`);
  }

  getContent(): string {
    const editor = tinymce.get(this.editorId);
    return editor ? editor.getContent() : '';
  }

}
