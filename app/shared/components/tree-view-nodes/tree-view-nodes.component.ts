import {
  AfterContentInit,
  ApplicationRef,
  Component,
  ComponentFactoryResolver,
  ElementRef,
  EventEmitter,
  Injector,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  Renderer2,
  SimpleChanges,
  ViewChild,
} from "@angular/core";
import { HtmlContainer } from "./HtmlContainer";
import { ViewNodeComponent } from "./view-node/view-node.component";

@Component({
  selector: "app-tree-view-nodes",
  templateUrl: "./tree-view-nodes.component.html",
  styleUrls: ["./tree-view-nodes.component.scss"],
})
export class TreeViewNodesComponent
  implements OnInit, AfterContentInit, OnChanges, OnDestroy
{
  @Input("facilityPaperId") facilityPaperId: any;
  @Input("currentAssignUser") currentAssignUser:any;
  @Input("currentFPStatus") currentFPStatus:any
  @Input("currentFPWC") currentFPWC:any
  @Input("nodes") nodes: any = [];
  @Input("assignDepartmentCode") assignDepartmentCode: any;
  @Input("isPreviewMode") isPreviewMode: boolean;

  @Output("onEdit") onEdit: EventEmitter<any> = new EventEmitter();

  @Input("isCommentEnable") isCommentEnable: boolean;

  @Output("openModalUPCTemplateComment")
  openModalUPCTemplateComment: EventEmitter<any> = new EventEmitter();

  @ViewChild("treeContainer", { static: true }) treeContainer: ElementRef;

  containers: HtmlContainer[] = [];

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private renderer2: Renderer2,
    private appRef: ApplicationRef,
    private injector: Injector
  ) {}

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["nodes"]) {
      if (!changes["nodes"].isFirstChange()) {
        this.reDrawTree();
      }
    }

    if (changes["isPreviewMode"] && !changes["isPreviewMode"].isFirstChange()) {
      this.reDrawTree();
    }
  }

  ngAfterContentInit(): void {
    this.createList(
      this.nodes,
      this.renderer2,
      this.treeContainer.nativeElement
    );
  }

  reDrawTree() {
    // Remove created component and tree structure
    this.containers.forEach((container) => container.dispose());
    const childElements = this.treeContainer.nativeElement.childNodes;
    for (let child of childElements) {
      this.renderer2.removeChild(this.treeContainer.nativeElement, child);
    }

    // Recreate tree structure
    this.createList(
      this.nodes,
      this.renderer2,
      this.treeContainer.nativeElement
    );
  }

  createList(nodes, renderer, containerNativeElement) {
    const ul = renderer.createElement("ul");
    renderer.appendChild(containerNativeElement, ul);
    for (const node of nodes) {
      const li = renderer.createElement("li");
      this.addNodeComponent(
        ViewNodeComponent,
        li,
        node.title,
        node.content,
        node.id,
        node
      );
      renderer.appendChild(ul, li);

      if (node.children && node.children.length > 0) {
        this.createList(node.children, renderer, li);
      }
    }
  }

  addNodeComponent(component, element, nodeTitle, nodeContent, nodeId, node) {
    const container = new HtmlContainer(
      element,
      this.appRef,
      this.componentFactoryResolver,
      this.injector
    );
    const componentRef = container.attach(component);
    componentRef.instance.nodeTitle = nodeTitle;
    componentRef.instance.nodeContent = nodeContent;
    componentRef.instance.nodeId = nodeId;
    componentRef.instance.node = node;
    componentRef.instance.isPreviewMode = this.isPreviewMode;
    componentRef.instance.isCommentEnable = this.isCommentEnable;
    componentRef.instance.facilityPaperId = this.facilityPaperId;
    componentRef.instance.currentAssignUser = this.currentAssignUser;
    componentRef.instance.currentFPStatus = this.currentFPStatus;
    componentRef.instance.currentFPWC = this.currentFPWC;
    componentRef.instance.assignDepartmentCode = this.assignDepartmentCode;

    //console.log("componentRef------>",componentRef)
    componentRef.instance.onEditClick.subscribe((value:any) => {
      this.onEdit.emit(value);
    });
    componentRef.instance.onCommentClick.subscribe((value:any) => {
      this.openModalUPCTemplateComment.emit(value);
    });

    this.containers.push(container);
  }

  ngOnDestroy(): void {
    this.containers.forEach((container) => container.dispose());
  }
}
