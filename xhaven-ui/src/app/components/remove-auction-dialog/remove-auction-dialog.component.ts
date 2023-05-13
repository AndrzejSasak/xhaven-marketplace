import {Component, Inject, Optional} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-remove-auction-dialog',
  templateUrl: './remove-auction-dialog.component.html',
  styleUrls: ['./remove-auction-dialog.component.scss']
})
export class RemoveAuctionDialogComponent {

  constructor(public dialogRef: MatDialogRef<RemoveAuctionDialogComponent>,
              @Optional() @Inject(MAT_DIALOG_DATA) public isSold: boolean) {
  }

  onNotSoldClick(): void {
    this.dialogRef.close({event: 'notSold'})
  }

  onSoldClick(): void {
    this.dialogRef.close({event: 'sold'})
  }

}
