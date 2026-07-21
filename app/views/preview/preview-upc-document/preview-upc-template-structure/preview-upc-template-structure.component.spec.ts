import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewUpcTemplateStructureComponent } from './preview-upc-template-structure.component';

describe('PreviewUpcTemplateStructureComponent', () => {
  let component: PreviewUpcTemplateStructureComponent;
  let fixture: ComponentFixture<PreviewUpcTemplateStructureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewUpcTemplateStructureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewUpcTemplateStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
