import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
  ViewEncapsulation
} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {HttpClient} from "@angular/common/http";
import {Subscription} from "rxjs";

declare var $: any;

@Component({
  selector: 'app-rich-text-editor',
  templateUrl: './rich-text-editor.component.html',
  styleUrls: ['./rich-text-editor.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class RichTextEditorComponent implements OnInit, OnChanges, OnDestroy {

  @Input('html') html: any = '';

  @Output('onContentSave') onContentSave = new EventEmitter();

  htmlSrc: any = '';

  htmlChangeSubs = new Subscription();

  constructor(public sanitizer: DomSanitizer,
              private _httpClient: HttpClient) {
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['html'] && (changes['html'].currentValue != null || changes['html'].currentValue != undefined)) {
      this.changeContainerHtml(this.html)
    }
  }

  ngOnDestroy(): void {
    this.htmlChangeSubs.unsubscribe();
  }

  changeContainerHtml(customHtml) {
    let path = 'assets/webeditor/' + 'index_.html';

    this.htmlChangeSubs.unsubscribe();
    this.htmlChangeSubs = this._httpClient.get(path, {responseType: 'text'}).subscribe(
      data => {
        let replaceData = customHtml.replaceAll("'", "&apos;").replace('\n', '<br/>');

        let customData = data.replace('Please Enter Your Input Here', replaceData);
        this.htmlSrc = this.sanitizer.bypassSecurityTrustHtml(customData);
      });
  }

  onSave() {
    let button = $('iframe[name=rich_editor_iframe_]').contents().find('#get_content_button');
    $(button[0]).click();
    let textAreaValue = $('iframe[name=rich_editor_iframe_]').contents().find('#final_content_text_area').val();

    this.onContentSave.emit(textAreaValue.replaceAll("'", "&apos;"));

  }

}
