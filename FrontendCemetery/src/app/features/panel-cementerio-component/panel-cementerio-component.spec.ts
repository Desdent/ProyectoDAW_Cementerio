import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelCementerioComponent } from './panel-cementerio-component';

describe('PanelCementerioComponent', () => {
  let component: PanelCementerioComponent;
  let fixture: ComponentFixture<PanelCementerioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PanelCementerioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PanelCementerioComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
