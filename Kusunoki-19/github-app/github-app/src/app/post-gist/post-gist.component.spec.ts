import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostGistComponent } from './post-gist.component';

describe('PostGistComponent', () => {
  let component: PostGistComponent;
  let fixture: ComponentFixture<PostGistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostGistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostGistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
