import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllGistComponent } from './all-gist.component';

describe('AllGistComponent', () => {
  let component: AllGistComponent;
  let fixture: ComponentFixture<AllGistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllGistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllGistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
