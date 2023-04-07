import { Injectable } from '@angular/core';
import {AuctionDto} from "../models/dto/dto-models";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Form} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class AuctionService {

  url: string = 'http://localhost:8080/api/v1/auctions';

  constructor(private httpClient: HttpClient) { }

  getAuctions(): Observable<AuctionDto[]> {
    return this.httpClient.get<AuctionDto[]>(this.url);
  }

  postNewAuction(formData: FormData): Observable<any> {
    // let headers: HttpHeaders = new HttpHeaders({
    //   'enctype': 'multipart/form-data'
    // })
    return this.httpClient.post(this.url, formData);
    // return this.httpClient.post(this.url, auctionDto, { headers: headers });
  }

}
