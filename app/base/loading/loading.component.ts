import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';
import {CommonService} from '../../core/service/common/common.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnDestroy {

  loading = false;
  loadingSubscription: Subscription;

  constructor(private commons: CommonService,
              private cdRef: ChangeDetectorRef) {
    this.loadingSubscription = this.commons.getLoadingStatus().subscribe(loading => {
      this.loading = loading.status;
      this.cdRef.detectChanges();
    });
  }

  private _animation = 'sk-folding-cube';

  get animation(): string {
    return this._animation;
  }

  ngOnDestroy() {
    this.loadingSubscription.unsubscribe();
  }

}
