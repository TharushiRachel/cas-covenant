import { AfterViewInit, Component, OnInit } from "@angular/core";
import { MDBModalRef } from "ng-uikit-pro-standard";
import { Subject } from "rxjs";

declare let tinymce: any;

@Component({
  selector: "app-save-risk-opinion-reply",
  templateUrl: "./save-risk-opinion-reply.component.html",
  styleUrls: ["./save-risk-opinion-reply.component.scss"],
})
export class SaveRiskOpinionReplyComponent implements OnInit, AfterViewInit {
  heading: string = "";
  html: any = "";
  content: any = "";
  textAreaHeight: string = "65vh";

  action: Subject<any> = new Subject<any>();

  constructor(public mdbModalRef: MDBModalRef) {}

  ngOnInit() {
    //ngOnInit
  }

  ngAfterViewInit(): void {
    let that = this;

    tinymce.remove();

    tinymce.init({
      selector: "#custom-html-editor-v3",
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
      },
      height: this.textAreaHeight,
      menubar: true,
      plugins:
        "print preview powerpaste importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists wordcount imagetools textpattern noneditable help charmap quickbars emoticons",
      toolbar:
        "undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview | insertfile image link anchor codesample | ltr rtl",
      content_style:
        "body { font-family: Roboto; max-width: 100% !important; overflow-x: hidden !important; } table { max-width: 100%; }",
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

  onSave() {
    let content: any = tinymce.get("custom-html-editor-v3").getContent();
    this.action.next(content);
  }
}
