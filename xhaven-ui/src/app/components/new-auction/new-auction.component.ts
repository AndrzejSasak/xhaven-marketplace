import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {CategoryService} from "../../services/category.service";
import {CategoryDto} from "../../models/dto/dto-models";

// import { ImageUploadComponent } from '../image-upload/image-upload.component';

@Component({
  selector: 'app-new-auction',
  templateUrl: './new-auction.component.html',
  styleUrls: ['./new-auction.component.scss']
})
export class NewAuctionComponent implements OnInit {
  treeControl = new NestedTreeControl<CategoryDto>(node => node.subcategories);
  dataSource = new MatTreeNestedDataSource<CategoryDto>();

  chosenCategory: string;
  // imageUploads: ImageUploadComponent[];

  constructor(private httpClient: HttpClient,
              private fb: FormBuilder,
              private categoryService: CategoryService) {
    // let imageUpload = new ImageUploadComponent(httpClient, fb);
  }

  hasChild = (_: number, node: CategoryDto) => !!node.subcategories && node.subcategories.length > 0;

  setCategory(name: string) {
    this.chosenCategory = name;
  }
  ngOnInit(): void {
    // this.dataSource.data = //todo: category json from backend
    this.categoryService.getCategories().subscribe((categories: CategoryDto[]) => {
      this.dataSource.data = categories;
    })
  }

}
