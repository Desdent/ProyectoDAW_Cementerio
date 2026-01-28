import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CementeriosAdminComponent } from './cementerios-admin-component';

describe('CementeriosAdminComponent', () => {
  let component: CementeriosAdminComponent;
  let fixture: ComponentFixture<CementeriosAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CementeriosAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CementeriosAdminComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
