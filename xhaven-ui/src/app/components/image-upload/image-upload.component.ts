import { HttpClient } from '@angular/common/http';
import {Component, EventEmitter, Input, Output} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {ImageDto} from "../../models/dto/dto-models";

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.scss']
})
export class ImageUploadComponent {

  @Input() componentId: number;
  imageURL: string;
  uploadForm: FormGroup;

  @Output() onImageUploaded: EventEmitter<any> = new EventEmitter<any>();

  constructor(private httpClient: HttpClient, private fb: FormBuilder) {
    this.uploadForm = this.fb.group({
      avatar: [null],
      name: ['']
    })
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    this.uploadForm.patchValue({
      avatar: file
    });

    this.uploadForm.get('avatar')?.updateValueAndValidity()
    const reader= new FileReader();
    reader.onload = () => {
      this.imageURL = reader.result as string;
    }
    reader.readAsDataURL(file);

    if(file) {

      const imageData = {
        "componentId": this.componentId,
        "file": file
      }

      this.onImageUploaded.emit(imageData);
    }

  }
}
