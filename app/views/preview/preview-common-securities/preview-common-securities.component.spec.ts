import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewCommonSecuritiesComponent } from './preview-common-securities.component';

describe('PreviewCommonSecuritiesComponent', () => {
  let component: PreviewCommonSecuritiesComponent;
  let fixture: ComponentFixture<PreviewCommonSecuritiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewCommonSecuritiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewCommonSecuritiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
