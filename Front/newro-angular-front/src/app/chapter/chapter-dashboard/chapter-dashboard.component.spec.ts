import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChapterDashboardComponent } from './chapter-dashboard.component';

describe('ChapterDashboardComponent', () => {
  let component: ChapterDashboardComponent;
  let fixture: ComponentFixture<ChapterDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChapterDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChapterDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
