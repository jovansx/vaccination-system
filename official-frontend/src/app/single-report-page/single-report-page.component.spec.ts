import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleReportPageComponent } from './single-report-page.component';

describe('SingleReportPageComponent', () => {
  let component: SingleReportPageComponent;
  let fixture: ComponentFixture<SingleReportPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleReportPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleReportPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
