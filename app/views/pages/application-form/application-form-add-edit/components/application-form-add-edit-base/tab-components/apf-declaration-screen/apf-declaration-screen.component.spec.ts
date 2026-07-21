import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApfDeclarationScreenComponent } from './apf-declaration-screen.component';

describe('ApfDeclarationScreenComponent', () => {
  let component: ApfDeclarationScreenComponent;
  let fixture: ComponentFixture<ApfDeclarationScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApfDeclarationScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApfDeclarationScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
