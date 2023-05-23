import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CategoryDto} from "../../models/dto/dto-models";
import {NestedTreeControl} from "@angular/cdk/tree";
import {MatTreeNestedDataSource} from "@angular/material/tree";
import {CategoryService} from "../../services/category.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  treeControl = new NestedTreeControl<CategoryDto>(node => node.subcategories);
  dataSource = new MatTreeNestedDataSource<CategoryDto>();
  categoryName: string | null;
  hasChild = (_: number, node: CategoryDto) => !!node.subcategories && node.subcategories.length > 0;

  constructor(private route: ActivatedRoute,
              private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.categoryName = this.route.snapshot.paramMap.get('categoryId');
    this.categoryService.getCategories().subscribe((categories: CategoryDto[]): void => {
      this.dataSource.data = categories;
    })
  }


}
