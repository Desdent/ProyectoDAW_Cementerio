import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConcesionesAytoComponent } from './concesiones-ayto-component';

describe('ConcesionesAytoComponent', () => {
  let component: ConcesionesAytoComponent;
  let fixture: ComponentFixture<ConcesionesAytoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConcesionesAytoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConcesionesAytoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
