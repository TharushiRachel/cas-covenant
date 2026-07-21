import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import { FormGroup } from "@angular/forms";
import { Subscription } from "rxjs";

declare var tinymce: any;

@Component({
  selector: "app-tiny-mce-editor",
  templateUrl: "./tiny-mce-editor.component.html",
  styleUrls: ["./tiny-mce-editor.component.scss"],
})
export class TinyMceEditorComponent
  implements OnInit, AfterViewInit, OnDestroy
{
  @Input("html") html: any = "";
  @Input("actionName") actionName: any = "Save";
  @Input("isSaveAndCloseEnabled") isSaveAndCloseEnabled: any = false;
  @Input("isSaveEnabled") isSaveEnabled: any = true;
  @Input("textAreaHeight") textAreaHeight: string = "65vh";
  @Input("isScrollEnabled") isScrollEnabled: any = false;
  @Output("onContentSave") onContentSave = new EventEmitter();
  @Output("onContentSaveAndClose") onContentSaveAndClose = new EventEmitter();
  @Output("notifySaveContent") notifySaveContent = new EventEmitter();
  @Output("setContentByClose") setContentByClose = new EventEmitter();

  componentForm: FormGroup;

  valueChangeSubs = new Subscription();
  content: any = "";

  constructor() {}

  ngOnInit() {}

  ngAfterViewInit(): void {
    let that = this;

    tinymce.remove();

    tinymce.init({
      selector: "#custom-html-editor-v2",
      browser_spellcheck: true,
      setup: function (editor: any) {
        editor.on("init", function (e: any) {
          if (that.html && typeof that.html == "string") {
            editor.setContent(that.html);
          } else {
            editor.setContent("");
          }
        });
        editor.on("keyup", function (e: any) {});
        // editor.on("paste", function (e: any) {
        //   let clipboardData = e && e.clipboardData ? e.clipboardData : null;
        //   let pastedText = clipboardData
        //     ? clipboardData.getData("text")
        //     : "";
        //   const cleanedContent = that.cleanContent(pastedText);

        //   e.preventDefault();

        //   editor.insertContent(cleanedContent);
        // });
      },
      height: this.textAreaHeight,
      menubar: true,
      plugins:
        "print preview powerpaste importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists wordcount imagetools textpattern noneditable help charmap quickbars emoticons",
      toolbar:
        "undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview | insertfile image link anchor codesample | ltr rtl",
      content_style: that.isScrollEnabled
        ? "body { font-family: Roboto; overflow-x: auto !important; }"
        : "body { font-family: Roboto; max-width: 100% !important; overflow-x: hidden !important; } table { max-width: 100%; }",
      font_formats:
        "Andale Mono=andale mono,times; Arial=arial,helvetica,sans-serif; Arial Black=arial black,avant garde; Book Antiqua=book antiqua,palatino; Comic Sans MS=comic sans ms,sans-serif; Courier New=courier new,courier; Georgia=georgia,palatino; Helvetica=helvetica; Impact=impact,chicago; Roboto=Roboto; Symbol=symbol; Tahoma=tahoma,arial,helvetica,sans-serif; Terminal=terminal,monaco; Times New Roman=times new roman,times; Trebuchet MS=trebuchet ms,geneva; Verdana=verdana,geneva; Webdings=webdings; Wingdings=wingdings,zapf dingbats",

      quickbars_selection_toolbar:
        "bold italic | quicklink h2 h3 blockquote quickimage quicktable",

      toolbar_mode: "sliding",
      skin: "oxide",
      powerpaste_allow_local_images: true,
      theme_advanced_buttons3_add: "tablecontrols",
      table_styles: "Header 1=header1;Header 2=header2;Header 3=header3",
      table_cell_styles:
        "Header 1=header1;Header 2=header2;Header 3=header3;Table Cell=tableCel1",
      table_row_styles:
        "Header 1=header1;Header 2=header2;Header 3=header3;Table Row=tableRow1",
      table_cell_limit: 100,
      table_row_limit: 500,
      table_col_limit: 500,
    });
  }

  ngOnDestroy(): void {
    this.valueChangeSubs.unsubscribe();
  }

  cleanContent(content: string): string {
    const tempElement = document.createElement("div");
    tempElement.innerHTML = content;
    tempElement
      .querySelectorAll("[class]")
      .forEach((element) => element.removeAttribute("class"));

    tempElement
      .querySelectorAll("[style]")
      .forEach((element) => element.removeAttribute("style"));

    return tempElement.innerHTML;
  }

  getPreparedContent(content: any) {
    var result: any = "";

    if (content.includes("<p></p>")) {
      content = content.replace("<p></p>", "");
    }

    if (content.includes("<p>&nbsp;</p>")) {
      content = content.replace("<p>&nbsp;</p>", "");
    }

    if (content.includes("<p><br></p>")) {
      content = content.replace("<p><br></p>", "");
    }

    const parser = new DOMParser();
    const html = parser.parseFromString(content, "text/html");

    html.body.querySelectorAll("table").forEach((item: HTMLElement) => {
      item.style.maxWidth = "100%";
      item.style.overflowX = "hidden";
      item.style.margin = "10px 0px";
    });

    result = html.body.innerHTML;

    return result;
  }

  onSave() {
    let content: any = tinymce.get("custom-html-editor-v2").getContent();
    if (this.isScrollEnabled) {
      this.onContentSave.emit(content);
    } else {
      this.onContentSave.emit(this.getPreparedContent(content));
    }
  }

  onSaveAndClose() {
    let content: any = tinymce.get("custom-html-editor-v2").getContent();

    this.onContentSaveAndClose.emit(this.getPreparedContent(content));
  }

  onNotifySaveContent() {
    let content: any = tinymce.get("custom-html-editor-v2").getContent();

    this.notifySaveContent.emit(this.getPreparedContent(content));
  }

  onCloseSaveContent() {
    let content: any = tinymce.get("custom-html-editor-v2").getContent();

    this.setContentByClose.emit(this.getPreparedContent(content));
  }
}
