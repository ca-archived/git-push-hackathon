import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TokenCheckerComponent } from './token-checker.component';

describe('TokenCheckerComponent', () => {
  let component: TokenCheckerComponent;
  let fixture: ComponentFixture<TokenCheckerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TokenCheckerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TokenCheckerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
