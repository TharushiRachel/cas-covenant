import {
  AfterContentChecked,
  AfterContentInit,
  Component,
  ContentChildren,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  QueryList,
  SimpleChanges,
} from "@angular/core";
import { TabComponent } from "./tab/tab.component";

@Component({
  selector: "app-tabs",
  templateUrl: "./tabs.component.html",
  styleUrls: ["./tabs.component.scss"],
})
export class TabsComponent
  implements AfterContentInit, OnChanges, AfterContentChecked
{
  @Output("onTabSelect") onTabSelect = new EventEmitter();

  @Input("selectedTabIndex") selectedTabIndex: number = 0;

  @ContentChildren(TabComponent) tabs: QueryList<TabComponent>;

  activeTabIndex: number = 0;

  constructor() {}

  ngOnInit() {}

  // contentChildren are set
  ngAfterContentInit() {
    // get all active tabs
    let activeTabs = this.tabs.filter((tab: any) => tab.active);

    // if there is no active tab set, activate the first
    if (activeTabs.length === 0) {
      // this.selectTab(this.tabs.first, 0);
      this.selectTab(
        this.tabs.toArray()[this.selectedTabIndex],
        this.selectedTabIndex
      );
    }
  }

  ngAfterContentChecked(): void {
    let activeTabs = this.tabs.filter((tab: any) => tab.active);
    if (activeTabs.length === 0) {
      this.selectTab(
        this.tabs.toArray()[this.selectedTabIndex],
        this.selectedTabIndex
      );
    }
  }

  selectTab(tab: any, index: any) {
    // deactivate all tabs
    this.tabs.toArray().forEach((tab: any) => (tab.active = false));

    // activate the tab the user has clicked on.
    tab.active = true;

    this.activeTabIndex = index;
    this.onTabSelect.emit(index);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes["selectedTabIndex"]) {
      if (this.tabs != undefined) {
        // deactivate all tabs
        this.tabs.toArray().forEach((tab) => (tab.active = false));
        this.tabs.toArray()[this.selectedTabIndex].active = true;
      }
    }
  }
}