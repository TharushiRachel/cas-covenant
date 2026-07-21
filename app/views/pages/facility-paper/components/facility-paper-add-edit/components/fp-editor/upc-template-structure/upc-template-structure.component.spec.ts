import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcTemplateStructureComponent } from './upc-template-structure.component';

describe('UpcTemplateStructureComponent', () => {
  let component: UpcTemplateStructureComponent;
  let fixture: ComponentFixture<UpcTemplateStructureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcTemplateStructureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcTemplateStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
