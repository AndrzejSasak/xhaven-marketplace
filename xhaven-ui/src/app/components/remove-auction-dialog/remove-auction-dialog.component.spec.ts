import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveAuctionDialogComponent } from './remove-auction-dialog.component';

describe('RemoveAuctionDialogComponent', () => {
  let component: RemoveAuctionDialogComponent;
  let fixture: ComponentFixture<RemoveAuctionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RemoveAuctionDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RemoveAuctionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
