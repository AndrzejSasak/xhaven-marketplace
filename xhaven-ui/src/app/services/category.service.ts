import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CategoryDto} from "../models/dto/dto-models";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  url: string = 'http://localhost:8080/api/v1/categories';
  constructor(private httpClient: HttpClient) { }

  getCategories(): Observable<CategoryDto[]> {
    return this.httpClient.get<CategoryDto[]>(this.url);
  }
}
