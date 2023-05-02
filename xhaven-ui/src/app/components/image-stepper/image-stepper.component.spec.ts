import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageStepperComponent } from './image-stepper.component';

describe('ImageStepperComponent', () => {
  let component: ImageStepperComponent;
  let fixture: ComponentFixture<ImageStepperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImageStepperComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImageStepperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
