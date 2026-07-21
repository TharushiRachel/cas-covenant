import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveRiskOpinionReplyComponent } from './save-risk-opinion-reply.component';

describe('SaveRiskOpinionReplyComponent', () => {
  let component: SaveRiskOpinionReplyComponent;
  let fixture: ComponentFixture<SaveRiskOpinionReplyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveRiskOpinionReplyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveRiskOpinionReplyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
