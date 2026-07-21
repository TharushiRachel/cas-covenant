import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateStructureComponent } from './template-structure.component';

describe('TemplateStructureComponent', () => {
  let component: TemplateStructureComponent;
  let fixture: ComponentFixture<TemplateStructureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplateStructureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
