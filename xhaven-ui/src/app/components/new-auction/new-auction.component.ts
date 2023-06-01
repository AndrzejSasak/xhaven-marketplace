import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';
import {CategoryService} from "../../services/category.service";
import {CategoryDto, NewAuctionDto} from "../../models/dto/dto-models";
import {AuctionService} from "../../services/auction.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-auction',
  templateUrl: './new-auction.component.html',
  styleUrls: ['./new-auction.component.scss']
})
export class NewAuctionComponent implements OnInit {
  treeControl = new NestedTreeControl<CategoryDto>(node => node.subcategories);
  dataSource = new MatTreeNestedDataSource<CategoryDto>();

  chosenCategoryName?: string;

  chosenCategory: CategoryDto;
  title: string;
  description: string;
  price: string;
  contactInformation: string;
  phoneNumber: string;

  imageComponentToImageMap: Record<number, File> = {};
  images: File[];

  constructor(private router: Router,
              private httpClient: HttpClient,
              private fb: FormBuilder,
              private categoryService: CategoryService,
              private auctionService: AuctionService) {
  }
  hasChild = (_: number, node: CategoryDto) => !!node.subcategories && node.subcategories.length > 0;

  setCategory(category: CategoryDto): void {
    this.chosenCategoryName = category.categoryName;
    this.chosenCategory = category;
  }
  ngOnInit(): void {
    this.categoryService.getCategories().subscribe((categories: CategoryDto[]): void => {
      this.dataSource.data = categories;
    })
  }

  uploadImage(imageData: any): void {
    this.imageComponentToImageMap[imageData.componentId] = imageData.file
  }

  postAuction(): void {
    this.images = Object.values(this.imageComponentToImageMap) as File[];
    let formDataWhole = new FormData();

    this.images.forEach((image: File) => {
      formDataWhole.append('files', image);
    });

    const newAuctionDto: NewAuctionDto = {
      "title": this.title,
      "description": this.description,
      "price": this.price,
      "contactInformation": this.contactInformation,
      "phoneNumber": this.phoneNumber,
      "category": this.chosenCategory
    }

    formDataWhole.append('newAuctionDto', new Blob([JSON.stringify(newAuctionDto)], {
      type: 'application/json'
    }))

    this.auctionService.postNewAuction(formDataWhole).subscribe(
      () => {
        this.router.navigate(['home']);
      }
    );
  }

}
