import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveCommitteeTypeComponent } from './save-committee-type.component';

describe('SaveCommitteeTypeComponent', () => {
  let component: SaveCommitteeTypeComponent;
  let fixture: ComponentFixture<SaveCommitteeTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveCommitteeTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveCommitteeTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
