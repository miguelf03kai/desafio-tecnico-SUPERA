import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrioridadeComponent } from './prioridade.component';

describe('PrioridadeComponent', () => {
  let component: PrioridadeComponent;
  let fixture: ComponentFixture<PrioridadeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrioridadeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrioridadeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
