import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageErrorBadRequestComponent } from './page-error-bad-request.component';

describe('PageErrorBadRequestComponent', () => {
  let component: PageErrorBadRequestComponent;
  let fixture: ComponentFixture<PageErrorBadRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageErrorBadRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageErrorBadRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
