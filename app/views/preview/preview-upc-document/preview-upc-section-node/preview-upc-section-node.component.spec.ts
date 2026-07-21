import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewUpcSectionNodeComponent } from './preview-upc-section-node.component';

describe('PreviewUpcSectionNodeComponent', () => {
  let component: PreviewUpcSectionNodeComponent;
  let fixture: ComponentFixture<PreviewUpcSectionNodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewUpcSectionNodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewUpcSectionNodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
