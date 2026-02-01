import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CementeriosClienteComponent } from './cementerios-cliente-component';

describe('CementeriosClienteComponent', () => {
  let component: CementeriosClienteComponent;
  let fixture: ComponentFixture<CementeriosClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CementeriosClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CementeriosClienteComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
