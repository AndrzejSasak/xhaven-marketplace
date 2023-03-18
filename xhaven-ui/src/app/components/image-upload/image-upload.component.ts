import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.scss']
})
export class ImageUploadComponent {

  fileName = '';
  imageURL: string;
  uploadForm: FormGroup;

  constructor(private httpClient: HttpClient, private fb: FormBuilder) {
    this.uploadForm = this.fb.group({
      avatar: [null],
      name: ['']
    })
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    this.uploadForm.patchValue({
      avatar: file
    });

    this.uploadForm.get('avatar')?.updateValueAndValidity()
    const reader = new FileReader();
    reader.onload = () => {
      this.imageURL = reader.result as string;
    }
    reader.readAsDataURL(file);

    if(file) {
      
      this.fileName = file.name;
      const formData = new FormData();
      formData.append("thumbnail", file);
      const upload$ = this.httpClient.post("url", formData);
      upload$.subscribe();
    }

  }
}
