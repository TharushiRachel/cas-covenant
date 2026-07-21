import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcTemplateAddDataComponent } from './upc-template-add-data.component';

describe('UpcTemplateAddDataComponent', () => {
  let component: UpcTemplateAddDataComponent;
  let fixture: ComponentFixture<UpcTemplateAddDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpcTemplateAddDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpcTemplateAddDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
