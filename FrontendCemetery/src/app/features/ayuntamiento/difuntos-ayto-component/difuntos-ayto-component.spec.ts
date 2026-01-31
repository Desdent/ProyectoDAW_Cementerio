import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DifuntosAytoComponent } from './difuntos-ayto-component';

describe('DifuntosAytoComponent', () => {
  let component: DifuntosAytoComponent;
  let fixture: ComponentFixture<DifuntosAytoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DifuntosAytoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DifuntosAytoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
