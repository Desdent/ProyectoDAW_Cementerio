import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainPanelAytoComponent } from './main-panel-ayto-component';

describe('MainPanelAytoComponent', () => {
  let component: MainPanelAytoComponent;
  let fixture: ComponentFixture<MainPanelAytoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainPanelAytoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainPanelAytoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
