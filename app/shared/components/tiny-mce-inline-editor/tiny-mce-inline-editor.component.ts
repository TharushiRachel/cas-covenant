import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";

declare var tinymce: any;

@Component({
  selector: 'app-tiny-mce-inline-editor',
  templateUrl: './tiny-mce-inline-editor.component.html',
  styleUrls: ['./tiny-mce-inline-editor.component.scss']
})
export class TinyMceInlineEditorComponent implements OnInit {

  @Input('html') html: any = '';
  @Input('actionName') actionName: any = 'Save';
  @Input('isSaveAndCloseEnabled') isSaveAndCloseEnabled: any = false;
  @Input('isSaveEnabled') isSaveEnabled: any = true;
  @Input('textAreaHeight') textAreaHeight: string = '65vh';
  @Input('isNeedToEditorClear') isNeedToEditorClear: any = false;
  @Output('onContentSave') onContentSave = new EventEmitter();
  @Output('onContentSaveAndClose') onContentSaveAndClose = new EventEmitter();
  @Output('onTyping') onTyping = new EventEmitter();
  @Output('hasDefaultValue') hasDefaultValue = new EventEmitter();

  componentForm: FormGroup;

  valueChangeSubs = new Subscription();

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    let that = this;
    tinymce.remove();

    tinymce.init({
      selector: '#custom-html-editor-v2',
      browser_spellcheck: true,
      setup: function (editor) {
        editor.on('init', function (e) {
          if (that.html) {
            editor.setContent(that.html);
            that.hasDefaultValue.emit(true);
          }
        });
        editor.on('keyup', () => {
          that.onTyping.emit(tinymce.get("custom-html-editor-v2").getContent());
        });
      },
      height: this.textAreaHeight,
      menubar: true,
      plugins:
        "print preview paste importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists wordcount imagetools textpattern noneditable help charmap quickbars emoticons",
      toolbar:
        "undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview | insertfile image link anchor codesample | ltr rtl",

      content_style:
        "body { font-family: Roboto; }",

      font_formats:
        "Andale Mono=andale mono,times; Arial=arial,helvetica,sans-serif; Arial Black=arial black,avant garde; Book Antiqua=book antiqua,palatino; Comic Sans MS=comic sans ms,sans-serif; Courier New=courier new,courier; Georgia=georgia,palatino; Helvetica=helvetica; Impact=impact,chicago; Roboto=Roboto; Symbol=symbol; Tahoma=tahoma,arial,helvetica,sans-serif; Terminal=terminal,monaco; Times New Roman=times new roman,times; Trebuchet MS=trebuchet ms,geneva; Verdana=verdana,geneva; Webdings=webdings; Wingdings=wingdings,zapf dingbats",

      quickbars_selection_toolbar:
        "bold italic | quicklink h2 h3 blockquote quickimage quicktable",

      toolbar_mode: "sliding",
      skin: "oxide",
      theme_advanced_buttons3_add: "tablecontrols",
      table_styles: "Header 1=header1;Header 2=header2;Header 3=header3",
      table_cell_styles: "Header 1=header1;Header 2=header2;Header 3=header3;Table Cell=tableCel1",
      table_row_styles: "Header 1=header1;Header 2=header2;Header 3=header3;Table Row=tableRow1",
      table_cell_limit: 100,
      table_row_limit: 500,
      table_col_limit: 500
    });
  }

  ngOnDestroy(): void {
    this.valueChangeSubs.unsubscribe();
  }

  onSave() {
    this.onContentSave.emit(tinymce.get("custom-html-editor-v2").getContent());
  }

  onSaveAndClose() {
    this.onContentSaveAndClose.emit(tinymce.get("custom-html-editor-v2").getContent());
  }

}
