import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CementeriosAytoComponent } from './cementerios-ayto-component';

describe('CementeriosAytoComponent', () => {
  let component: CementeriosAytoComponent;
  let fixture: ComponentFixture<CementeriosAytoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CementeriosAytoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CementeriosAytoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
