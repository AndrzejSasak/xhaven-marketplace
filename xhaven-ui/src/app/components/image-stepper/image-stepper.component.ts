import {Component} from '@angular/core';
import {CdkStepper} from "@angular/cdk/stepper";

@Component({
  selector: 'app-image-stepper',
  templateUrl: './image-stepper.component.html',
  styleUrls: ['./image-stepper.component.scss'],
  providers: [{provide: CdkStepper, useExisting: ImageStepperComponent}],
})
export class ImageStepperComponent extends CdkStepper {
  selectStepByIndex(index: number): void {
    this.selectedIndex = index;
  }
}
