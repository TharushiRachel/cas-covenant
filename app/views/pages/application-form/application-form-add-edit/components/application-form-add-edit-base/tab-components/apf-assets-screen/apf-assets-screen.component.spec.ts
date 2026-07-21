import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfAssetsScreenComponent } from './apf-assets-screen.component';

describe('ApfAssetsScreenComponent', () => {
  let component: ApfAssetsScreenComponent;
  let fixture: ComponentFixture<ApfAssetsScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfAssetsScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfAssetsScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
