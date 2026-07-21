import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {NavigationStart, Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  settings: any;
  defaultSettings: any;
  onSettingsChanged: BehaviorSubject<any>;

  constructor(private router: Router) {
    this.defaultSettings = {
      layout: {
        showNavigation: true,
        showHeader: true,
        showFooter: true
      },
      routerAnimation: 'fadeIn'
    };

    this.settings = Object.assign({}, this.defaultSettings);

    router.events.subscribe(
      (event) => {
        if (event instanceof NavigationStart) {
          this.setSettings({layout: this.defaultSettings.layout});
        }
      }
    );

    this.onSettingsChanged = new BehaviorSubject(this.settings);
  }

  setSettings(settings) {
    this.settings = Object.assign({}, this.settings, settings);
    this.onSettingsChanged.next(this.settings);
  }
}
