import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable()
export class ACAESearchService implements Resolve<any> {
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([
        this.getFacilityPapers()
      ]).then(() => {
        resolve()
      }, reject)
    })
  }
  getFacilityPapers(){
    
  }
}