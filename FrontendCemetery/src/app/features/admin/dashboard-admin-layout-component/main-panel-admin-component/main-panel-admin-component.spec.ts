import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainPanelAdminComponent } from './main-panel-admin-component';

describe('MainPanelAdminComponent', () => {
  let component: MainPanelAdminComponent;
  let fixture: ComponentFixture<MainPanelAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainPanelAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainPanelAdminComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
