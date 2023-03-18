import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ImageUploadComponent } from '../image-upload/image-upload.component';

@Component({
  selector: 'app-new-offer',
  templateUrl: './new-offer.component.html',
  styleUrls: ['./new-offer.component.scss']
})
export class NewOfferComponent {

  imageUploads: ImageUploadComponent[];

  constructor(private httpClient: HttpClient, private fb: FormBuilder) {
    let imageUpload = new ImageUploadComponent(httpClient, fb);
    // this.imageUploads.push(imageUpload);
  }

  
}
