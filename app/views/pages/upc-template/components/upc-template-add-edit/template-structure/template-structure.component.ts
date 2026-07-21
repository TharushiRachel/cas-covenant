import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {ITreeOptions, KEYS, TREE_ACTIONS} from "angular-tree-component";
import * as _ from 'lodash';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-template-structure',
  templateUrl: './template-structure.component.html',
  styleUrls: ['./template-structure.component.scss']
})
export class TemplateStructureComponent implements OnInit, OnChanges, OnDestroy {

  @Input('allUpcSectionData') allUpcSectionData: any = [];
  @Input('initNodeData') initNodeData: any = [];
  @Input('alreadyAddedUpcSectionIDs') alreadyAddedUpcSectionIDs: any = [];

  @Output('onUpdate') onUpdate = new EventEmitter();
  @Output('search') search = new EventEmitter();

  nodes = [];
  addedIDs: any = [];

  options: ITreeOptions = {
    actionMapping: {
      mouse: {
        click: TREE_ACTIONS.TOGGLE_ACTIVE,
        dblClick: null,
        contextMenu: null,
        expanderClick: TREE_ACTIONS.TOGGLE_EXPANDED,
        checkboxClick: TREE_ACTIONS.TOGGLE_SELECTED,
        drop: (tree, node, $event, {from, to}) => {

          if (!from.hasOwnProperty('treeModel')) {
           // console.log('drag', from, to);

            this.nodes.push({
              name: from.name,
              item: from.item,
              isExpanded: true,
              children: []
            });

            this.addToAddedIDs(from.item.upcSectionID);
            tree.update();

          } else {
            TREE_ACTIONS.MOVE_NODE(tree, node, $event, {from, to});
          }

          this.onUpdate.emit(this.nodes);
        }
      },
      keys: {
        [KEYS.ENTER]: (tree, node, $event) => {
          node.expandAll();
        }
      },
    },
    nodeHeight: 23,
    allowDrag: (node) => {
      return true;
    },
    allowDrop: (node) => {
      return true;
    },
    allowDragoverStyling: true,
    levelPadding: 20,
    useVirtualScroll: true,
    animateExpand: true,
    scrollOnActivate: true,
    animateSpeed: 30,
    animateAcceleration: 1.2,
    scrollContainer: document.documentElement // HTML
  };


  upcSearchForm: FormGroup;
  searchKeyWord: '';
  onSearchFormChangeSub: Subscription = new Subscription();
  resultUpcSectionData: any = [];

  constructor(private formBuilder: FormBuilder) {
    this.resultUpcSectionData = this.allUpcSectionData;
    this.upcSearchForm = this.createUPCSearchForm()
  }

  ngOnInit() {
    this.nodes = this.initNodeData;
    this.addedIDs = this.alreadyAddedUpcSectionIDs;

    this.onSearchFormChangeSub = this.upcSearchForm.valueChanges
      .debounceTime(200)
      .distinctUntilChanged()
      .subscribe(form => {
        this.search.emit(form.searchKeyWord);
      });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.nodes = this.initNodeData;
    this.addedIDs = this.alreadyAddedUpcSectionIDs;
  }

  ngOnDestroy(): void {
    this.onSearchFormChangeSub.unsubscribe();
  }

  addToAddedIDs(id) {
    this.addedIDs = [...this.addedIDs, id];
  }

  removeFromAddedID(ids) {
    this.addedIDs = _.without(this.addedIDs, ...ids);
  }

  addEligible(item) {
    return _.indexOf(this.addedIDs, item.upcSectionID) === -1;
  }

  removeItem(treeModel, node) {

    let removedIDs = [];

    this.getRrmovedItemIDs(removedIDs, node.data);

    _.remove(node.parent.data.children, node.data);
    treeModel.update();

    this.removeFromAddedID(removedIDs);
  }

  getRrmovedItemIDs(dataArray, node) {

    dataArray.push(node.item.upcSectionID);

    if (node.children && node.children.length > 0) {
      for (let i = 0; i < node.children.length; i++) {
        this.getRrmovedItemIDs(dataArray, node.children[i]);
      }

    }
  }

  createUPCSearchForm() {
    return this.formBuilder.group({
      searchKeyWord: [this.searchKeyWord]
    });
  }

}
