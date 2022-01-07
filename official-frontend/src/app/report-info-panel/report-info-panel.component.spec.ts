import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportInfoPanelComponent } from './report-info-panel.component';

describe('ReportInfoPanelComponent', () => {
  let component: ReportInfoPanelComponent;
  let fixture: ComponentFixture<ReportInfoPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportInfoPanelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportInfoPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
