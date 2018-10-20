import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllGistUserComponent } from './all-gist-user.component';

describe('AllGistUserComponent', () => {
  let component: AllGistUserComponent;
  let fixture: ComponentFixture<AllGistUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllGistUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllGistUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
